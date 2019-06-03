package com.sensedia.payment.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.sensedia.payment.exception.EntityAlreadyExistsException;
import com.sensedia.payment.exception.EntityNotFoundException;
import com.sensedia.payment.exception.ErrorMessage;
import com.sensedia.payment.exception.InternalServerErrorException;
import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.PreconditionFailedException;
import com.sensedia.payment.exception.UnprocessableEntityException;

@RestControllerAdvice
public class ControllerAdvice {
  
  private Logger log = LogManager.getLogger(this);
  
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
  
  @ExceptionHandler(EntityAlreadyExistsException.class)
  protected ResponseEntity<MessageError> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
      log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getError());
  }

  @ExceptionHandler(PreconditionFailedException.class)
  protected ResponseEntity<List<MessageError>> handlePreconditionFailedException(PreconditionFailedException ex) {
	  log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ex.getErrors());
  }
  
  @ExceptionHandler(InternalServerErrorException.class)
  protected ResponseEntity<MessageError> handlePreconditionFailedException(InternalServerErrorException ex) {
      log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getError());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<List<MessageError>> handleException(HttpMessageNotReadableException ex) {
      List<MessageError> errors = new ArrayList<>();
      errors.add(new MessageError(ErrorMessage.INVALID_REQUEST));
      return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errors);
  }


}
