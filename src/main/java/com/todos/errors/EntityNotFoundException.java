package com.todos.errors;

/**
 * Custom Exception class which is thrown to generate an error message 
 * with a 404 http status for users.
 *  
 * @author igp
 */

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
    
}
