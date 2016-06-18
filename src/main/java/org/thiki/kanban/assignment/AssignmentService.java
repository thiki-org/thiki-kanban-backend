package org.thiki.kanban.assignment;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;
import org.thiki.kanban.task.Task;
import org.thiki.kanban.task.TasksPersistence;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
@Service
public class AssignmentService {

    @Resource
    private AssignmentPersistence assignmentPersistence;
    @Resource
    private TasksPersistence tasksPersistence;

    public Assignment create(final Assignment assignment, String taskId, String reporterUserId) {
        assignment.setTaskId(taskId);
        assignment.setReporter(reporterUserId);
        assignmentPersistence.create(assignment);
        return assignmentPersistence.findById(assignment.getId());
    }

    public Assignment findById(String id) {
        return assignmentPersistence.findById(id);
    }

    public List<Assignment> findByTaskId(String taskId) {
        Task task = tasksPersistence.findById(taskId);
        if (task == null) {
            throw new InvalidParameterException("task[" + taskId + "] is not found.");
        }
        return assignmentPersistence.findByTaskId(taskId);
    }

    public int deleteById(String id) {
        Assignment assignmentToDelete = assignmentPersistence.findById(id);
        if (assignmentToDelete == null) {
            throw new ResourceNotFoundException("assignment[" + id + "] is not found.");
        }
        return assignmentPersistence.deleteById(id);
    }
}
