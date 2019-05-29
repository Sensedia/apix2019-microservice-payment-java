package com.sensedia.payment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensedia.payment.entity.DebitEntity;
import com.sensedia.payment.entity.InstallmentEntity;
import com.sensedia.payment.enumeration.InstallmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstallmentService {
	
	public List<InstallmentEntity> generateInstallments(int day, int installmentsNumber, BigDecimal value, DebitEntity debit) {
		BigDecimal installmentValue = value.divide(new BigDecimal(installmentsNumber), 2, RoundingMode.DOWN)
				.setScale(2, RoundingMode.DOWN);
		BigDecimal remainder = value.subtract(installmentValue.multiply(BigDecimal.valueOf(installmentsNumber)));
		BigDecimal firstInstallmentValue = installmentValue.add(remainder);
		
		List<InstallmentEntity> installments = new ArrayList<>();
		installments.add(InstallmentEntity.builder()
				.debit(debit)
				.expirationDate(generateInstallmentDate(day, 0))
				.status(InstallmentStatus.PENDING)
				.value(firstInstallmentValue)
				.build());
		for (int i = 1; i < installmentsNumber; i++) {
			installments.add(InstallmentEntity.builder()
					.debit(debit)
					.expirationDate(generateInstallmentDate(day, i))
					.status(InstallmentStatus.PENDING)
					.value(installmentValue)
					.build());
		}
		
		return installments;
	}
	
	private LocalDate generateInstallmentDate(int day, int plusMonths) {
		LocalDate date = LocalDate.now();
		if (date.withDayOfMonth(day).isBefore(date.plusDays(10))) {
			plusMonths++;
		}
		date = date.withDayOfMonth(day);
		date = date.plusMonths(plusMonths);
		return date;
	}
	
}
