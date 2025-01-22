package de.schulungen.quarkus.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import java.util.UUID;

@ApplicationScoped
public class UUIDMapper {

  public String map(UUID uuid) {
    return null == uuid ? null : uuid.toString();
  }

  public UUID map(String uuid) {
    try {
      return null == uuid ? null : UUID.fromString(uuid);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid UUID: " + uuid);
    }
  }

}
