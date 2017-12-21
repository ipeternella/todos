package com.todos.errorhandlers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.todos.errors.ApiError;
import com.todos.errors.EntityNotFoundException;

/**
 * Class to handle Exceptions that are thrown by the controllers.
 * @author schaulerfuchs
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
    }
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable (HttpMessageNotReadableException ex, 
																   HttpHeaders headers, HttpStatus status, 
																   WebRequest request) {
		String error = "Malformed JSON request :(";
		
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}
			
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
		ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Something's wrong with our database :(", ex);
		
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Could not find what you were looking for :(", ex);
		
		return buildResponseEntity(apiError);
	}
	
}
