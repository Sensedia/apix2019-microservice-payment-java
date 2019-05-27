package com.sensedia.payment.response;

import java.math.BigDecimal;
import com.sensedia.payment.entity.DebitEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitResponse {

  private String productId;

  private BigDecimal value;

  private Integer installmentsNumber;

  public static DebitResponse valueOf(DebitEntity debitEntity) {
    return DebitResponse.builder().value(debitEntity.getValue())
        .installmentsNumber(debitEntity.getInstallmentsNumber()).productId(debitEntity.getProductId()).build();
  }

}
