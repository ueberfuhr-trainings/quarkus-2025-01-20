package de.schulungen.quarkus.domain;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@Tag("Domain-Init")
public class CustomersServiceInitializationTests {

  @Inject
  CustomersService customersService;

  @Test
  void shouldInitializeCustomersService() {
    var count = customersService.count();
    assertTrue(count > 0);
  }

}
