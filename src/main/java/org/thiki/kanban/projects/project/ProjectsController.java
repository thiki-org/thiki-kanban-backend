package org.thiki.kanban.projects.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class ProjectsController {
    @Autowired
    private ProjectsService projectsService;
    @Resource
    private ProjectResource projectResource;
    @Resource
    private ProjectsResource projectsResource;

    @RequestMapping(value = "/{userName}/projects", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Project project, @PathVariable("userName") String userName) throws Exception {
        Project savedProject = projectsService.create(userName, project);
        return Response.post(projectResource.toResource(userName, savedProject));
    }


    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.PUT)
    public HttpEntity updateTeam(@RequestBody Project project, @PathVariable("projectId") String projectId, @RequestHeader("userName") String userName) throws Exception {
        Project savedProject = projectsService.update(projectId, project, userName);
        return Response.build(projectResource.toResource(userName, savedProject));
    }

    @RequestMapping(value = "/{userName}/projects", method = RequestMethod.GET)
    public HttpEntity findByUserName(@PathVariable("userName") String userName) throws Exception {
        List<Project> projectList = projectsService.findByUserName(userName);
        return Response.build(projectsResource.toResource(userName, projectList));
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @RequestHeader("userName") String userName) throws Exception {
        Project project = projectsService.findById(id);
        return Response.build(projectResource.toResource(userName, project));
    }
}
