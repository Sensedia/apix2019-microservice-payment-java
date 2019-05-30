package com.sensedia.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final MessageError error;

  public InternalServerErrorException(MessageError error) {
    this.error = error;
  }

  public MessageError getError() {
    return error;
  }

}
