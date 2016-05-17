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
        List<TaskResource> resources = new TaskResourceAssembler(entryId).toResources(taskList);
        TasksResource tasksRes = new TasksResource();
        tasksRes.setTasks(resources);

        return new ResponseEntity<TasksResource>(
                tasksRes,
                taskList == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{id}", method = RequestMethod.GET)
    public HttpEntity<TaskResource> findById(@PathVariable String entryId, @PathVariable String id) {
        return null;
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{taskId}", method = RequestMethod.PUT)
    public HttpEntity<TaskResource> update(@RequestBody Task task, @PathVariable String entryId, @PathVariable String taskId) {
        Task updatedTask = tasksService.updateContent(taskId, task);
        ResponseEntity<TaskResource> responseEntity = new ResponseEntity<TaskResource>(
                new TaskResourceAssembler(entryId).toResource(task),
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TaskResource> deleteById(@PathVariable String entryId, @PathVariable String id) {
        tasksService.deleteById(id);
        return new ResponseEntity<>(
                new TaskResourceAssembler(entryId).emptyResource(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/entries/{entryId}/tasks", method = RequestMethod.POST)
    public ResponseEntity<TaskResource> create(@RequestBody Task task, @PathVariable String entryId, @RequestHeader Integer userId) {
        Task savedTask = tasksService.create(userId, entryId, task);

        ResponseEntity<TaskResource> responseEntity = new ResponseEntity<TaskResource>(
                new TaskResourceAssembler(entryId).toResource(savedTask),
                HttpStatus.CREATED);
        return responseEntity;
    }
}
