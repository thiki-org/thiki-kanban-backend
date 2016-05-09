package org.thiki.kanban.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "")
public class TasksController {

    @Autowired 
    private TasksService tasksService;

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public HttpEntity<TaskResource> findById(@PathVariable Integer id) {
        return null;
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    public HttpEntity<TaskResource> update(@RequestBody Task task, @PathVariable Integer id) {
        return null;
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TaskResource> deleteById(@PathVariable Integer id) {
        return null;
    }

    @RequestMapping(value = "{userId}/tasks", method = RequestMethod.POST)
    public ResponseEntity<TaskResource> create(@RequestBody Task task, @PathVariable Integer userId) {
        Task savedTask = tasksService.create(userId, task);
        
        ResponseEntity<TaskResource> responseEntity = new ResponseEntity<TaskResource>(
                new TaskResourceAssembler().toResource(savedTask), 
                HttpStatus.CREATED);
        return responseEntity;
    }
}
