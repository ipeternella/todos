package com.todos.usecases

import spock.lang.Specification
import com.todos.domain.Todo
import com.todos.services.TodoService
import com.todos.repository.TodoRepository

class CrudTodoSpec extends Specification {

	TodoRepository todoRepo = Mock(TodoRepository)
	TodoService todoService = new TodoService("todoRepo": todoRepo) 
	
	def "Todo Creation Test"() {
		given: "a todo that came from an HTTP POST payload"
			Todo dummyTodo = getDummyTodo()
			Todo createdTodo = todoService.create(dummyTodo)
							
		when: "During Todos creation"
			1 * todoService.create(_)
			
		then: "Returned new todo"
			assert createdTodo != null
			assert createdTodo.getId() == "100"			
			assert createdTodo.getUser() == "someone u dunno"
			assert createdTodo.getTask() == "walk the dog, dude!"
			assert createdTodo.isCompleted() == false 
	} 
	
	def getDummyTodo() {
		Todo dummyTodo = new Todo("000", "someone u dunno", "walk the dog, dude!", false)
		
		return dummyTodo		
	}
}