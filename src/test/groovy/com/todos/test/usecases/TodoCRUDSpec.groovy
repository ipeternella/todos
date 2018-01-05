package com.todos.test.usecases

 import spock.lang.*
import com.todos.domain.Todo
import com.todos.errors.EntityNotFoundException
import com.todos.errors.MalformedQueryStringException
import com.todos.repository.TodoRepository
import com.todos.services.TodoService
import com.todos.usecases.TodoCRUD
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import com.todos.test.helpers.TestHelper
import java.util.HashMap;

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
    def "creating a todo"() {
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
    def "getting one todo by its mongo id"() {    
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
     * READ all todos (of a specific user) operation.
     */
    def "getting on todo in mongo by its user"() {
        HashMap<String, String> qryStrParams = new HashMap<String, String>()
        qryStrParams.putAt("userName", "someone")
        
        List<String> expectedParams = Arrays.asList("userName")
        
        when: "controller calls todoCRUD.findAll method with a query string an no exception is thrown"
            List<Todo> todoList = todoCRUD.findAll(qryStrParams);
            
        then: "should invoke todoService.findByUser"
            1 * todoService.findByUser(_) >> TestHelper.getDummyTodo(3) // returns a list of todos
    }
    
    /**
     * READ all todos (of a specific user) operation when a malformed query string is passed.
     */
    def "getting on todo in mongo by its user with a malformed query string"() {
        HashMap<String, String> qryStrParams = new HashMap<String, String>()
        qryStrParams.putAt("unexpectedParamName", "someone")
        
        List<String> expectedParams = Arrays.asList("userName")
        
        when: "controller calls todoCRUD.findAll method with a malformed query string"
            List<Todo> todoList = todoCRUD.findAll(qryStrParams);
            
        then: "should throw MalformedQueryStringException"
            thrown MalformedQueryStringException
    }
    
    
    /**
     * READ all todos operation.
     */
    def "getting all todos in mongo"() {        
        HashMap<String, String> qryStrParams = new HashMap<String, String>() // empty qryStr hashmap
        
        when: "controller calls todoCRUD.findAll method"
            ArrayList<Todo> todoList = todoCRUD.findAll(qryStrParams) // calls findAll with a null qryStrParams
            
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
    def "updating one todo in mongo"() {
        when: "controller calls todoCRUD.update"
            Todo todoUpdate = TestHelper.getDummyTodo() // fake Todo update from HTTP body                    
            Todo updatedTodo = todoCRUD.update(todoUpdate) // fake updated Todo
            
        then: "should invoke todoService.update method"
            1 * todoService.update(_) >> TestHelper.getDummyTodo()
        
        and: "should match the dummy todo"
            TestHelper.assertTodo(updatedTodo, TestHelper.getDummyTodo())
    } 
            
}
