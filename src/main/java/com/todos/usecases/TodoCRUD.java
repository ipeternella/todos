package com.todos.usecases;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    /**
     * Creates a new todo by calling the create service.
     * 
     * @param todo a todo instance created from an HTTP POST payload
     * @return createdTodo a newly created todo 
     * @throws DataAccessException if a database connection problem happens
     */
    public Todo create(Todo todo) throws DataAccessException {
        // calls a todo service to insert the todo object into mongoDB
        Todo createdTodo = todoService.create(todo);
        
        return createdTodo;
    }
        
    /**
     * Reads one todo by its id by calling the read service.
     * 
     * @param todoId a String which contains the id of the todo
     * @return foundTodo a found todo by its id
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo was found with the given id
     */
    public Todo findById(String todoId) 
            throws DataAccessException, EntityNotFoundException {
        // uses a todo service to find a the todo object in mongoDB
        Todo foundTodo = todoService.findById(todoId);
                
        return foundTodo;
    }

    /**
     * Reads all the todos by calling the find all or find by user service. 
     * Optionally accepts a query string with the userName parameter to filter the results by the user.
     * 
     * @param qryStrParams a Map which should have the key "userName" which is the only allowed query string parameter
     * @return todoList a list with all the todos or all the todos of a given user 
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo was found in the database or if no todo was found for a given user
     * @throws MalformedQueryStringException if the HTTP request contained 
     *  a query string with unexpected parameters (anything besides the userName parameter)
     */
    public List<Todo> findAll(Map<String, String> qryStrParams) 
            throws DataAccessException, EntityNotFoundException, MalformedQueryStringException {
        // declares a list to hold all the todos
        List<Todo> todoList;
        
        if (qryStrParams.isEmpty()) {
            // if the client sends no query string parameters, it calls the service.findAll method 
            todoList = todoService.findAll();
        } else {
            // if the client sends a query string, then it must be validated
            // creates a list with the expected query string params for this endpoint (userName parameter only)
            List<String> expectedParams = Arrays.asList("userName");
            
            // validates the query string by making sure that only the parameter userName was sent
            // unexpected parameters throws MalformedQueryStringException
            validateQryStr(qryStrParams, expectedParams);
            
            // if the validation was ok, calls the service
            todoList = todoService.findByUser(qryStrParams.get("userName"));
        }
        
        return todoList;
    }

    /**
     * Deletes one todo by its id by calling the delete service.
     * 
     * @param todoId a String which holds the id of the todo that will be deleted
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo with such id was found to be deleted
     */
    public void delete(String todoId) 
            throws DataAccessException, EntityNotFoundException {
        // uses todo service to delete a todo which returns void
        todoService.delete(todoId);        
    }

    /**
     * Updates one todo
     * 
     * @param todoUpdate a todo instance created from an HTTP PUT payload which holds the new data to update the todo 
     * @return updatedTodo the newly updated todo
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo with the given id was found to be updated
     */
    public Todo update(Todo todoUpdate) 
            throws DataAccessException, EntityNotFoundException {
        Todo updatedTodo = todoService.update(todoUpdate);
        
        return updatedTodo;
    }
    
    
    /*
     * Private helper method that is used to see if the query string of an HTTP request contains only expected parameters.
     * 
     * Throws MalformedQueryStringException if unexpected parameters are found in the query string
     */
    private void validateQryStr(Map<String, String> qryStrParams, List<String> expectedParams) 
            throws MalformedQueryStringException {         
        // list to hold unexpected query string params
        List<String> unexpectedParams = new ArrayList<String>();

        // traverses query string parameters to check for unexpected ones
        for (String qryStrParam : qryStrParams.keySet()) {
            // if a query string parameter in the request is not expected
            if (!expectedParams.contains(qryStrParam)) {
                // adds unexpected parameter to a list of unknown parameters
                unexpectedParams.add(qryStrParam);
            }
        }
        
        // if unexpected params are found, throws an exception which shows the unexpected parameters found
        if (!unexpectedParams.isEmpty()) {
            throw new MalformedQueryStringException("Could not process the request with the following unexpected" + 
                                                    " query string parameters: " + unexpectedParams + "." + 
                                                    " This endpoint accepts only the following parameters: " + expectedParams);
        }
        
    }
}