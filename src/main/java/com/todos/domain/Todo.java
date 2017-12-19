package com.todos.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Todo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
