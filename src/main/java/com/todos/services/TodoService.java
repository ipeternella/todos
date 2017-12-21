package com.todos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.repository.TodoRepository;

@Service
public class TodoService { 

	// logic of the application
	@Autowired
	private TodoRepository todoRepo;
		
	public Todo create(Todo todo) {		
		try {			
			Todo createdTodo = todoRepo.save(todo);
			
			return createdTodo;			
		} catch (DataAccessException e) {			
			System.out.println("[ERROR]: " + e.getMessage());
			
			throw (e);
		} 		
	}

	public Todo findById(String todoId) throws DataAccessException, EntityNotFoundException {
		try {			
			Todo foundTodo = todoRepo.findOne(todoId);			
			
			// if nothing is found
			if (foundTodo == null) {
				throw new EntityNotFoundException("Todo was not found on the database with the id: " + todoId);
			}
			
			return foundTodo;
		} catch (DataAccessException e) {
			System.out.println("[ERROR]: " + e.getMessage());
			
			throw(e);
		}
	}
}
