package com.sensedia.payment.response;

import java.math.BigDecimal;import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.entity.InstallmentEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitResponse {
	
	private String id;

	private String productId;

	private BigDecimal value;

	private Integer installmentsNumber;
  
	private List<InstallmentResponse> installments;

	public static DebitResponse valueOf(DebitEntity debitEntity) {
		debitEntity.getInstallments().sort(Comparator.comparing(InstallmentEntity::getExpirationDate));
		return DebitResponse.builder()
				.value(debitEntity.getValue())
				.id(debitEntity.getId().toString())
				.installmentsNumber(debitEntity.getInstallmentsNumber())
				.installments(debitEntity.getInstallments().stream()
						.map(InstallmentResponse::valueOf)
						.collect(Collectors.toList()))
				.productId(debitEntity.getProductId())
				.build();
	}

}
