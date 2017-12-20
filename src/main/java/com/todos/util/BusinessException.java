package com.todos.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message) {
		super(message);
	}
	
}
