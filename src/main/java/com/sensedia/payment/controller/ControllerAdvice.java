package com.sensedia.payment.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.sensedia.payment.exceptions.EntityNotFoundException;
import com.sensedia.payment.exceptions.ErrorMessage;
import com.sensedia.payment.exceptions.MessageError;
import com.sensedia.payment.exceptions.PreconditionFailedException;
import com.sensedia.payment.exceptions.UnprocessableEntityException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
  
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<MessageError> handleException(MethodArgumentTypeMismatchException ex) {
	  log.error(ex.getMessage(), ex);
      MessageError error = new MessageError(ErrorMessage.INVALID_REQUEST, ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Void> handleNotFoundException(EntityNotFoundException ex) {
	  log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  protected ResponseEntity<MessageError> handleUnprocessableEntityException(UnprocessableEntityException ex) {
	  log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getError());
  }

  @ExceptionHandler(PreconditionFailedException.class)
  protected ResponseEntity<List<MessageError>> handlePreconditionFailedException(PreconditionFailedException ex) {
	  log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ex.getErrors());
  }
  
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Void> handleException(Exception ex) {
	  log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }


}
