package org.thiki.kanban.notification;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/18/16.
 */
@Service
public class UnreadNotificationsResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "notification", key = "#userName+'unreadNotificationTotal'")
    public Object toResource(String userName, Integer unreadNotificationTotal) throws Exception {
        UnreadNotificationsResource unreadNotificationsResource = new UnreadNotificationsResource();
        unreadNotificationsResource.buildDataObject("unreadNotificationsTotal", unreadNotificationTotal);
        Link selfLink = linkTo(methodOn(NotificationController.class).loadUnreadNotificationsTotal(userName)).withSelfRel();
        unreadNotificationsResource.add(tlink.from(selfLink).build(userName));

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        unreadNotificationsResource.add(tlink.from(notificationsLink).build(userName));
        return unreadNotificationsResource.getResource();
    }
}
