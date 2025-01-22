package de.schulungen.quarkus;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
@Tag("API-MOCK")
class CustomerApiWithMockedServiceTests {

  @InjectMock
  CustomersService customersService;

  @DisplayName("GET /customers -> 200 + []")
  @Test
  void whenGetCustomers_thenOk() {
    when(customersService.getCustomers())
      .thenReturn(Stream.empty());
    given()
      .when()
      .accept(ContentType.JSON)
      .get("/customers/")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body(equalTo("[]"));
  }

  @DisplayName("GET /customers/{uuid} -> 200")
  @Test
  void whenGetExistingCustomer_thenReturn200() {
    var customer = new Customer();
    var uuid = UUID.randomUUID();
    customer.setUuid(uuid);
    customer.setName("John");
    customer.setBirthdate(LocalDate.of(2004, Month.MAY, 2));
    customer.setState(CustomerState.ACTIVE);
    when(customersService.getCustomerByUuid(uuid))
      .thenReturn(Optional.of(customer));
    given()
      .when()
      .accept(ContentType.JSON)
      .get("/customers/{uuid}", uuid.toString())
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("uuid", is(equalTo(uuid.toString())))
      .body("name", is(equalTo("John")))
      .body("birthdate", is(equalTo("2004-05-02")))
      .body("state", is(equalTo("active")));
  }

  @DisplayName("GET /customers/{uuid} -> 404")
  @Test
  void whenGetMissingCustomer_thenReturn404() {
    var uuid = UUID.randomUUID();
    when(customersService.getCustomerByUuid(uuid))
      .thenReturn(Optional.empty());
    given()
      .when()
      .accept(ContentType.JSON)
      .get("/customers/{uuid}", uuid.toString())
      .then()
      .statusCode(404);
  }

  @DisplayName("POST /customers (name < 3chars) -> 400")
  @Test
  void whenPostCustomersWithNameTooShort_thenBadRequest() {
    given()
      .when()
      .contentType(ContentType.JSON)
      .body("""
        {
          "name": "Jo",
          "birthdate": "2004-05-02",
          "state" : "active"
        }
        """)
      .accept(ContentType.JSON)
      .post("/customers")
      .then()
      .statusCode(400);
    // customer must not be created within the service
    verify(customersService, never())
      .createCustomer(any());
  }

}
