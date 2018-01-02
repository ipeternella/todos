package com.todos.errors;

/**
 * Custom Exception class which is thrown when a malformed query string is received.
 *  
 * @author igp
 */

public class MalformedQueryStringException extends Exception {

    private static final long serialVersionUID = 1L;

    public MalformedQueryStringException(String msg) {
        super(msg);
    }
    
}
