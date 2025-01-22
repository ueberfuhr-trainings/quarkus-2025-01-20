package de.schulungen.quarkus.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class Customer {

  private UUID uuid;
  @NotNull
  @Size(min = 3, max = 100)
  private String name;
  @Past
  private LocalDate birthdate;
  private CustomerState state = CustomerState.ACTIVE;

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public CustomerState getState() {
    return state;
  }

  public void setState(CustomerState state) {
    this.state = state;
  }
}
