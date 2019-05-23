package com.sensedia.payment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sensedia.payment.exception.MessageError;
import com.sensedia.payment.exception.Messages;
import com.sensedia.payment.exception.NotFoundException;
import com.sensedia.payment.exception.UnprocessableEntityException;

@RestControllerAdvice
public class ControllerAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MessageError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    	logger.error(ex.getMessage(), ex);
    	return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new MessageError(Messages.REQUIRED_FIELD, ex.getParameterName()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
    	logger.error(ex.getMessage(), ex);
    	return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Void> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex) {
    	logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException(NotFoundException ex) {
    	logger.error(ex.getMessage(), ex);
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<List<MessageError>> handleUnprocessableEntityException(UnprocessableEntityException ex) {
    	logger.error(ex.getMessage(), ex);
    	return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getErrors());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception ex) {
    	logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
