package com.todos.test.helpers

import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import com.todos.domain.Todo

class TestHelper {
    
    // helper function to create dummy todos
    public static getDummyTodo(numberOfTodos = 1) {
        // fake Todo template
        Fixture.of(Todo.class).addTemplate("test", new Rule() {{
            add("id", "101"); 
            add("user", "someone");
            add("task", "walk the dog");
            add("completed", false);
        }});

        // returns a list of Todo's
        if (numberOfTodos > 1) {
            ArrayList<Todo> list = new ArrayList<Todo>()
            
            for (int i = 0; i < numberOfTodos; ++i) {
                list.add(Fixture.from(Todo.class).gimme("test"))
            }
            
            return list // ArrayList with dummy Todo object references
        }

        // returns a single Todo
        return Fixture.from(Todo.class).gimme("test")                
    }
        
    // helper function to assert todos
    public static void assertTodo(oneTodo, anotherTodo) {
        // can be a single Todo or a List of todos
        assert oneTodo == anotherTodo
    }

}
