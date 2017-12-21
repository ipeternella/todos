package com.todos.usecases

import spock.lang.Specification
import com.todos.domain.Todo
import com.todos.services.TodoService
import com.todos.repository.TodoRepository
import com.todos.usecase.CrudTodo
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule

class CrudTodoSpec extends Specification {

	/**
	* CREATE CRUD operation
	*/
	def "Test CRUD class - CREATE operation" () {
	
    		TodoService todoService = Mock(TodoService) // mocks service that calls the repository layer
		TodoRepository todoRepo = Mock(TodoRepository) // mocks the repository layer
		CrudTodo crudTodo = new CrudTodo(todoService: todoService)
		
		when: "I call crudTodo.create() [usecase layer]"
		
			// dummy todo for database insert							
			Todo dummyTodo = getDummyTodo() 
			
			// crudTodo.create() should invoke service.create()
			Todo createdTodo = crudTodo.create(dummyTodo)
							
		then: "Should invoke service.create()"
		
			1 * todoService.create(_) >> dummyTodo 
			
		and: "Should match the dummy Todo"
		
			assert createdTodo != null
			assert createdTodo.getId() == "000"			
			assert createdTodo.getUser() == "someone u dunno"
			assert createdTodo.getTask() == "walk the dog, dude!"
			assert createdTodo.isCompleted() == false 
			
		when: "I call todoService.create() [service layer]"
		
			// creates a non mock service with a mocked repository object
			todoService = new TodoService(todoRepo: todoRepo)
			
			// todoService.create() should invoke todoRepo.save()
			todoService.create(dummyTodo)
		
		then: "Should call todoRepo.save()"
		
			1 * todoRepo.save(_)
						  				 
	}
		
	/** 
	* READ operation
	*/ 
	def "Test CRUD class - READ operation" () {
	
    		TodoService todoService = Mock(TodoService) // mocks service that calls the repository layer
		TodoRepository todoRepo = Mock(TodoRepository) // mocks the repository layer
		CrudTodo crudTodo = new CrudTodo(todoService: todoService)
		
		// fake id
		String todoId = "5a3aedba63904306ca3f63a4"
		
		when: "I call crudTodo.findById() [usecase layer]"
						
			// crudTodo.create() should invoke service.findById()
			Todo createdTodo = crudTodo.findById(todoId)
							
		then: "Should invoke service.findById()"
			
			1 * todoService.findById(_) 
			
		when: "I call todoService.findById() [service layer]"
		
			// creates a non mock service with a mocked repository object
			todoService = new TodoService(todoRepo: todoRepo)
			
			// todoService.findById() should invoke todoRepo.findById()			
			todoService.findById(todoId)
		
		then: "Should call todoRepo.findById()"
				
			1 * todoRepo.findById(_)
						  				 
	}
			
	def getDummyTodo() {		
		Fixture.of(Todo.class).addTemplate("test", new Rule() {{
			add("id", "000"); 
			add("user", "someone u dunno");
			add("task", "walk the dog, dude!");
			add("completed", false);
		}});
				
		return Fixture.from(Todo.class).gimme("test")		
	}
}