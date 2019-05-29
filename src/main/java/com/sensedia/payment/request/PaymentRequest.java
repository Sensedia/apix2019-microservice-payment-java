package com.sensedia.payment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
	
	private String debitId;
	
	private Integer installments;
	
	private String cardNumber;

}
