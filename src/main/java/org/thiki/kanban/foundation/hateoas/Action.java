package org.thiki.kanban.foundation.hateoas;

import org.thiki.kanban.foundation.security.authentication.MethodType;

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
        if (MethodType.GET.name().equals(actionName)) {
            this.actionName = "read";
        }
        if (MethodType.POST.name().equals(actionName)) {
            this.actionName = "saveCard";
        }
        if (MethodType.PUT.name().equals(actionName)) {
            this.actionName = "modify";
        }
        if (MethodType.DELETE.name().equals(actionName)) {
            this.actionName = "delete";
        }
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
