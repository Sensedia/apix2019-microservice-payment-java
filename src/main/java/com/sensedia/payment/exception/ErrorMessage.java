package com.sensedia.payment.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ErrorMessage {
	
    public static final String REQUIRED_FIELD = "412.001";
    public static final String INVALID_UUID = "412.002";
    public static final String INVALID_REQUEST = "412.003";
    public static final String INVALID_FIELD = "412.004";
    
    public static final String FIELD_VALUE_NOT_EXISTS = "422.001";
	public static final String INVALID_INSTALLMENTS = "422.002";
    
}
