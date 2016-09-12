package org.thiki.kanban.notification;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by xutao on 09/12/16.
 */

@Repository
public interface NotificationPersistence {
    Integer create(Notification notification);

    Notification findById(@Param("id") String id);
}
