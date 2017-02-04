package org.thiki.kanban.projects.projectMembers;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.thiki.kanban.projects.project.Project;

import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Repository
public interface ProjectMembersPersistence {
    List<ProjectMember> loadAll();

    void joinTeam(ProjectMember projectMember);

    ProjectMember findById(@Param("id") String id);

    ProjectMember findMemberByTeamId(@Param("member") String member, @Param("projectId") String projectId);

    List<Member> loadMembersByTeamId(String projectId);

    boolean isAMemberOfTheTeam(@Param("userName") String userName, @Param("projectId") String projectId);

    Integer leaveTeam(@Param("projectId") String projectId, @Param("memberName") String memberName);

    List<Project> findTeams(String userName);
}
