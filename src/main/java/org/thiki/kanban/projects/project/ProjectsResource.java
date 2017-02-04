package org.thiki.kanban.projects.project;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 9/9/16.
 */
@Service
public class ProjectsResource extends RestResource {
    @Resource
    private ProjectResource projectResourceService;

    @Cacheable(value = "project", key = "'resource-projects'+#userName")
    public Object toResource(String userName, List<Project> projectList) throws Exception {
        ProjectsResource projectsResource = new ProjectsResource();
        projectsResource.domainObject = projectList;

        List<Object> projectsResources = new ArrayList<>();
        for (Project project : projectList) {
            Object projectResource = projectResourceService.toResource(userName, project);
            projectsResources.add(projectResource);
        }
        projectsResource.buildDataObject("projects", projectsResources);
        return projectsResource.getResource();
    }
}
