package com.sensedia.payment.validator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.request.ClientRequest;

public class ClientValidator {

	public static void validate(ClientRequest request) {

		List<MessageError> errors = new ArrayList<>();

		String document = request.getDocument();
		if (StringUtils.isEmpty(document)) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "document"));
		}

		String name = request.getName();
		if (StringUtils.isEmpty(name)) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "name"));
		}

		String email = request.getEmail();
		if (StringUtils.isEmpty(email)) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "email"));
		}

		String phone = request.getPhone();
		if (StringUtils.isEmpty(phone)) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "phone"));
		}

		Integer expirationDay = request.getExpirationDay();
		if (StringUtils.isEmpty(expirationDay)) {
			errors.add(new MessageError(ErrorMessage.REQUIRED_FIELD, "expirationDay"));
		}
		if (!errors.isEmpty()) {
			throw new PreconditionFailedException(errors);
		}
	}
	
	private ClientValidator () {}
}
