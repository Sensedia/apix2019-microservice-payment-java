package com.sensedia.payment.converter;

import java.util.List;
import java.util.stream.Collectors;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.request.CustomerRequest;
import com.sensedia.payment.response.CustomerResponse;

public class CustomerConverter {

  private CustomerConverter() {}

  public static List<CustomerResponse> toCustomerResponse(List<CustomerEntity> customers) {
    return customers.stream().map(CustomerConverter::toCustomerResponse).collect(Collectors.toList());
  }

  public static CustomerResponse toCustomerResponse(CustomerEntity customer) {
    return new CustomerResponse(customer.getId(), customer.getDocument(), customer.getName(), customer.getEmail(), customer.getPhone(),
        customer.getExpirationDay());
  }

  public static CustomerEntity toCustomer(CustomerRequest customer) {
    return new CustomerEntity(customer.getDocument(), customer.getPassword(), customer.getName(), customer.getEmail(), customer.getPhone(),
        customer.getExpirationDay());
  }

}
