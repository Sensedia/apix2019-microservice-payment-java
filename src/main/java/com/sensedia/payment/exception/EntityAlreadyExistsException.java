package com.sensedia.payment.exception;

public class EntityAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final MessageError error;

  public EntityAlreadyExistsException(MessageError error) {
    this.error = error;
  }

  public MessageError getError() {
    return error;
  }

}
