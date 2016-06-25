package org.thiki.kanban.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

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
    public HttpEntity findByEntryId(@PathVariable String entryId) {
        List<Task> taskList = tasksService.findByEntryId(entryId);
        return Response.build(new TasksResource(taskList, entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String entryId, @PathVariable String id) {
        Task foundTask = tasksService.findById(id);

        return Response.build(new TaskResource(foundTask, entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{taskId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Task task, @PathVariable String entryId, @PathVariable String taskId) {
        Task updatedTask = tasksService.update(taskId, task);
        return Response.build(new TaskResource(updatedTask, entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String entryId, @PathVariable String id) {
        tasksService.deleteById(id);
        return Response.build(new TaskResource(entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Task task, @PathVariable String entryId, @RequestHeader Integer userId) {
        Task savedTask = tasksService.create(userId, entryId, task);

        return Response.post(new TaskResource(savedTask, entryId));
    }
}
