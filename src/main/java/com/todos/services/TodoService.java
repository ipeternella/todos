package com.todos.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
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

    /**
     * Creates a new todo.
     * 
     * @param todo a todo instance created from an HTTP POST payload
     * @return createdTodo a newly created todo
     * @throws DataAccessException if a database connection problem happens
     */
    public Todo create(Todo todo) throws DataAccessException {        
        try {
            
            // inserts todo in the database by using the repository
            Todo createdTodo = todoRepo.save(todo);
            
            // logs the created todo
            System.out.println("[LOG] Created todo: " + createdTodo);
            
            return createdTodo;
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw (e);
        }         
    }

    /**
     * Reads one todo by its MongoDB id.
     * 
     * @param todoId
     * @return foundTodo a todo found by its id
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo was found for the given id
     */
    public Todo findById(String todoId) throws DataAccessException, EntityNotFoundException {
        try {
            
            // finds a todo by its id
            Todo foundTodo = todoRepo.findOne(todoId);            
            
            // if no todo was found, throws EntityNotFoundException to send a 404 http status
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
     * Reads all the todos in MongoDB of a given user.
     * 
     * @param userName a username paramter sent in a query string
     * @return todoList a list of todos that belongs to a specific user
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todos are found for a given user
     */
    public List<Todo> findByUser(String userName) throws DataAccessException, EntityNotFoundException {         
        try {
            // finds all the todos for a given user
            List<Todo> todoList = todoRepo.findByUser(userName);
            
            // if no todos were found for that user, throws EntityNotFoundException to send a 404 http status
            if (todoList.isEmpty()) {
                throw new EntityNotFoundException("No todos found in the database for the user: " + userName + ". Maybe he's not busy?");
            }
            
            return todoList;
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);        
        }
    }

    /**
     * Reads all the todos. No filtering by username.
     *  
     * @return todoList a list of all todos found in the database
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todos are found in the database
     */
    public List<Todo> findAll() throws DataAccessException, EntityNotFoundException {
        try {

            // finds all the todos in mongoDB
            List<Todo> todoList = todoRepo.findAll();
            
            // if no todos were found in the database, throws EntityNotFoundException
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
    
    /**
     * Deletes one todo by its id.
     * 
     * @param todoId a String which contains the id of the todo to be deleted
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo was found to be deleted with the given id
     */
    public void delete(String todoId) throws DataAccessException, EntityNotFoundException {
        try {
            
            // finds the todo
            Todo toBeDeleted = todoRepo.findOne(todoId);
            
            // if the todo cannot be found, throws EntityNotFoundException to send a 404 http status
            if (toBeDeleted == null) {
                throw new EntityNotFoundException("No todo was found to be deleted :(");
            }
            
            // if the todo was found, deletes the todo
            todoRepo.delete(toBeDeleted);
            
            // logs
            System.out.println("[LOG]: " + " deleted todo: " + toBeDeleted);
            
        } catch (DataAccessException e) {
            
            // logs database-related exception
            System.out.println("[ERROR]: " + e.getMessage());
            
            // proceeds to throw the exception
            throw(e);
        }        
    }

    /**
     * Updates one todo by its id.
     * 
     * @param todoUpdate a todo instance create from an HTTP PUT payload which holds the new data to update the id 
     * @return todoUpdate the newly updated todo
     * @throws DataAccessException if a database connection problem happens
     * @throws EntityNotFoundException if no todo to be updated was found
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
            
            // updates the todo by executing an upsert in MongoDB
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


}
