package org.thiki.kanban.projects.project;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.projects.projectMembers.ProjectMembersController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by bogehu on 7/11/16.
 */
@Service
public class ProjectResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "project", key = "'project'+#project.id+#userName")
    public Object toResource(String userName, Project project) throws Exception {
        ProjectResource projectResource = new ProjectResource();
        projectResource.domainObject = project;
        if (project != null) {
            Link selfLink = linkTo(methodOn(ProjectsController.class).findById(project.getId(), userName)).withSelfRel();
            projectResource.add(tlink.from(selfLink).build(userName));

            Link membersLink = linkTo(methodOn(ProjectMembersController.class).loadMembersByProjectId(project.getId(), userName)).withRel("members");
            projectResource.add(tlink.from(membersLink).build(userName));
        }
        return projectResource.getResource();
    }
}
