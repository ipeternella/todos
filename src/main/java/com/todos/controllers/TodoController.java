package com.todos.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.usecases.TodoCRUD;

/**
 * Controller for the CRUD operations of the todos.
 * 
 * @author igp
 */

@RestController
@RequestMapping(value="/todos") 
public class TodoController {

	@Autowired
	private TodoCRUD todoCRUD;
	
	/*
	 * CREATE (CRUD) endpoint.
	 */	
	@RequestMapping(method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Todo> create(@RequestBody Todo inputTodo) throws DataAccessException, HttpMessageNotReadableException {
		// usecase to perform the create operation of the todo
		// malformed JSONs will throw HttpMessageNotReadableException which is handled by the error handler
		Todo createdTodo = todoCRUD.create(inputTodo);
		
		// logging
		System.out.println("[LOG] Created Todo in the database: " + createdTodo);

		// if the operation was successful, returns the newly created todo
		return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);		
	}
	
	/*
	 * READ one (CRUD) endpoint.
	 */
	@RequestMapping(value = "/{todoId}", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Todo> findById(@PathVariable(value="todoId") String todoId) throws DataAccessException, EntityNotFoundException {		
		// usecase to perform the read operation based on the id given by the path variable
		// throws custom EntityNotFoundException to return 404 http status if no todo was found in mongoDB
		Todo foundTodo = todoCRUD.findById(todoId);
		
		// if the operation was successful, returns the found todo
		return new ResponseEntity<>(foundTodo, HttpStatus.OK);
	}
	
	/*
	 * Read All (CRUD) endpoint.
	 */
	@RequestMapping(method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Todo>> findAll() throws DataAccessException, EntityNotFoundException {
		
		// usecase to read all the todos that are stored in mongoDB
		// throws custom EntityNotFoundException to return 404 http status if no todo was found in mongoDB
		List<Todo> todoList = todoCRUD.findAll();  
		
		return new ResponseEntity<>(todoList, HttpStatus.OK);
	}
	
	/*
	 * Delete (CRUD) endpoint.
	 */
	@RequestMapping(value = "/{todoId}",
					method = RequestMethod.DELETE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String todoId) throws DataAccessException, EntityNotFoundException {
		
		// use to delete a todo by its id
		todoCRUD.delete(todoId);
		
		// returns an empty body
		return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
	}
	
	/*
	 * Update (CRUD) endpoint.
	 */
	@RequestMapping(method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE)				
	public ResponseEntity<Todo> update(@RequestBody Todo todoUpdate) throws DataAccessException, EntityNotFoundException {
		Todo updatedTodo = todoCRUD.update(todoUpdate);
		
		return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
	}
}
