package com.todos.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.services.TodoService;

/**
 * Class that represents the CRUD usecase of the todos.
 * Performs CRUD operations by calling the CRUD service.
 * 
 * @author igp
 */

@Service
public class CrudTodo {
	
	@Autowired
	private TodoService todoService;
	
	/*
	 * CREATE one todo operation.
	 */	
	public Todo create (Todo todo) throws DataAccessException {
		// uses a todo service to insert the todo object into mongoDB
		Todo createdTodo = todoService.create(todo);
		
		return createdTodo;
	}
	
	/*
	 * READ one todo operation.
	 */
	public Todo findById(String todoId) throws DataAccessException, EntityNotFoundException {
		// uses a todo service to find a the todo object in mongoDB
		Todo foundTodo = todoService.findById(todoId);
				
		return foundTodo;
	}
	
}
