package de.schulungen.quarkus.persistence;

import de.schulungen.quarkus.domain.Customer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerEntityMapper {

  public CustomerEntity map(Customer customer) {
    CustomerEntity customerDto = new CustomerEntity();
    customerDto.setUuid(customer.getUuid());
    customerDto.setName(customer.getName());
    customerDto.setBirthdate(customer.getBirthdate());
    customerDto.setState(customer.getState());
    return customerDto;
  }

  public Customer map(CustomerEntity customerDto) {
    Customer customer = new Customer();
    customer.setUuid(customerDto.getUuid());
    customer.setName(customerDto.getName());
    customer.setBirthdate(customerDto.getBirthdate());
    customer.setState(customerDto.getState());
    return customer;
  }

}
