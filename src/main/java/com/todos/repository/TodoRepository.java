package com.todos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.todos.domain.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
	
}
