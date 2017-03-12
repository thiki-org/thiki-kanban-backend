package org.thiki.kanban.projects.project;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.projects.members.MembersService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@Service
public class ProjectsService {
    @Resource
    private ProjectsPersistence projectsPersistence;
    @Resource
    private MembersService membersService;

    @CacheEvict(value = "project", key = "contains('projects'+#userName)", allEntries = true)
    public Project create(String userName, final Project project) {
        boolean isNameConflict = projectsPersistence.isNameConflict(userName, project.getName());
        if (isNameConflict) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_ALREADY_EXISTS);
        }
        projectsPersistence.create(userName, project);

        membersService.joinTeam(userName, project.getId());
        return projectsPersistence.findById(project.getId());
    }

    @Cacheable(value = "project", key = "'service-project'+#projectId")
    public Project findById(String projectId) {
        Project project = projectsPersistence.findById(projectId);
        if (project == null) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }
        return project;
    }

    @Cacheable(value = "project", key = "'service-projects'+#userName")
    public List<Project> findByUserName(String userName) {
        return projectsPersistence.findByUserName(userName);
    }

    public boolean isTeamExist(String projectId) {
        return projectsPersistence.isTeamExist(projectId);
    }

    @CacheEvict(value = "project", key = "contains('#projectId')", allEntries = true)
    public Project update(String projectId, Project project, String userName) {
        Project originProject = projectsPersistence.findById(projectId);
        if (originProject == null) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }
        projectsPersistence.update(projectId, project);
        return projectsPersistence.findById(projectId);
    }
}
