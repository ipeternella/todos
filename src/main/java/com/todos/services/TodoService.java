package com.todos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.errors.MalformedQueryStringException;
import com.todos.repository.TodoRepository;

/**
 * Service class that performs CRUD operations by accessing the
 * repository layer of the application. 
 * 
 * @author igp
 */

@Service
public class TodoService { 

    @Autowired
    private TodoRepository todoRepo;
    
    /*
     * Create todo service. 
     */
    public Todo create(Todo todo) throws DataAccessException {        
        try {
            
            // inserts todo in the database by using the repository
            Todo createdTodo = todoRepo.save(todo);
            
            // logs the created todo
            System.out.println("[LOG] Created todo: " + createdTodo);
            
            return createdTodo;
            
        } catch (DataAccessException e) { // catches database-related exceptions
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw (e);
        }         
    }

    /*
     * Read-one todo service.
     */
    public Todo findById(String todoId) throws DataAccessException, EntityNotFoundException {
        try {
            
            // finds todo based on the path variable id
            Todo foundTodo = todoRepo.findOne(todoId);            
            
            // throws custom exception if no todo was found in mongoDB to return a 404 http status
            if (foundTodo == null) {
                throw new EntityNotFoundException("Todo was not found in the database with the id: " + todoId);
            }
            
            return foundTodo;
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);
        }
    }
    
    /**
     * Read-all-todos-of-a-user service. 
     */
    public List<Todo> findByUser(Map<String, String> qryStrParams) throws DataAccessException, EntityNotFoundException, MalformedQueryStringException {         
        // array list to store necessary query string params
        ArrayList<String> requiredParams = new ArrayList<String>();
        
        // this service requires a userName param which is supplied in a query string
        requiredParams.add("userName");
        
        try {
            // checks if any unexpected query string params are present (anything that is not the parameter userName)
            ArrayList<String> unexpectedParams = getUnexpectedQryStrParams(qryStrParams, requiredParams);
            
            // throws custom exception with error 422 if an unexpected param has been found
            if (!unexpectedParams.isEmpty()) {
                throw new MalformedQueryStringException("Could not process the request with unexpected query string params: " + unexpectedParams);
            }
            
            // if all query string params are okay, proceeds to find all todos in mongoDB from the user
            List<Todo> todoList = todoRepo.findByUser(qryStrParams.get("userName"));
            
            // throws custom exception if no todo was found in mongoDB for that user 
            if (todoList.isEmpty()) {
                throw new EntityNotFoundException("No todos found in the database for the user: " + qryStrParams.get("userName") + ". Maybe he's not busy?");
            }
            
            return todoList;
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);        
        }
    }
    
    /*
     * Read-all todo service.
     */    
    public List<Todo> findAll() throws DataAccessException, EntityNotFoundException {
        try {
            
            // finds all todos in mongoDB
            List<Todo> todoList = todoRepo.findAll();
            
            // throws custom exception if no todo was found in mongoDB to return 404 http status 
            if (todoList.isEmpty()) {
                throw new EntityNotFoundException("No todos found in the database, don't you have anything to do?");
            }            
            
            return todoList;
            
         } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);        
        }
    }
    
    /*
     * Delete todo service.
     */
    public void delete(String todoId) throws DataAccessException, EntityNotFoundException {
        try {
            // finds the todo
            Todo toBeDeleted = todoRepo.findOne(todoId);
            
            // checks if the todo really exists
            // if the it doesn't, throws custom exception if no todo was found in mongoDB to return 404 http status
            if (toBeDeleted == null) {
                throw new EntityNotFoundException("No todo was found to be deleted :(");
            }
            
            // deletes the todo
            todoRepo.delete(toBeDeleted);
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);
        }        
    }
    
    /*
     * Update todo service.
     */
    public Todo update(Todo todoUpdate) throws DataAccessException, EntityNotFoundException {
        try {
            // finds the todo to be updated
            Todo toBeUpdated = todoRepo.findOne(todoUpdate.getId());
            
            // checks if the todo really exists
            // if the it doesn't, throws custom exception if no todo was found in mongoDB to return 404 http status
            if (toBeUpdated == null) {
                throw new EntityNotFoundException("Todo was not found to be updated :(");
            }
            
            // updates the todo by executing an upsert in mongoDB
            todoRepo.save(todoUpdate);
            
            // logs the updated todo
            System.out.println("[LOG] Updated todo: " + todoUpdate);
            
            return todoUpdate;
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);
        }        
    }

    /*
     * Helper method to validate wrong query parameters sent via HTTP requests. Returns all an ArrayList of unexpected parameters.
     */
    private ArrayList<String> getUnexpectedQryStrParams(Map<String, String> qryStrParams, ArrayList<String> requiredParams) {         
        // ArrayList to hold unexpected query string params
        ArrayList<String> unexpectedParams = new ArrayList<String>();
                        
        for (String param : qryStrParams.keySet()) {
            // adds the parameter if its not a required (expected) one
            if (!requiredParams.contains(param)) {
                unexpectedParams.add(param);
            }
        }
        
        return unexpectedParams;
    }
    
}
