package com.sensedia.payment.exception;

public abstract class Messages {

	private Messages() {
		// empty
	}
	public static final String REQUIRED_FIELD = "422.001";
	public static final String NOT_FOUND_CLIENT = "404.001";
	public static final String SERVER_ERROR = "500.001";
}
