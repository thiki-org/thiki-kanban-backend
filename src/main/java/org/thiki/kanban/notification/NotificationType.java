package org.thiki.kanban.notification;

/**
 * Created by xubt on 23/09/2016.
 */
public enum NotificationType {
    PROJECT_MEMBER_INVITATION("project-members-invitation", "团队邀请");
    private String type;
    private String name;

    NotificationType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getNameByType(String type) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (notificationType.type.equals(type)) {
                return notificationType.typeName();
            }
        }
        return type;
    }

    public String type() {
        return type;
    }

    public String typeName() {
        return name;
    }
}
