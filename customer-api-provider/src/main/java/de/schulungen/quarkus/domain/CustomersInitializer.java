package de.schulungen.quarkus.domain;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.Month;

@ApplicationScoped
// @IfBuildProfile("dev") // nur für DEV
// @UnlessBuildProfile("prod") // für alles außer PROD
@IfBuildProperty(
  name = "customers.initialization.enabled",
  stringValue = "true"
)
public class CustomersInitializer {

  @Inject
  CustomersService customersService;

  /*
  @ConfigProperty(
    name = "customers.initialization.enabled",
    defaultValue = "false"
  )
  boolean enabled; // if (enabled) {...}
  */

  @Startup
  public void init() {
    if (customersService.count() == 0) {
      Customer customer = new Customer();
      customer.setName("Tom Mayer");
      customer.setState(CustomerState.ACTIVE);
      customer.setBirthdate(LocalDate.of(1990, Month.JULY, 1));
      customersService.createCustomer(customer);
    }
  }

}
