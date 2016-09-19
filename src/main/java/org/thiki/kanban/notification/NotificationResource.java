package org.thiki.kanban.notification;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/17/16.
 */
public class NotificationResource extends RestResource {
    public NotificationResource(String userName, Notification notification) throws Exception {

        this.domainObject = notification;
        Link selfLink = linkTo(methodOn(NotificationController.class).loadNotificationById(notification.getId(), userName)).withSelfRel();
        this.add(selfLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        this.add(notificationsLink);
    }
}
