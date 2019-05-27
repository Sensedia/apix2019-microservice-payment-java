package com.sensedia.payment.exceptions;

import java.util.List;

public class PreconditionFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final List<MessageError> errors;

    public PreconditionFailedException(List<MessageError> errors) {
        this.errors = errors;
    }

    public List<MessageError> getErrors() {
        return errors;
    }
}
