package org.thiki.kanban.foundation.hateoas;

/**
 * Created by xubt on 16/12/2016.
 */
public class Action {
    private String actionName;
    private boolean isAllowed;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    public void setMethod(String actionName, boolean isAllowed) {
        this.actionName = actionName;
        this.isAllowed = isAllowed;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionName='" + actionName + '\'' +
                ", isAllowed=" + isAllowed +
                '}';
    }
}
