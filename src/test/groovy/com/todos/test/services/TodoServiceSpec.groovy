package com.todos.test.services

import spock.lang.Specification
import com.todos.repository.TodoRepository
import com.todos.services.TodoService

import java.nio.charset.MalformedInputException

import com.todos.domain.Todo
import com.todos.test.helpers.TestHelper
import com.todos.errors.EntityNotFoundException
import com.todos.errors.MalformedQueryStringException

/**
* Unit tests for the TodoService class.
*
* @author igp
*/
class TodoServiceSpec extends Specification {
    
    TodoRepository todoRepo = Mock(TodoRepository) // mocks repository layer to isolate the functionality of the TodoService class
    TodoService todoService = new TodoService(todoRepo: todoRepo) // creates new TodoService instance with mocked dependencies

    /**
     * CREATE a todo service.
     */
    def "creating a todo with mongo repository"() {
        when: "todoCRUD invokes todoService.create method"
            Todo createdTodo = todoService.create(TestHelper.getDummyTodo())
        
        then: "should invoke todoRepo.save method" 
            1 * todoRepo.save(_) >> TestHelper.getDummyTodo()
            
        and: "assert created todo"
            TestHelper.assertTodo(createdTodo, TestHelper.getDummyTodo())
    }
    
    /**
     * GET one todo service.
     */        
    def "getting a todo with mongo repository"() {
        // fake id
        String todoId = "1"
                
        when: "todoCRUD invokes todoService.findById"
            Todo foundTodo = todoService.findById(todoId)
            
        then: "should invoke todoRepo.findOne"
            1 * todoRepo.findOne(_) >> TestHelper.getDummyTodo()            
            
        and: "assert found todo"
           TestHelper.assertTodo(foundTodo, TestHelper.getDummyTodo())                  
    }
    
    /**
     * GET one todos service when the todo is not found in mongoDB.
     */    
    def "getting a todo with mongo repository WITHOUT any results"() {
        String todoId = "1"
        
        when: "todoCRUD invokes todoService.findById and NO todo is found"
            Todo foundTodo = todoService.findById(todoId)
            
        then: "todoRepo returns null and EntityNotFoundException is thrown " +
              "to send a customized user-friendly JSON message handled by Spring"
             1 * todoRepo.findOne(_) >> null // query for one todo returns NULL when nothing is found
             
             thrown EntityNotFoundException
    }
    
    /**
     * GET all todos service (no username filter).
     */    
    def "getting ALL todos with mongo repository"() {
        // number of dummy todos returned
        def NUMBER_OF_RETURNED_TODOS = 3
                
        when: "todoCRUD invokes todoService.findById"
            List<Todo> todoList = todoService.findAll()
            
        then: "should invoke todoRepo.findOne and return 3 dummy Todos"
            1 * todoRepo.findAll() >> TestHelper.getDummyTodo(NUMBER_OF_RETURNED_TODOS)            
            
        and: "assert found todo"
           TestHelper.assertTodo(todoList, TestHelper.getDummyTodo(NUMBER_OF_RETURNED_TODOS))                  
    }    
    
    /**
     * GET all todos service when no todos are found in mongoDB (no username filter).
     */      
    def "getting ALL todos with mongo repository WITHOUT any results"() {
        when: "todoCRUD invokes todoService.findById and NO todos are found"
            List<Todo> foundTodo = todoService.findAll()
            
        then: "todoRepo returns null and EntityNotFoundException is thrown " +
              "to send a customized user-friendly JSON message handled by Spring"
              
            1 * todoRepo.findAll() >> new ArrayList<Todo>() // query for all todos returns an EMPTY 
            
            thrown EntityNotFoundException
    }
                
    /**
     * GET all todos service filtered by a username.
     */    
    def "getting ALL todos with mongo repository filtered by a username"() {
        HashMap<String, String> qryStrParams = new HashMap<String, String>()
        qryStrParams.putAt("userName", "someone")
        
        // number of dummy todos returned
        int NUMBER_OF_RETURNED_TODOS = 3
                
        when: "todoCRUD invokes todoService.findByUser with the query string params"
            List<Todo> todoList = todoService.findByUser(qryStrParams)
            
        then: "should invoke todoRepo.findByUser and return 3 dummy Todos"
            1 * todoRepo.findByUser(_) >> TestHelper.getDummyTodo(NUMBER_OF_RETURNED_TODOS)            
            
        and: "assert found todos"
           TestHelper.assertTodo(todoList, TestHelper.getDummyTodo(NUMBER_OF_RETURNED_TODOS))                  
    }
    
    /**
     * GET all todos service filtered by a username when a malformed query string is passed.
     */    
    def "getting ALL todos with mongo repository with a malformed query string"() {
        HashMap<String, String> qryStrParams = new HashMap<String, String>()
        qryStrParams.putAt("wrongParamName", "someone") // query string with a wrong parameter name
        
        // number of dummy todos returned        
        def NUMBER_OF_RETURNED_TODOS = 3
                
        when: "todoCRUD invokes todoService.findByUser with a wrong query string parameter name"
            List<Todo> todoList = todoService.findByUser(qryStrParams)
            
        then: "should throw MalformedQueryStringException due to the unknown query string parameter"                        
            thrown MalformedQueryStringException 
    }
    
    /**
     * GET all todos service filtered by a username when the username does NOT exist.
     */    
    def "getting ALL todos with mongo repository filtered by a username when it does NOT exist"() {
        HashMap<String, String> qryStrParams = new HashMap<String, String>()
        qryStrParams.putAt("userName", "someone") // appropriate parameter name
        
        // number of dummy todos returned        
        def NUMBER_OF_RETURNED_TODOS = 3
                
        when: "todoCRUD invokes todoService.findByUser"
            List<Todo> todoList = todoService.findByUser(qryStrParams)
            
        then: "should try to find the todos of the given user but fails and throws an EntityNotFoundException"
            1 * todoRepo.findByUser(_) >> new ArrayList<Todo>() // empty list (no todos for the given userName of the query string)            
            
            thrown EntityNotFoundException 
    }
    
    /**
     * DELETE one todo service.
     */  
    def "deleting one todo with mongo repository"() {
        String todoId = "1"
        
        when: "todoCRUD invokes todoService.delete"
            todoService.delete(todoId) // void method
            
        then: "todoRepo should try to find the todo first and then delete it"
            1 * todoRepo.findOne(_) >> TestHelper.getDummyTodo()
            1 * todoRepo.delete(_) 
    }
    
    /**
     * DELETE one todos service when the todo is not found in mongoDB.
     */  
    def "deleting one todo with mongo repository that does NOT exist"() {
        String todoId = "1"
        
        when: "todoCRUD invokes todoService.delete"
            todoService.delete(todoId) // void method
            
        then: "todoRepo should try to find the todo but fails and throws EntityNotFoundException"
            1 * todoRepo.findOne(_) >> null
             
            thrown EntityNotFoundException
    }
        
    /**
     * UPDATE one todo service.
     */  
    def "updating one todo with mongo repository"() {
        when: "todoCRUD invokes todoService.update with a todo from a POST payload"
            Todo updatedTodo = todoService.update(TestHelper.getDummyTodo())
            
        then: "todoRepo should try to find the todo first and then update it with save method (upsert)"
            1 * todoRepo.findOne(_) >> TestHelper.getDummyTodo()
            1 * todoRepo.save(_) 
        
        and: "assert updated todo"
            TestHelper.assertTodo(updatedTodo, TestHelper.getDummyTodo())              
    }
    
        
    /**
     * UPDATE one todo service when the todo is not found in mongoDB.
     */  
    def "updating one todo with mongo repository that does NOT exist"() {
        when: "todoCRUD invokes todoService.update with a todo from a POST payload"
            Todo updatedTodo = todoService.update(TestHelper.getDummyTodo())
            
        then: "todoRepo should try to find the todo but fails and throws EntityNotFoundException"
            1 * todoRepo.findOne(_) >> null
            
            thrown EntityNotFoundException        
    }
 
}
