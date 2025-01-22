package de.schulungen.quarkus;

import jakarta.json.bind.adapter.JsonbAdapter;

public class CustomerStateAdapter
  implements JsonbAdapter<CustomerState, String> {

  @Override
  public String adaptToJson(final CustomerState state) {
    return state.name().toLowerCase();
  }

  @Override
  public CustomerState adaptFromJson(final String state) {
    return CustomerState.valueOf(state.toUpperCase());
  }
}
