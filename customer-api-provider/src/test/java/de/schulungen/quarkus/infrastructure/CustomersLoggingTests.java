package de.schulungen.quarkus.infrastructure;

import de.schulungen.quarkus.domain.Customer;
import de.schulungen.quarkus.domain.CustomerState;
import de.schulungen.quarkus.domain.CustomersService;
import de.schulungen.quarkus.tests.ProfileWithMockedLogger;
import io.quarkus.arc.log.LoggerName;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@QuarkusTest
@TestProfile(ProfileWithMockedLogger.class)
class CustomersLoggingTests {

  /*
   * Requirement:
   *  - when a customer is created, a logging should occur (Logger with name "customers")
   * Hints:
   *  - we mock the JBoss logger here to verify the logging
   *  - this is error-prone, because
   *     - we overwrite the Quarkus-internal @DefaultBean (see io.quarkus.arc.runtime.LoggerProducer)
   *     - we verify the invocation of the special logger method (instead of verifying any logging)
   *  - it would be better instead to encapsulate Customer Events Logging within a custom bean
   *    and mock this bean during the test
   */

  @Inject
  CustomersService service;
  @LoggerName("customer-events")
  Logger log;

  @DisplayName("[INFRA] Customer created -> Logging")
  @Test
  void shouldLogWhenCustomerCreated() throws InterruptedException {
    reset(log); // already logged during startup (initialization)!

    // we invoke the logging asynchronously -> we join with a count down latch
    final var lock = new CountDownLatch(1);
    // count down when the info() method is invoked
    doAnswer(invocation -> {
      lock.countDown();
      return null;
    })
      .when(log).info(anyString());

    var customer = new Customer();
    customer.setName("Tom");
    customer.setBirthdate(LocalDate.of(2000, Month.FEBRUARY, 2));
    customer.setState(CustomerState.ACTIVE);
    service.createCustomer(customer);

    // wait until logging is done asynchronously
    Assertions.assertTrue(lock.await(1, TimeUnit.SECONDS));

    verify(log).info(anyString());

  }

}
