package de.schulungen.quarkus.persistence;

import de.schulungen.quarkus.domain.CustomerState;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerEntityRepository
  implements PanacheRepositoryBase<CustomerEntity, UUID> {

  public List<CustomerEntity> findByState(CustomerState state) {
    return this.list("state", state);
  }

}
