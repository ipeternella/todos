package com.todos.usecases;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.errors.MalformedQueryStringException;
import com.todos.services.TodoService;

/**
 * Class that represents the CRUD usecase of the todos.
 * Performs CRUD operations by calling the CRUD service.
 * 
 * @author igp
 */

@Service
public class TodoCRUD {
    
    @Autowired
    private TodoService todoService;
    
    /*
     * CREATE one todo operation.
     */    
    public Todo create(Todo todo) throws DataAccessException {
        // uses a todo service to insert the todo object into mongoDB
        Todo createdTodo = todoService.create(todo);
        
        return createdTodo;
    }
    
    /*
     * READ one todo operation.
     */
    public Todo findById(String todoId) throws DataAccessException, EntityNotFoundException {
        // uses a todo service to find a the todo object in mongoDB
        Todo foundTodo = todoService.findById(todoId);
                
        return foundTodo;
    }

    /*
     * READ all todos operation. Accepts a userName from a query string to filter results by a user.
     */    
    public List<Todo> findAll(Map<String, String> qryStrParams) throws DataAccessException, EntityNotFoundException, MalformedQueryStringException {
        List<Todo> todoList;
        
        if (qryStrParams.isEmpty()) {
            // invokes service.findAll method when no query string params are found in the request
            todoList = todoService.findAll();
        } else {
            // invokes findByUser with the query param userName or throws an exception if such parameter is not found
            todoList = todoService.findByUser(qryStrParams);
        }
        
        return todoList;
    }
    
    /*
     * DELETE one todo operation.
     */
    public void delete(String todoId) throws DataAccessException, EntityNotFoundException {
        // uses todo service to delete a todo which returns void
        todoService.delete(todoId);        
    }
    
    /*
     * UPDATE one todo operation.
     */
    public Todo update(Todo todoUpdate) throws DataAccessException, EntityNotFoundException {
        Todo updatedTodo = todoService.update(todoUpdate);
        
        return updatedTodo;
    }
    
}
