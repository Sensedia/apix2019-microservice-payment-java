package com.sensedia.payment.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.enumeration.InstallmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class InstallmentResponse {
	
	private BigDecimal value;
	
	private InstallmentStatus status;
	
	private String expirationDate;
	
	private String payday;
	
	private BigDecimal paidValue;
	
	private Integer appliedDiscountPercentage;
	
	public static InstallmentResponse valueOf(InstallmentEntity installmentEntity) {
		return InstallmentResponse.builder()
				.appliedDiscountPercentage(installmentEntity.getAppliedDiscount())
				.expirationDate(installmentEntity.getExpirationDate().toString())
				.paidValue(installmentEntity.getPaidValue())
				.payday(Optional.ofNullable(installmentEntity.getPayday())
						.map(LocalDateTime::toString)
						.orElse(null))
				.status(installmentEntity.getStatus())
				.value(installmentEntity.getValue())
				.build();
	}

}
