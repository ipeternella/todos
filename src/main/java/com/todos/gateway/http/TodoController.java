package com.todos.gateway.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.todos.domain.Todo;
import com.todos.services.TodoService;

@RestController
@RequestMapping(value="/todos") 
public class TodoController {
	
	@Autowired
	private TodoService todoService;
		
	@RequestMapping(method=RequestMethod.POST, 
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public Todo createTodo(@RequestBody Todo inputTodo) {
		System.out.println("Creating todo...");
		System.out.println(inputTodo);
		
		Todo createdTodo = todoService.create(inputTodo);
				
		return createdTodo;
	}	
}
