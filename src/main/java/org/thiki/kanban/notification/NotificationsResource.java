package org.thiki.kanban.notification;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/17/16.
 */
@Service
public class NotificationsResource extends RestResource {
    @Resource
    private TLink tlink;
    @Resource
    private NotificationResource notificationResourceService;

    public Object toResource(String userName, List<Notification> notifications) throws Exception {
        NotificationsResource notificationsResource = new NotificationsResource();
        List<Object> notificationResources = new ArrayList<>();
        for (Notification notification : notifications) {
            Object notificationResource = notificationResourceService.toResource(userName, notification);
            notificationResources.add(notificationResource);
        }

        notificationsResource.buildDataObject("notifications", notificationResources);
        Link selfLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withSelfRel();
        notificationsResource.add(tlink.from(selfLink).build(userName));

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        notificationsResource.add(tlink.from(notificationsLink).build(userName));
        return notificationsResource.getResource();
    }
}
