package de.schulungen.quarkus.persistence;

import de.schulungen.quarkus.domain.CustomerState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CustomerStateConverter
  implements AttributeConverter<CustomerState, Integer> {

  @Override
  public Integer convertToDatabaseColumn(CustomerState attribute) {
    return null == attribute ? null : switch (attribute) {
      case ACTIVE -> 0;
      case DISABLED -> 1;
      case LOCKED -> 2;
    };
  }

  @Override
  public CustomerState convertToEntityAttribute(Integer dbData) {
    return null == dbData ? null : switch (dbData) {
      case 0 -> CustomerState.ACTIVE;
      case 1 -> CustomerState.DISABLED;
      case 2 -> CustomerState.LOCKED;
      default -> throw new IllegalStateException("Unexpected value: " + dbData);
    };
  }
}
