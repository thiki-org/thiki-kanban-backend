package org.thiki.kanban.activity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 16/01/2017.
 */

@Repository
public interface ActivityPersistence {
    Integer record(Activity activity);

    List<Activity> loadActivitiesByCard(String cardId);
}
