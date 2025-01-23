package de.schulungen.quarkus.domain;

import de.schulungen.quarkus.tests.InitializationEnabledProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestProfile(InitializationEnabledProfile.class)
@Tag("Domain")
@Tag("Init")
class CustomersServiceInitializationEnabledTests {

  @Inject
  CustomersService customersService;

  @Test
  void shouldInitializeCustomersService() {
    var count = customersService.count();
    assertTrue(count > 0);
  }

}
