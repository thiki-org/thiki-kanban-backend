package org.thiki.kanban.notification;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/17/16.
 */
public class NotificationsResource extends RestResource {
    public NotificationsResource(String userName, Notifications notifications) throws Exception {
        this.buildDataObject("unreadNotificationsTotal", notifications.getUnreadNotificationsTotal());
        Link selfLink = linkTo(methodOn(NotificationController.class).loadUnreadNotificationsTotal(userName)).withSelfRel();
        this.add(selfLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        this.add(notificationsLink);
    }
}
