package com.todos.gateway.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.todos.domain.Todo;
import com.todos.usecase.CrudTodo;

@RestController
@RequestMapping(value="/todos") 
public class TodoController {

	@Autowired
	private CrudTodo crudTodo;
		
	@RequestMapping(method=RequestMethod.POST, 
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTodo(@RequestBody Todo inputTodo) throws DataAccessException {
							
		Todo createdTodo = crudTodo.create(inputTodo);
		System.out.println("[LOG] Created Todo in the database: " + createdTodo);

		return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);		
	}
		
}
