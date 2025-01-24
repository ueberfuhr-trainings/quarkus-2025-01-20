package de.schulungen.quarkus.persistence;

import de.schulungen.quarkus.domain.CustomerState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "Customer") // Name im JP-QL
@Table(name = "CUSTOMERS")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  private String name;
  @Column(name = "BIRTH_DATE")
  private LocalDate birthdate;
  // @Enumerated(EnumType.STRING)
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
