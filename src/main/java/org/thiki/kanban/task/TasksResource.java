package org.thiki.kanban.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 *
 */
public class TasksResource extends RestResource {
    public TasksResource(List<Task> taskList, String entryId) {
        this.domainObject = taskList;
        JSONArray tasksJSONArray = new JSONArray();
        for (Task task : taskList) {
            TaskResource taskResource = new TaskResource(task, entryId);
            JSONObject taskJSON = taskResource.getResource();
            tasksJSONArray.add(taskJSON);
        }
        this.resourcesJSON = tasksJSONArray;
    }
}
