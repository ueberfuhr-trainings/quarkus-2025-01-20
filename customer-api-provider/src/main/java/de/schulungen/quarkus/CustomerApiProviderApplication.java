package de.schulungen.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CustomerApiProviderApplication {

  public static void main(String[] args) {
    Quarkus.run(args);
  }

}
