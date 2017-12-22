package com.todos.errors;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that is used to generated customized JSON messages to users when exceptions occur.
 * 
 * @author igp
 */

public class ApiError {
	
	@Getter @Setter private HttpStatus status;        	 // HTTP status
	@Getter @Setter private int statusCode;		         // HTTP status number	
	@Getter @Setter private String message;           	 // user-friendly message about the error
	@Getter @Setter private String debugMessage;      	 // system error message to describe in a more specific way
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime timestamp;  	 // timestamp when an error occured
	
	private ApiError() {
       timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
    		this();
        this.status = status;
        this.statusCode = status.value();        
    }
    
    public ApiError(HttpStatus status, Throwable ex) {
    		this();
    		this.status = status;
    		this.statusCode = status.value();	
    		this.message = "Unexpected error";
    		this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
    		this();
    		this.status = status;
    		this.statusCode = status.value();	
    		this.message = message;
    		this.debugMessage = ex.getLocalizedMessage();
    }
 
}
