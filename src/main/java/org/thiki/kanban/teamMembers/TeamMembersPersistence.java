package org.thiki.kanban.teamMembers;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Repository
public interface TeamMembersPersistence {
    List<TeamMember> loadAll();

    void joinTeam(TeamMember teamMember);

    TeamMember findById(@Param("id") String id);
}
