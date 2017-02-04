package org.thiki.kanban.projects.projectMembers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class ProjectMembersResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "project", key = "'projectMember'+projectMember.id+#projectId+#userName")
    public Object toResource(String projectId, ProjectMember projectMember, String userName) {
        ProjectMembersResource projectMembersResource = new ProjectMembersResource();
        projectMembersResource.domainObject = projectMember;
        if (projectMember != null) {
            Link selfLink = linkTo(methodOn(ProjectMembersController.class).joinTeam(projectMember, projectId, null)).withSelfRel();
            projectMembersResource.add(tlink.from(selfLink).build(userName));
        }
        return projectMembersResource.getResource();
    }
}
