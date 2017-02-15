package org.thiki.kanban.activity;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 16/01/2017.
 */

@Repository
public interface ActivityPersistence {
    Integer record(Activity activity);

    List<Activity> loadActivitiesByCard(@Param("operationTypeCode") String operationTypeCode, @Param("cardId") String cardId);
}
