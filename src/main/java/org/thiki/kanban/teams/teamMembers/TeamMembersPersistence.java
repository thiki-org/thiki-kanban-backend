package org.thiki.kanban.teams.teamMembers;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.thiki.kanban.teams.team.Team;

import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Repository
public interface TeamMembersPersistence {
    List<TeamMember> loadAll();

    void joinTeam(TeamMember teamMember);

    TeamMember findById(@Param("id") String id);

    TeamMember findMemberByTeamId(@Param("member") String member, @Param("teamId") String teamId);

    List<Member> loadMembersByTeamId(String teamId);

    boolean isAMemberOfTheTeam(@Param("userName") String userName, @Param("teamId") String teamId);

    Integer leaveTeam(@Param("teamId") String teamId, @Param("memberName") String memberName);

    List<Team> findTeams(String userName);
}
