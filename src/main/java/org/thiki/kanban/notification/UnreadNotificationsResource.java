package org.thiki.kanban.notification;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/18/16.
 */
public class UnreadNotificationsResource extends RestResource {
    public UnreadNotificationsResource(String userName, Integer unreadNotificationTotal) throws Exception {
        this.buildDataObject("unreadNotificationsTotal", unreadNotificationTotal);
        Link selfLink = linkTo(methodOn(NotificationController.class).loadUnreadNotificationsTotal(userName)).withSelfRel();
        this.add(selfLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        this.add(notificationsLink);
    }
}
