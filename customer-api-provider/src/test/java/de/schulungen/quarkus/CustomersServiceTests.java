package de.schulungen.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@Tag("Domain")
class CustomersServiceTests {

  @Inject
  CustomersService customerService;

  @Test
  void whenCreateCustomer_thenGetCustomerWillReturnCustomer() {
    var customer = new Customer();
    customer.setName("John");
    customer.setBirthdate(LocalDate.of(2004, Month.MAY, 2));
    customer.setState(CustomerState.ACTIVE);

    customerService.createCustomer(customer);

    assertNotNull(customer.getUuid());

    var result = customerService.getCustomerByUuid(customer.getUuid());
    Assertions.assertTrue(result.isPresent());
    Assertions.assertSame(customer, result.get());

  }

  @Test
  void whenCreateCustomerWithoutName_thenValidationWillFail() {
    var customer = new Customer();
    customer.setName(null);
    customer.setBirthdate(LocalDate.of(2004, Month.MAY, 2));
    customer.setState(CustomerState.ACTIVE);

    assertThrows(
      Exception.class,
      () -> customerService.createCustomer(customer)
    );
  }


}
