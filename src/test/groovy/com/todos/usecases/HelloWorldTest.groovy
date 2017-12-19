package com.todos.usecases

import com.todos.domain.Todo
import spock.lang.Specification

class MyTest extends Specification {
		
	def "test"() {
		when: "i start"
			Todo myTodo = new Todo("abc", "igor", "Wash the dishes", false)
			
		then: "toDo created"
			assert myTodo.getUser() == "igorxxx"		 
	} 
}