package org.thiki.kanban.notification;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/17/16.
 */
public class NotificationsResource extends RestResource {
    public NotificationsResource(String userName, List<Notification> notifications) throws Exception {

        List<NotificationResource> notificationResources = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationResource notificationResource = new NotificationResource(userName, notification);
            notificationResources.add(notificationResource);
        }

        this.buildDataObject("notifications", notificationResources);
        Link selfLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withSelfRel();
        this.add(selfLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        this.add(notificationsLink);
    }
}
