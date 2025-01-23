package de.schulungen.quarkus.domain;

import de.schulungen.quarkus.tests.InitializationDisabledProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestProfile(InitializationDisabledProfile.class)
@Tag("Domain")
@Tag("No-Init")
class CustomersServiceInitializationDisabledTests {

  @Inject
  CustomersService customersService;

  @Test
  void shouldNotInitializeCustomersService() {
    var count = customersService.count();
    assertTrue(count == 0);
  }

}
