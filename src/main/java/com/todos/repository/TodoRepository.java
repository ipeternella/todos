package com.todos.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.todos.domain.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
	
	public Todo findById(ObjectId id);
}
