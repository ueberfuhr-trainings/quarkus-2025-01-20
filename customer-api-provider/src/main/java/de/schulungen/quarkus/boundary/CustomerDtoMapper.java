package de.schulungen.quarkus.boundary;

import de.schulungen.quarkus.domain.Customer;
import de.schulungen.quarkus.domain.CustomerState;
import jakarta.ws.rs.BadRequestException;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerDtoMapper {

  CustomerDto map(Customer customer);

  Customer map(CustomerDto customerDto);

  default String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  default CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new BadRequestException("Unknown state: " + state);
    };
  }

}
