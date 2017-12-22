package com.todos.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.repository.TodoRepository;

/**
 * Service class that performs CRUD operations by accessing the
 * repository layer of the application. 
 * 
 * @author igp
 */

@Service
public class TodoService { 

	@Autowired
	private TodoRepository todoRepo;
	
	/*
	 * Create todo service. 
	 */
	public Todo create(Todo todo) {		
		try {
			
			// inserts todo in the database by using the repository
			Todo createdTodo = todoRepo.save(todo);
			
			// logs the created todo
			System.out.println("[Todo]: " + createdTodo);
			
			return createdTodo;
			
		} catch (DataAccessException e) {
			
			// logs database-related exception
			System.out.println("[ERROR]: " + e.getMessage());
			
			throw (e);
		} 		
	}

	/*
	 * Read one todo service.
	 */
	public Todo findById(String todoId) throws DataAccessException, EntityNotFoundException {
		try {
			
			// finds todo based on the path variable id
			Todo foundTodo = todoRepo.findOne(todoId);			
			
			// throws custom exception if no todo was found to return 404 http status and not 200
			if (foundTodo == null) {
				throw new EntityNotFoundException("Todo was not found on the database with the id: " + todoId);
			}
			
			return foundTodo;
			
		} catch (DataAccessException e) {
			
			// logs database-related exception
			System.out.println("[ERROR]: " + e.getMessage());
			
			throw(e);
		}
	}
}
