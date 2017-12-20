package com.todos.usecases

import spock.lang.Specification
import com.todos.domain.Todo
import com.todos.services.TodoService
import com.todos.repository.TodoRepository

class CrudTodoSpec extends Specification {

	TodoRepository todoRepo = Mock(TodoRepository)
	TodoService todoService = new TodoService("todoRepo": todoRepo)
		
	def "Should INSERT a new todo in the database by invoking TodoService.create"() {
		when: "I call TodoService.create()"		
			Todo dummyTodo = getDummyTodo()
			Todo createdTodo = todoService.create(dummyTodo)
							
		then: "Should invoke Repository save()"
			1 * todoRepo.save(_) >> dummyTodo // fakes a Todo return to continue testing todoService.create() 
			
		and: "Should match the dummy Todo"
			assert createdTodo != null
			assert createdTodo.getId() == "000"			
			assert createdTodo.getUser() == "someone u dunno"
			assert createdTodo.getTask() == "walk the dog, dude!"
			assert createdTodo.isCompleted() == false		 
	} 	
	
	def getDummyTodo() {
		Todo dummyTodo = new Todo("000", "someone u dunno", "walk the dog, dude!", false)
		
		return dummyTodo		
	}
}