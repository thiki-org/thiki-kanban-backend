package org.thiki.kanban.projects.project;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@Repository
public interface ProjectsPersistence {
    List<Project> loadAll();

    void create(@Param("userName") String userName, @Param("project") Project project);

    Project findById(@Param("id") String id);

    List<Project> findByUserName(String userName);

    boolean isTeamExist(String projectId);

    boolean isNameConflict(@Param("userName") String userName, @Param("projectName") String projectName);

    Integer update(@Param("projectId") String projectId, @Param("project") Project project);
}
