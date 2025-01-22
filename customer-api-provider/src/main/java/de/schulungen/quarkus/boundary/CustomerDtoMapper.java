package de.schulungen.quarkus.boundary;

import de.schulungen.quarkus.domain.Customer;
import de.schulungen.quarkus.domain.CustomerState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class CustomerDtoMapper {

  public CustomerDto map(Customer customer) {
    CustomerDto customerDto = new CustomerDto();
    customerDto.setUuid(customer.getUuid());
    customerDto.setName(customer.getName());
    customerDto.setBirthdate(customer.getBirthdate());
    customerDto.setState(this.mapState(customer.getState()));
    return customerDto;
  }

  public Customer map(CustomerDto customerDto) {
    Customer customer = new Customer();
    customer.setUuid(customerDto.getUuid());
    customer.setName(customerDto.getName());
    customer.setBirthdate(customerDto.getBirthdate());
    customer.setState(this.mapState(customerDto.getState()));
    return customer;
  }

  public String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  public CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new BadRequestException("Unknown state: " + state);
    };
  }

}
