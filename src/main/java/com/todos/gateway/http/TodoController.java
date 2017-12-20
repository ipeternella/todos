package com.todos.gateway.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.todos.domain.Todo;
import com.todos.services.TodoService;
import com.todos.util.BusinessException;

@RestController
@RequestMapping(value="/todos") 
public class TodoController {
	
	@Autowired
	private TodoService todoService;
		
	@RequestMapping(method=RequestMethod.POST, 
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTodo(@RequestBody Todo inputTodo) {
				
		try {
			
			Todo createdTodo = todoService.create(inputTodo);
			System.out.println("[LOG] Created Todo in the database: " + inputTodo);

			return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
			
		} catch (Exception e) {
			
			System.out.println("[ERROR] Could not create resource on the database: " + e);
								
			return new ResponseEntity<>(new BusinessException(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
		}		
						
	}	
}
