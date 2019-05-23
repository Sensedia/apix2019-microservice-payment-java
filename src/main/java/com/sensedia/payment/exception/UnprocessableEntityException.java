package com.sensedia.payment.exception;

import java.util.Arrays;
import java.util.List;

public class UnprocessableEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final List<MessageError> errors;

	public UnprocessableEntityException(MessageError error) {
		this.errors = Arrays.asList(error);
	}

	public UnprocessableEntityException(List<MessageError> errors) {
		this.errors = errors;
	}

	public List<MessageError> getErrors() {
		return errors;
	}

}