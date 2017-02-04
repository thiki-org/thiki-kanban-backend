package org.thiki.kanban.projects.projectMembers;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.InvalidParamsException;
import org.thiki.kanban.foundation.exception.UnauthorisedException;
import org.thiki.kanban.projects.project.Project;
import org.thiki.kanban.projects.project.ProjectCodes;
import org.thiki.kanban.projects.project.ProjectsService;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class ProjectMembersService {
    @Resource
    private ProjectMembersPersistence projectMembersPersistence;
    @Resource
    private ProjectsService projectsService;

    @CacheEvict(value = "project", key = "contains('projects'+#userName)", allEntries = true)
    public ProjectMember joinTeam(String projectId, final ProjectMember projectMember, String userName) {
        Project targetProject = projectsService.findById(projectId);
        if (targetProject == null) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }

        ProjectMember foundMember = projectMembersPersistence.findMemberByTeamId(projectMember.getMember(), projectId);
        if (foundMember != null) {
            throw new InvalidParamsException(MessageFormat.format("Member named {0} is already in the projects.", projectMember.getMember()));
        }

        projectMember.setAuthor(userName);
        projectMember.setProjectId(projectId);
        projectMembersPersistence.joinTeam(projectMember);
        return projectMembersPersistence.findById(projectMember.getId());
    }

    @Caching(evict = {@CacheEvict(value = "project", key = "contains('projects'+#projectId)", allEntries = true), @CacheEvict(value = "board", key = "startsWith('#userName + boards')", allEntries = true)})
    public ProjectMember joinTeam(String userName, String projectId) {
        boolean isAlreadyJoinTeam = projectMembersPersistence.isAMemberOfTheTeam(userName, projectId);
        if (isAlreadyJoinTeam) {
            throw new BusinessException(ProjectMembersCodes.USER_IS_ALREADY_A_MEMBER_OF_THE_PROJECT);
        }
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectId);
        projectMember.setMember(userName);
        projectMember.setAuthor(userName);
        projectMembersPersistence.joinTeam(projectMember);
        return projectMembersPersistence.findById(projectMember.getId());
    }

    @Cacheable(value = "project", key = "'service-members'+#projectId")
    public List<Member> loadMembersByTeamId(String userName, String projectId) {
        boolean isTeamExist = projectsService.isTeamExist(projectId);
        if (!isTeamExist) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }

        boolean isAMember = projectMembersPersistence.isAMemberOfTheTeam(userName, projectId);
        if (!isAMember) {
            throw new UnauthorisedException(ProjectMembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_PROJECT);
        }
        List<Member> members = projectMembersPersistence.loadMembersByTeamId(projectId);

        return members;
    }

    public boolean isMember(String projectId, String userName) {
        return projectMembersPersistence.isAMemberOfTheTeam(userName, projectId);
    }

    @CacheEvict(value = "project", key = "contains('projects'+#memberName)", allEntries = true)
    public void leaveTeam(String projectId, String memberName) {
        projectMembersPersistence.leaveTeam(projectId, memberName);
    }

    @Cacheable(value = "project", key = "'service-projects'+#userName")
    public List<Project> loadTeamsByUserName(String userName) {
        return projectMembersPersistence.findTeams(userName);
    }
}
