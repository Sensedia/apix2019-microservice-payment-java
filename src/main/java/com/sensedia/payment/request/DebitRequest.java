package com.sensedia.payment.request;

import java.math.BigDecimal;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.enums.PaymentType;
import lombok.Data;

@Data
public class DebitRequest {

  private String clientId;
  
  private String productId;

  private BigDecimal value;

  private PaymentType paymentType;

  private Integer installmentsNumber;

  public DebitEntity toEntity() {
    return DebitEntity.builder().clientId(clientId).value(value).paymentType(paymentType).installmentsNumber(installmentsNumber).productId(productId).build();
  }


}
