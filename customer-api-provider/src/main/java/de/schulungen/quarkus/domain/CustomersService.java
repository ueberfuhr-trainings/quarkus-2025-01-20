package de.schulungen.quarkus.domain;

import de.schulungen.quarkus.shared.interceptors.FireEvent;
import de.schulungen.quarkus.shared.interceptors.LogPerformance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  @Inject
  CustomersSink sink;

  public Stream<Customer> getCustomers() {
    return sink.findCustomers();
  }

  public Stream<Customer> getCustomersByState(CustomerState state) {
    return sink.findCustomersByState(state);
  }

  public Optional<Customer> getCustomerByUuid(UUID uuid) {
    return sink.findCustomerByUuid(uuid);
  }

  @LogPerformance
  @FireEvent(CustomerCreatedEvent.class)
  public void createCustomer(@Valid Customer customer) {
    sink.createCustomer(customer);
  }

  @LogPerformance(Logger.Level.DEBUG)
  public boolean deleteCustomer(UUID uuid) {
    return sink.deleteCustomer(uuid);
  }

  public boolean existsCustomer(UUID uuid) {
    return sink.existsCustomer(uuid);
  }

  public long count() {
    return sink.count();
  }
}
