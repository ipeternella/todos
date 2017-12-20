package com.todos.usecases

import spock.lang.Specification
import com.todos.domain.Todo
import com.todos.services.TodoService
import com.todos.repository.TodoRepository
import com.todos.usecase.CrudTodo
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule

class CrudTodoSpec extends Specification {
		
	def "Test CrudTodo class" () {
	    TodoService todoService = Mock(TodoService)
		CrudTodo crudTodo = new CrudTodo(todoService: todoService)
	
		when: "I call crudTodo.create()"		
			Todo dummyTodo = getDummyTodo()
			Todo createdTodo = crudTodo.create(dummyTodo)
							
		then: "Should invoke service.create()"
			1 * todoService.create(_) >> dummyTodo // crudTodo.create() should invoke service.create() 
			
		and: "Should match the dummy Todo"
			assert createdTodo != null
			assert createdTodo.getId() == "000"			
			assert createdTodo.getUser() == "someone u dunno"
			assert createdTodo.getTask() == "walk the dog, dude!"
			assert createdTodo.isCompleted() == false		 
	}
	
	def "Test TodoService class" () {
		TodoRepository todoRepo = Mock(TodoRepository)
		TodoService todoService = new TodoService(todoRepo: todoRepo)
				
		when: "I call todoService.create()"
			Todo dummyTOdo = getDummyTodo() 
			todoService.create(dummyTodo)
			
		then: "should invoke MongoRepository.save()"
			1 * todoRepo.save(_)  
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