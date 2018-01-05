# ToDo's API - Never forget anything anywhere anymore!
* This demo project implements a RESTful API that allow users to manage their daily life tasks (To-Dos).

## Spring Powered
* Based on the Spring framework;
* Implements CRUD functionalities for todos;
* Uses mongoDB as the main database (but also has the option to run an embedded mongoDB version).

## TDD + BDD developed
* Backend was written using TDD and BDD software development philosophies;
* Groovy language was used to write unit tests with the spock testing framework.

## Coming Sooon
* DevOps + deployment!
* Middleware based on Node.js!
* Frontend with React!

## API Examples

* This section shows some examples of the API running on localhost.

### GET routes

* To get all the todos in the database: ```http://localhost:8080/api/todos```

API response example **(HTTP status: 200)**:

```json 
[
    {
        "id": "5a4bec46fc99700603a47e39",
        "user": "someone",
        "task": "walk the dog",
        "completed": false
    },
    {
        "id": "5a4bee08fc99700603a47e3a",
        "user": "someone",
        "task": "do the laundry",
        "completed": false
    },
    {
        "id": "5a4bee18fc99700603a47e3b",
        "user": "someone",
        "task": "walk the dog",
        "completed": false
    },
    {
        "id": "5a4bee3dfc99700603a47e3c",
        "user": "someone_else",
        "task": "go to the gym",
        "completed": false
    }
]
```

* If there are no todos in the database, one receives a user-friendly JSON as follows:

API response **(HTTP status: 404)**:
```json
{
    "status": "NOT_FOUND",
    "statusCode": 404,
    "message": "Could not find what you were looking for :(",
    "debugMessage": "No todos found in the database, don't you have anything to do?",
    "timestamp": "01-02-2018 18:23:19"
}
```

* To get a specific todo with by its id: ```http://localhost:8080/api/todos/{id}```

*Example*: ```http://localhost:8080/api/todos/5a4bee18fc99700603a47e3b```

API response example **(HTTP status: 200)**:
```json
{
    "id": "5a4bee18fc99700603a47e3b",
    "user": "someone",
    "task": "walk the dog",
    "completed": false
}
```

* If no todo is found with the given id in the URL path, a 404 HTTP message is also received:

*Example:* ```http://localhost:8080/api/todos/123```

API response **(HTTP status: 404)**
```json
{
    "status": "NOT_FOUND",
    "statusCode": 404,
    "message": "Could not find what you were looking for :(",
    "debugMessage": "Todo was not found in the database with the id: 123",
    "timestamp": "01-02-2018 18:44:20"
}
```

* GET requests also accepts an optional query string parameter ```userName``` to specify a user:

*Example*: ```http://localhost:8080/api/todos?userName=someone_else```

API response example **(HTTP status: 200)**
```json
[
    {
        "id": "5a4bee3dfc99700603a47e3c",
        "user": "someone_else",
        "task": "go to the gym",
        "completed": false
    }
]
```
* A similar HTTP 404 message is also received if the ```userName``` matches no user

```json 
{
    "status": "NOT_FOUND",
    "statusCode": 404,
    "message": "Could not find what you were looking for :(",
    "debugMessage": "No todos found in the database for the user: someone_elsex. Maybe he's not busy?",
    "timestamp": "01-02-2018 20:24:18"
}
```
* If the user sends an unexpected query string parameter, i.e., anything that is not ```userName```, 
then he gets an HTTP 422 message:

```json
{
    "status": "UNPROCESSABLE_ENTITY",
    "statusCode": 422,
    "message": "Check the query string params and try again :(",
    "debugMessage": "Could not process the request with unexpected query string params: [user]",
    "timestamp": "01-02-2018 20:25:45"
}
```

### POST route

* The HTTP payload content type must be a JSON, i.e., it must contain the header ```content-type:application/json```

* HTTP POST to  ```http://localhost:8080/api/todos```

HTTP body example:
```json
{
	"user": "someone",
	"task": "walk the dog",
	"completed": false
}
```

* Creates a new todo and if succeeds, returns the same JSON but with an id

API response example - **(HTTP Status: 201)**
```json 
{
    "id": "5a4bec46fc99700603a47e39",
    "user": "someone",
    "task": "walk the dog",
    "completed": false
}
```

* If the user adds any malformed JSON, the API responds with an HTTP 400 message:

API response example - **(HTTP Status: 400)**
```json
{
    "status": "BAD_REQUEST",
    "statusCode": 400,
    "message": "Malformed JSON request :(",
    "debugMessage": "JSON parse error: Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries; nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries\n at [Source: java.io.PushbackInputStream@66cdb74e; line: 4, column: 3]",
    "timestamp": "01-02-2018 20:31:31"
}
```
* Any unexpected field names in the JSON request will be neglected.
