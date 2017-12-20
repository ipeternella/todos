package com.todos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todos.domain.Todo;
import com.todos.repository.TodoRepository;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository todoRepo;
	
	public Todo create(Todo todo) {
		Todo createdTodo = todoRepo.save(todo);
						
		return createdTodo;
	}
}
