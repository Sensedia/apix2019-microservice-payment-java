package com.sensedia.payment.request;

import java.math.BigDecimal;

import com.sensedia.payment.entity.ClientEntity;
import com.sensedia.payment.entity.DebitEntity;
import lombok.Data;

@Data
public class DebitRequest {

  private String productId;

  private BigDecimal value;

  private Integer installmentsNumber;

  public DebitEntity toEntity(ClientEntity client) {
    return DebitEntity.builder().client(client).value(value).installmentsNumber(installmentsNumber).productId(productId).build();
  }

}
