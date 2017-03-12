package org.thiki.kanban.projects.members;

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
import org.thiki.kanban.user.UsersService;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class MembersService {
    @Resource
    private MembersPersistence membersPersistence;
    @Resource
    private ProjectsService projectsService;
    @Resource
    private UsersService usersService;

    @CacheEvict(value = "project", key = "contains('projects'+#userName)", allEntries = true)
    public Member joinTeam(String projectId, final Member member, String userName) {
        Project targetProject = projectsService.findById(projectId);
        if (targetProject == null) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }

        Member foundMember = membersPersistence.findMember(member.getUserName(), projectId);
        if (foundMember != null) {
            throw new InvalidParamsException(MessageFormat.format("Member named {0} is already in the projects.", member.getUserName()));
        }

        member.setAuthor(userName);
        member.setProjectId(projectId);
        membersPersistence.joinProject(member);
        return membersPersistence.findById(member.getId());
    }

    @Caching(evict = {@CacheEvict(value = "project", key = "contains('projects'+#projectId)", allEntries = true), @CacheEvict(value = "board", key = "startsWith('#userName + boards')", allEntries = true)})
    public Member joinTeam(String userName, String projectId) {
        boolean isAlreadyJoinTeam = membersPersistence.isAMemberOfTheProject(userName, projectId);
        if (isAlreadyJoinTeam) {
            throw new BusinessException(MembersCodes.USER_IS_ALREADY_A_MEMBER_OF_THE_PROJECT);
        }
        Member projectMember = new Member();
        projectMember.setProjectId(projectId);
        projectMember.setUserName(userName);
        projectMember.setAuthor(userName);
        membersPersistence.joinProject(projectMember);
        return membersPersistence.findById(projectMember.getId());
    }

    @Cacheable(value = "project", key = "'service-members'+#projectId")
    public List<Member> loadMembersByTeamId(String userName, String projectId) {
        boolean isTeamExist = projectsService.isTeamExist(projectId);
        if (!isTeamExist) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }

        boolean isAMember = membersPersistence.isAMemberOfTheProject(userName, projectId);
        if (!isAMember) {
            throw new UnauthorisedException(MembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_PROJECT);
        }
        List<Member> members = membersPersistence.loadMembersByProject(projectId);
        for (Member member : members) {
            member.setProfile(usersService.loadProfileByUserName(member.getUserName()));
        }
        return members;
    }

    public boolean isMember(String projectId, String userName) {
        return membersPersistence.isAMemberOfTheProject(userName, projectId);
    }

    @CacheEvict(value = "project", key = "contains('projects'+#memberName)", allEntries = true)
    public void leaveTeam(String projectId, String memberName) {
        membersPersistence.leaveProject(projectId, memberName);
    }

    @Cacheable(value = "project", key = "'service-projects'+#userName")
    public List<Project> loadProjectsByUserName(String userName) {
        return membersPersistence.findProjects(userName);
    }
}
