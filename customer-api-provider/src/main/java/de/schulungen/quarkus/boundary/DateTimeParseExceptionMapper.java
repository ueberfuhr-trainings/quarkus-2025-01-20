package de.schulungen.quarkus.boundary;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DateTimeParseExceptionMapper
  implements ExceptionMapper<JsonbException> {

  @Override
  public Response toResponse(JsonbException exception) {
    // Return a 400 Bad Request with a message
    return Response
      .status(Response.Status.BAD_REQUEST)
      .build();
  }
}
