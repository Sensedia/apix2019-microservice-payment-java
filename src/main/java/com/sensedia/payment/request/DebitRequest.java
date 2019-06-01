package com.sensedia.payment.request;

import java.math.BigDecimal;
import com.sensedia.payment.entity.CustomerEntity;
import com.sensedia.payment.entity.DebitEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitRequest {

  private String productId;

  private String description;

  private BigDecimal value;

  private Integer installmentsNumber;

  public DebitEntity toEntity(CustomerEntity customer) {
    return DebitEntity.builder().customer(customer).value(value).installmentsNumber(installmentsNumber).productId(productId).description(description).build();
  }

}
