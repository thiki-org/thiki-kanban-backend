package org.thiki.kanban.worktile.domains;

/**
 * Created by xubt on 01/11/2016.
 */
public class Todo {
    private String name;
    private int checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
