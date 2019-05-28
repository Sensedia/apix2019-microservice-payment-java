package com.sensedia.payment.request;

import java.math.BigDecimal;

import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.entity.DebitEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitRequest {

  private String productId;

  private BigDecimal value;

  private Integer installmentsNumber;

  public DebitEntity toEntity(ClientEntity client) {
    return DebitEntity.builder().client(client).value(value).installmentsNumber(installmentsNumber).productId(productId).build();
  }

}
