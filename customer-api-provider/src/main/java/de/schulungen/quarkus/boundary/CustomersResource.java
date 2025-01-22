package de.schulungen.quarkus.boundary;


// GET /customers -> 200

import de.schulungen.quarkus.domain.CustomersService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@Path("/customers")
public class CustomersResource {

  @Context
  UriInfo uriInfo;
  @Inject
  CustomersService customerService;
  @Inject
  CustomerDtoMapper mapper;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<CustomerDto> getCustomers(
    @QueryParam("state")
    @Pattern(regexp = "active|locked|disabled")
    String state
  ) {
    return (
      state == null
        ? customerService.getCustomers()
        : customerService.getCustomersByState(mapper.mapState(state))
    )
      .map(mapper::map)
      .toList();
  }

  // TODO mapper?
  private static UUID fromParameter(String uuid) {
    try {
      return UUID.fromString(uuid);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid UUID: " + uuid);
    }
  }

  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON)
  public CustomerDto getCustomer(
    // if we use UUID here directly,
    // JAX-RS will return 404 if the path parameter is syntactically invalid
    @PathParam("uuid")
    String uuid
  ) {
    return customerService
      .getCustomerByUuid(fromParameter(uuid))
      .map(mapper::map)
      .orElseThrow(() -> new NotFoundException("Customer not found: " + uuid));
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCustomer(
    @Valid
    CustomerDto customerDto
  ) {

    var customer = mapper.map(customerDto);
    customerService.createCustomer(customer);
    var responseDto = mapper.map(customer);

    String location = uriInfo.getAbsolutePathBuilder()
      .path(customer.getUuid().toString())
      .build()
      .toString();

    return Response
      .created(URI.create(location))
      .entity(responseDto)
      .build();
  }

  @DELETE
  @Path("/{uuid}")
  public void deleteCustomer(
    // if we use UUID here directly,
    // JAX-RS will return 404 if the path parameter is syntactically invalid
    @PathParam("uuid")
    String uuid
  ) {
    if (!customerService.deleteCustomer(fromParameter(uuid))) {
      throw new NotFoundException("Customer not found: " + uuid);
    }
  }


}
