package com.todos.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Todo {
	
	@Id
	private String id;
	
	private String user;
	private String task;
	private boolean completed;
	
	public Todo() {
		
	}
	
	public Todo(String id, String user, String task, boolean completed) {
		super();
		
		this.id = id;
		this.user = user;
		this.task = task;
		this.completed = completed;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", user=" + user + ", task=" + task + ", completed=" + completed + "]";
	}
	
	
	
}
