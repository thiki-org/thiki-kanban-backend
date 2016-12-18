package org.thiki.kanban.notification;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/17/16.
 */

@Service
public class NotificationResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(String userName, Notification notification) throws Exception {
        NotificationResource notificationResource = new NotificationResource();
        notificationResource.domainObject = notification;
        Link selfLink = linkTo(methodOn(NotificationController.class).loadNotificationById(notification.getId(), userName)).withSelfRel();
        notificationResource.add(tlink.from(selfLink).build(userName));

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        notificationResource.add(tlink.from(notificationsLink).build(userName));
        return notificationResource.getResource();
    }
}
