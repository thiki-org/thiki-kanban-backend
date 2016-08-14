package org.thiki.kanban.team;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@Repository
public interface TeamsPersistence {
    List<Team> loadAll();

    void create(Team team);

    Team findById(@Param("id") String id);

    Integer deleteById(@Param("id") String id);

    Integer update(Team team);
}
