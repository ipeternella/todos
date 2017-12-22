package com.todos.usecase;

import java.util.List;

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
	
	/*
	 * READ all todos operation.
	 */	
	public List<Todo> findAll() throws DataAccessException, EntityNotFoundException {
		List<Todo> todoList = todoService.findAll();
		
		return todoList;
	}
	
	/*
	 * DELETE one todo operation.
	 */
	public void delete(String todoId) throws DataAccessException, EntityNotFoundException {
		// uses todo service to delete a todo which returns void
		todoService.delete(todoId);		
	}
	
	/*
	 * UPDATE one todo operation.
	 */
	public Todo update(Todo todoUpdate) throws DataAccessException, EntityNotFoundException {
		Todo updatedTodo = todoService.update(todoUpdate);
		
		return updatedTodo;
	}
}
