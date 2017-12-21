package com.todos.errors;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}
	
}
