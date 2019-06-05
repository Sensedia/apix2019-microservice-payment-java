package com.sensedia.payment.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.request.PaymentRequest;

public class PaymentValidator {
	
	private PaymentValidator() {
		// empty
	}

	public static void validate(PaymentRequest request) {
		List<MessageError> errors = new ArrayList<>();
		
		if (StringUtils.isEmpty(request.getCardNumber())) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "cardNumber"));
		}
		
		if (StringUtils.isEmpty(request.getDebitId())) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "debitId"));
		}
		
		if (request.getInstallments() == null) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "installments"));
		}
		
		if (!errors.isEmpty()) {
			throw new PreconditionFailedException(errors);
		}
	}

}
