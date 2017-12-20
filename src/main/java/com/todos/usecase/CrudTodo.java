package com.todos.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.services.TodoService;

// One usecase is the CRUD operation
@Service
public class CrudTodo {
	
	@Autowired
	private TodoService todoService;
	
	public Todo create (Todo todo) throws DataAccessException {				
		Todo createdTodo = todoService.create(todo);
		
		return createdTodo;
	}
	
}
