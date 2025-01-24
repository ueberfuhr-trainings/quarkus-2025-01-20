package de.schulungen.quarkus.domain;

import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@DefaultBean
@Typed(CustomersSink.class)
public class CustomersSinkInMemoryImpl
  implements CustomersSink {

  private final HashMap<UUID, Customer> customers = new HashMap<>();

  @Override
  public Stream<Customer> findCustomers() {
    return customers
      .values()
      .stream();
  }

  @Override
  public Optional<Customer> findCustomerByUuid(UUID uuid) {
    return Optional
      .ofNullable(customers.get(uuid));
  }

  @Override
  public void createCustomer(Customer customer) {
    var uuid = UUID.randomUUID();
    customer.setUuid(uuid);
    customers.put(customer.getUuid(), customer);
  }

  @Override
  public boolean deleteCustomer(UUID uuid) {
    return null != customers.remove(uuid);
  }

  @Override
  public boolean existsCustomer(UUID uuid) {
    return customers.containsKey(uuid);
  }

  @Override
  public long count() {
    return customers.size();
  }
}
