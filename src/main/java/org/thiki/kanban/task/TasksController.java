package org.thiki.kanban.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @RequestMapping(value = "/entries/{entryId}/tasks", method = RequestMethod.GET)
    public HttpEntity<TasksResource> findByEntryId(@PathVariable String entryId) {
        List<Task> taskList = tasksService.findByEntryId(entryId);
        List<TaskResource> resources = new TaskResourceAssembler().toResources(taskList);
        TasksResource tasksRes = new TasksResource();
        tasksRes.setTasks(resources);

        return new ResponseEntity<TasksResource>(
                tasksRes,
                taskList == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public HttpEntity<TaskResource> findById(@PathVariable String id) {
        return null;
    }

    @RequestMapping(value = "/tasks/{taskId}/assignment/", method = RequestMethod.PUT)
    public HttpEntity<TaskResource> assign(@PathVariable String taskId) {
        Task task = tasksService.assign(taskId);
        ResponseEntity<TaskResource> responseEntity = new ResponseEntity<TaskResource>(
                new TaskResourceAssembler().toResource(task),
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.PUT)
    public HttpEntity<TaskResource> update(@RequestBody Task task, @PathVariable String taskId) {
        Task updatedTask = tasksService.updateContent(taskId, task);
        ResponseEntity<TaskResource> responseEntity = new ResponseEntity<TaskResource>(
                new TaskResourceAssembler().toResource(task),
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TaskResource> deleteById(@PathVariable Integer id) {
        return null;
    }

    @RequestMapping(value = "/entries/{entryId}/tasks", method = RequestMethod.POST)
    public ResponseEntity<TaskResource> create(@RequestBody Task task, @RequestHeader Integer userId, @PathVariable String entryId) {
        Task savedTask = tasksService.create(userId, entryId, task);

        ResponseEntity<TaskResource> responseEntity = new ResponseEntity<TaskResource>(
                new TaskResourceAssembler().toResource(savedTask),
                HttpStatus.CREATED);
        return responseEntity;
    }
}
