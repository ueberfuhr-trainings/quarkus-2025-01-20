package de.schulungen.quarkus.domain;

public record CustomerCreatedEvent(
  Customer customer
) {
}
