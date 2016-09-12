package org.thiki.kanban.teams.team;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@Repository
public interface TeamsPersistence {
    List<Team> loadAll();

    void create(@Param("userName") String userName, @Param("team") Team team);

    Team findById(@Param("id") String id);

    Integer deleteById(@Param("id") String id);

    List<Team> findByUserName(String userName);

    boolean isTeamExist(String teamId);
}
