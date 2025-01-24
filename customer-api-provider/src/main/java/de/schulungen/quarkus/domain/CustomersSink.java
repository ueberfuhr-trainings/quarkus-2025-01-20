package de.schulungen.quarkus.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findCustomers();

  default Stream<Customer> findCustomersByState(CustomerState state) {
    return findCustomers()
      .filter(customer -> customer.getState().equals(state));
  }

  default Optional<Customer> findCustomerByUuid(UUID uuid) {
    return findCustomers()
      .filter(customer -> customer.getUuid().equals(uuid))
      .findFirst();
  }

  void createCustomer(Customer customer);

  boolean deleteCustomer(UUID uuid);

  default boolean existsCustomer(UUID uuid) {
    return findCustomerByUuid(uuid)
      .isPresent();
  }

  default long count() {
    return findCustomers()
      .count();
  }
}
