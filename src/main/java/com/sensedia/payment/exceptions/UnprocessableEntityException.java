package com.sensedia.payment.exceptions;

public class UnprocessableEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final MessageError error;

	public UnprocessableEntityException(MessageError error) {
		this.error = error;
	}

	public MessageError getError() {
		return error;
	}

}