package com.todos.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.todos.domain.Todo;
import com.todos.errors.EntityNotFoundException;
import com.todos.errors.MalformedQueryStringException;
import com.todos.usecases.TodoCRUD;

/**
 * Controller for the CRUD operations of the todos.
 *
 * @author igp
 */

@RestController
@RequestMapping(value = "api/todos")
public class TodoController {

    @Autowired private TodoCRUD todoCRUD;

    /**
     * Create a todo endpoint.
     * 
     * @param inputTodo a todo instance created from an HTTP POST payload
     * @return JSON of the created todo with a 201 HTTP status if it was successful 
     * @throws DataAccessException if a database connection problem happens
     *  which is handled to send a JSON with a 500 HTTP status to the client 
     * @throws HttpMessageNotReadableException if a malformed JSON is sent by the client
     *  which is handled to send a JSON with a 400 HTTP status to the client
     */
    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> create(@RequestBody Todo inputTodo)
            throws DataAccessException, HttpMessageNotReadableException {
        // creates a new todo or throws and HttpMessageNotReadableException if a malformed JSON is received 
        Todo createdTodo = todoCRUD.create(inputTodo);

        // logs the newly created todo
        System.out.println("[LOG] Created Todo in the database: " + createdTodo);

        // if the operation was successful, returns the newly created todo
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    /**
     * Read one todo by its id endpoint.
     * 
     * @param todoId a path variable String which holds the id of the todo
     * @return JSON of the found todo with a 200 HTTP status
     * @throws DataAccessException if a database connection problem happens
     *  which is handled to send a JSON with a 500 HTTP status to the client
     * @throws EntityNotFoundException if no todo was found for the given id
     *  which is handled to send a JSON with a 404 HTTP status to the client
     */
    @RequestMapping(value = "/{todoId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> findById(@PathVariable(value = "todoId") String todoId)
            throws DataAccessException, EntityNotFoundException {
        // finds the todo
        Todo foundTodo = todoCRUD.findById(todoId);

        // if the operation was successful, returns the found todo
        return new ResponseEntity<>(foundTodo, HttpStatus.OK);
    }
    
    /**
     * Reads all the todos in MongoDB endpoint. 
     * Optionally accepts a query string with the parameter userName to filter the results by a user name.
     * 
     * @param qryStrParams a Map implementation which can be null (no query string) or contain the "userName" key
     * @return JSON with the list of found todos with a 200 HTTP status
     * @throws DataAccessException if a database connection problem happens
     *  which is handled to send a JSON with a 500 HTTP status to the client
     * @throws EntityNotFoundException if no todo was found for the given id
     *  which is handled to send a JSON with a 404 HTTP status to the client 
     * @throws MalformedQueryStringException if any query string parameter other than "userName" was sent
     *  which is handled to send a JSON with a 422 HTTP status to the client
     */
    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Todo>> findAll(@RequestParam Map<String, String> qryStrParams) throws DataAccessException, EntityNotFoundException, MalformedQueryStringException {
        // finds all todos 
        // query string parameters can be an empty Map (no username filter)
        List<Todo> todoList = todoCRUD.findAll(qryStrParams); 

        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    /**
     * Deletes one todo endpoint.
     * 
     * @param todoId a path variable String which holds the id of the todo
     * @return an empty JSON with a 204 http status
     * @throws DataAccessException if a database connection problem happens
     *  which is handled to send a JSON with a 500 HTTP status to the client
     * @throws EntityNotFoundException if no todo was found for the given id
     *  which is handled to send a JSON with a 404 HTTP status to the client 
     */
    @RequestMapping(value = "/{todoId}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable String todoId)
            throws DataAccessException, EntityNotFoundException {

        // usecase to delete a todo by its id
        todoCRUD.delete(todoId);

        // returns an empty body
        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

    /**
     * Updates one todo endpoint.
     * 
     * @param todoUpdate a todo instance created from HTTP PUT payload
     * @return JSON with the updated todo with a 200 HTTP status
     * @throws DataAccessException if a database connection problem happens
     *  which is handled to send a JSON with a 500 HTTP status to the client
     * @throws EntityNotFoundException if no todo was found for the given id
     *  which is handled to send a JSON with a 404 HTTP status to the client
     */
    @RequestMapping(method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> update(@RequestBody Todo todoUpdate)
            throws DataAccessException, EntityNotFoundException {
        Todo updatedTodo = todoCRUD.update(todoUpdate);

        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
}
