package com.todos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class used to represent a todo. Used on CRUD operations in the collection todos
 * of mongoDB.
 * 
 * @author igp
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection="todos") // collection specification for mongoDB
public class Todo {
	
	@Id
	private String id; // _id field for mongoDB

	private String user;
	private String task;
	private boolean completed;
		
}
