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

    List<Team> findByUserName(String userName);

    boolean isTeamExist(String teamId);

    boolean isNameConflict(@Param("userName") String userName, @Param("teamName") String teamName);

    Integer update(@Param("teamId") String teamId, @Param("team") Team team);
}
