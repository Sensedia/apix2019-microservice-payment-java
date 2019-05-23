package com.sensedia.payment.exception;

import java.io.Serializable;

import com.sensedia.payment.component.Dictionary;


public class MessageError implements Serializable {
	private static final long serialVersionUID = 7070495460223524599L;
	private String code;
    private String message;
    
    public MessageError() {
    	// empty constructor
    }

    public MessageError(String code, Object... args) {
        this.code = code;
        this.message = Dictionary.valueOf(code, args);
    }
    
    public String getCode() {
    	return code;
    }
    
    public void setCode(String code) {
		this.code = code;
	}
    
    public String getMessage() {
    	return message;
    }
    
    public void setMessage(String message) {
		this.message = message;
	}

}
