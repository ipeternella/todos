package com.todos.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
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

	public Todo findById(String todoId) throws DataAccessException {
		try {
			Todo foundTodo = todoRepo.findById(new ObjectId(todoId));
			
			return foundTodo;
		} catch (DataAccessException e) {
			System.out.println("[ERROR]: " + e.getMessage());
			
			throw(e);
		}
	}
}
