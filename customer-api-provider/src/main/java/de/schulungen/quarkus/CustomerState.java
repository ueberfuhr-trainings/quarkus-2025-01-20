package de.schulungen.quarkus;

import jakarta.json.bind.annotation.JsonbTypeAdapter;

@JsonbTypeAdapter(CustomerStateAdapter.class)
public enum CustomerState {

  ACTIVE, LOCKED, DISABLED

}
