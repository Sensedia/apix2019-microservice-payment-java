package com.sensedia.payment.response;

import java.math.BigDecimal;
import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitResponse {
  
  private String clientId;
  
  private String productId;

  private BigDecimal value;

  private PaymentType paymentType;

  private Integer installmentsNumber;

  public static DebitResponse valueOf(DebitEntity debitEntity) {
    return DebitResponse.builder().clientId(debitEntity.getClientId()).value(debitEntity.getValue()).installmentsNumber(debitEntity.getInstallmentsNumber())
        .paymentType(debitEntity.getPaymentType()).productId(debitEntity.getProductId()).build();
  }
  
}
