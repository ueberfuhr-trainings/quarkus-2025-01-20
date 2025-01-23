package de.schulungen.quarkus.tests;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class InitializationEnabledProfile
  implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of(
      "customers.initialization.enabled", "true"
    );
  }
}
