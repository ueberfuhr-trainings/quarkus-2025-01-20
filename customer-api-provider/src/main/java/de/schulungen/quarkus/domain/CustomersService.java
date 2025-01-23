package de.schulungen.quarkus.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  @Inject
  Event<CustomerCreatedEvent> eventPublisher;

  private final HashMap<UUID, Customer> customers = new HashMap<>();

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
    eventPublisher.fireAsync(new CustomerCreatedEvent(customer));
  }

  public boolean deleteCustomer(UUID uuid) {
    return null != customers.remove(uuid);
  }

  public boolean existsCustomer(UUID uuid) {
    return customers.containsKey(uuid);
  }

  public long count() {
    return customers.size();
  }
}
