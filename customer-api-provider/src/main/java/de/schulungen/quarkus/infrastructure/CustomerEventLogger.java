package de.schulungen.quarkus.infrastructure;

import de.schulungen.quarkus.domain.CustomerCreatedEvent;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CustomerEventLogger {

  @LoggerName("customer-events")
  Logger log;

  public void logCustomerEvent(
    @ObservesAsync
    CustomerCreatedEvent event
  ) {
    log.info("Created new customer with uuid: " + event.customer().getUuid());
  }

}
