package com.todos.test.usecases

import spock.lang.*;
import com.todos.domain.Todo
import com.todos.errors.EntityNotFoundException
import com.todos.repository.TodoRepository
import com.todos.services.TodoService
import com.todos.usecases.TodoCRUD

import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule

/**
* Unit tests for the CRUD functionalities. Used for TDD development.
*
* @author igp
*/

class TodoCRUDSpec extends Specification {

	// fields
	TodoService todoService = Mock(TodoService) // mocks service that calls the repository layer
	TodoCRUD todoCRUD = new TodoCRUD(todoService: todoService)

	// feature methods
	
	/**
	* CREATE CRUD operation
	*/
	def "creating a todo" () {
		when: "controller calls todoCRUD.create method"		
			// dummy todo for database insert							
			Todo dummyTodo = getDummyTodo() 
			
			Todo createdTodo = todoCRUD.create(dummyTodo)
							
		then: "should invoke service.create()"		
			1 * todoService.create(_) >> dummyTodo 
			
		and: "service's result should match the createdTodo"
			matchesDummyTodo(createdTodo)
	}
		
	/** 
	* READ operation
	*/ 
	def "getting one todo by its mongo id" () {	
		when: "controller calls todoCRUD.findById method"
			// fake id
			String todoId = "1"									
			Todo foundTodo = todoCRUD.findById(todoId)
							
		then: "should invoke service.findById()"			
			1 * todoService.findById(_) >> getDummyTodo()
			
		and: "service's result should match the foundTodo"
			matchesDummyTodo(foundTodo)
		
	}
		
	// helper methods
		
	// helper function to create a dummy todo		
	def getDummyTodo() {		
		Fixture.of(Todo.class).addTemplate("test", new Rule() {{
			add("id", "101"); 
			add("user", "someone");
			add("task", "walk the dog");
			add("completed", false);
		}});
				
		return Fixture.from(Todo.class).gimme("test")		
	}
	
	// helper function to match a dummy todo
	void matchesDummyTodo(todo) {
		assert todo.getId() == "101"
		assert todo.getUser() == "someone"
		assert todo.getTask() == "walk the dog"
		assert todo.isCompleted() == false
	}
	
}
