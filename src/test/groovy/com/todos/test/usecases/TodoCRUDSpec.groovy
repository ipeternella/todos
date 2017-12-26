package com.todos.test.usecases

import spock.lang.*
import com.todos.domain.Todo
import com.todos.errors.EntityNotFoundException
import com.todos.repository.TodoRepository
import com.todos.services.TodoService
import com.todos.usecases.TodoCRUD
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import com.todos.test.helpers.TestHelper

/**
* Unit tests for the TodoCRUD class.
*
* @author igp
*/
class TodoCRUDSpec extends Specification {

    TodoService todoService = Mock(TodoService) // mocks service layer to isolate the functionality of the TodoCRUD class
    TodoCRUD todoCRUD = new TodoCRUD(todoService: todoService) // creates new TodoCRUD instance with mocked dependencies 
        
   /**
    * CREATE todo operation.
    */
    def "creating a todo" () {
        when: "controller calls todoCRUD.create method"        
            // dummy todo for database insert                                        
            Todo createdTodo = todoCRUD.create(TestHelper.getDummyTodo())
                            
        then: "should invoke service.create()"        
            1 * todoService.create(_) >> TestHelper.getDummyTodo()
                         
        and: "service's result should match the createdTodo"
            TestHelper.assertTodo(createdTodo, TestHelper.getDummyTodo())                          
    }
        
    /** 
    * READ one todo operation.
    */ 
    def "getting one todo by its mongo id" () {    
        when: "controller calls todoCRUD.findById method"
            // fake id
            String todoId = "1"                                    
            Todo foundTodo = todoCRUD.findById(todoId)
                            
        then: "should invoke service.findById method"            
            1 * todoService.findById(_) >> TestHelper.getDummyTodo()
            
        and: "service's result should match the foundTodo"
            TestHelper.assertTodo(foundTodo, TestHelper.getDummyTodo())        
    }
    
    /**
     * READ all todos operation.
     */
    def "getting all todos in mongo"() {
        when: "controller calls todoCRUD.findAll method"
            ArrayList<Todo> todoList = todoCRUD.findAll()
            
        then: "should invoke todoService.findAll"
            1 * todoService.findAll() >> TestHelper.getDummyTodo(3) // creates 3 fake todos
        
        and: "service's result should match the list of Todos"
            TestHelper.assertTodo(todoList, TestHelper.getDummyTodo(3))        
    }

    /**
     * DELETE one todo operation.
     */        
    def "deleting one todo in mongo"() {
        when: "controller calls todoCRUD.delete"
            // fake id
            String todoId = "1"
            todoCRUD.delete(todoId)
            
        then: "should invoke todoService.delete method"
            1 * todoService.delete(_)   
    }
    
    /**
     * UPDATE one todo operation.
     */
    def "updating one todo in mongo" () {
        when: "controller calls todoCRUD.update"
            Todo todoUpdate = TestHelper.getDummyTodo() // fake Todo update from HTTP body                    
            Todo updatedTodo = todoCRUD.update(todoUpdate) // fake updated Todo
            
        then: "should invoke TodoService.update method"
            1 * todoService.update(_) >> TestHelper.getDummyTodo()
        
        and: "should match the dummy todo"
            TestHelper.assertTodo(updatedTodo, TestHelper.getDummyTodo())
    }         
}
