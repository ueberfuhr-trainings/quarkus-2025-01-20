package de.schulungen.quarkus.persistence;

import de.schulungen.quarkus.domain.Customer;
import de.schulungen.quarkus.domain.CustomerState;
import de.schulungen.quarkus.domain.CustomersSink;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(CustomersSink.class)
// @IfBuildProperty ???
public class CustomersSinkJpaImpl
  implements CustomersSink {

  @Inject
  CustomerEntityMapper mapper;
  @Inject
  CustomerEntityRepository repo;

  @Override
  public Stream<Customer> findCustomers() {
    return repo
      .findAll()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Stream<Customer> findCustomersByState(CustomerState state) {
    return repo
      .findByState(state)
      .stream()
      .map(mapper::map);
  }

  @Override
  public Optional<Customer> findCustomerByUuid(UUID uuid) {
    return repo
      .findByIdOptional(uuid)
      .map(mapper::map);
  }

  @Transactional
  @Override
  public void createCustomer(Customer customer) {
    var entity = mapper.map(customer);
    repo.persist(entity);
    customer.setUuid(entity.getUuid()); // im Mapper
  }

  @Transactional
  @Override
  public boolean deleteCustomer(UUID uuid) {
    return repo.deleteById(uuid);
  }

  @Override
  public long count() {
    return repo.count();
  }
}
