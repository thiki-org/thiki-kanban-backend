package org.thiki.kanban.projects.members;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.thiki.kanban.projects.project.Project;

import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Repository
public interface MembersPersistence {
    void joinProject(Member projectMember);

    Member findById(@Param("id") String id);

    Member findMember(@Param("userName") String userName, @Param("projectId") String projectId);

    List<Member> loadMembersByProject(String projectId);

    boolean isAMemberOfTheProject(@Param("userName") String userName, @Param("projectId") String projectId);

    Integer leaveProject(@Param("projectId") String projectId, @Param("userName") String userName);

    List<Project> findProjects(String userName);
}
