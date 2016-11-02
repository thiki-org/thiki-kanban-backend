package org.thiki.kanban.worktile.domains;

import java.util.List;

/**
 * Created by xubt on 01/11/2016.
 */
public class Task {
    private String name;
    private Entry entry;
    private List<Todo> todos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
