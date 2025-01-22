package de.schulungen.quarkus.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  private final HashMap<UUID, Customer> customers = new HashMap<>();

  // TODO: extract?
  {
    Customer customer = new Customer();
    customer.setUuid(UUID.randomUUID());
    customer.setName("Tom Mayer");
    customer.setState(CustomerState.ACTIVE);
    customer.setBirthdate(LocalDate.of(1990, Month.JULY, 1));
    customers.put(customer.getUuid(), customer);
  }

  public Stream<Customer> getCustomers() {
    return customers
      .values()
      .stream();
  }

  public Stream<Customer> getCustomersByState(CustomerState state) {
    return getCustomers()
      .filter(customer -> customer.getState() == state);
  }

  public Optional<Customer> getCustomerByUuid(UUID uuid) {
    return Optional
      .ofNullable(customers.get(uuid));
  }

  public void createCustomer(@Valid Customer customer) {
    var uuid = UUID.randomUUID();
    customer.setUuid(uuid);
    customers.put(customer.getUuid(), customer);
  }

  public boolean deleteCustomer(UUID uuid) {
    return null != customers.remove(uuid);
  }

  public boolean existsCustomer(UUID uuid) {
    return customers.containsKey(uuid);
  }


}
