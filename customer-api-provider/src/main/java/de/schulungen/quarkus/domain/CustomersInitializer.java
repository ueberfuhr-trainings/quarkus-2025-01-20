package de.schulungen.quarkus.domain;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.Month;

@Dependent
public class CustomersInitializer {

  @Inject
  CustomersService customersService;

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
