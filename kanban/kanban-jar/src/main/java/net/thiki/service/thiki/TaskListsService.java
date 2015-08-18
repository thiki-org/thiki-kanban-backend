package net.thiki.service.thiki;

import java.util.ArrayList;
import java.util.List;

import net.thiki.core.endpoint.AbstractRESTfulService;
import net.thiki.core.endpoint.RESTfulRequest;
import net.thiki.core.endpoint.StandardRESTfulService;
import net.thiki.domain.tasklist.TaskList;

import org.springframework.stereotype.Service;

@Service
public class TaskListsService extends AbstractRESTfulService<TaskList> implements StandardRESTfulService<TaskList> {

	@Override
	public List<TaskList> findAll(RESTfulRequest request) {
		
		List<TaskList> list = new ArrayList<>();
		TaskList taskList = new TaskList();
		taskList.setContent("跑15公里");
		taskList.setTitle("锻炼");
		list.add(taskList);
		
		return list;
	}

	@Override
	public TaskList find(String id, RESTfulRequest request) {
		TaskList taskList = new TaskList();
		taskList.setContent("跑15公里");
		taskList.setTitle("锻炼");
		return taskList;
	}

}
