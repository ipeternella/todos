package com.todos.gateway.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todos.domain.Todo;
import com.todos.usecases.CrudTodo;

// calls CrudTodos 

@RestController
@RequestMapping(value="/todos")
public class TodoController {
	
//	@Autowired
//	CrudTodo crudTodo; 
	
	@RequestMapping(method=RequestMethod.GET)
	public Todo test() {
		Todo todo = new Todo("abc", "igor", "Wash the dishes", false);
		
		return todo;
	}
}
