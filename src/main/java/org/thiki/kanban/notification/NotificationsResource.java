package org.thiki.kanban.notification;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/17/16.
 */
public class NotificationsResource extends RestResource {
    public NotificationsResource(String userName, List<Notification> notifications) throws Exception {

        JSONArray notificationsJSONArray = new JSONArray();

        for (Notification notification : notifications) {
            NotificationResource notificationResource = new NotificationResource(userName, notification);
            JSONObject notificationJSON = notificationResource.getResource();
            notificationsJSONArray.add(notificationJSON);
        }

        this.buildDataObject("notifications", notificationsJSONArray);
        Link selfLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withSelfRel();
        this.add(selfLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadNotifications(userName)).withRel("notifications");
        this.add(notificationsLink);
    }
}
