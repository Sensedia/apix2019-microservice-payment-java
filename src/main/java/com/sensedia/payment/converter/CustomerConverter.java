package com.sensedia.payment.converter;

import java.util.List;
import java.util.stream.Collectors;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.request.CustomerRequest;
import com.sensedia.payment.response.CustomerResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConverter {
  
  public static List<CustomerResponse> toCustomerResponse(List<CustomerEntity> customers) {
    return customers.stream().map(CustomerConverter::toCustomerResponse).collect(Collectors.toList());
  }

  public static CustomerResponse toCustomerResponse(CustomerEntity customer) {
    return CustomerResponse.builder()
    		.id(customer.getId())
    		.document(customer.getDocument())
    		.name(customer.getName())
    		.email(customer.getEmail())
    		.phone(customer.getPhone())
    		.expirationDay(customer.getExpirationDay())
    		.build();
  }

  public static CustomerEntity toCustomer(CustomerRequest customer) {
    return CustomerEntity.builder()
    		.document(customer.getDocument())
    		.name(customer.getName())
    		.email(customer.getEmail())
    		.phone(customer.getPhone())
    		.expirationDay(customer.getExpirationDay())
    		.password(customer.getPassword())
    		.build();

  }

}
