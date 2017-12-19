package com.todos.repository;

import org.springframework.data.repository.CrudRepository;
import com.todos.domain.Todo;

// must reach mongoDB
public interface TodoRepository extends CrudRepository<Todo, String>{

}
