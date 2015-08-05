package net.thiki.service.thiki;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.thiki.core.endpoint.RESTfulRequest;
import net.thiki.core.endpoint.RESTfulResponse;
import net.thiki.core.endpoint.StandardRESTfulService;
import net.thiki.domain.tasklist.TaskList;

@Service
public class TaskListsService implements StandardRESTfulService<TaskList> {

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
	public TaskList find(Integer id, RESTfulRequest request) {
		TaskList taskList = new TaskList();
		taskList.setContent("跑15公里");
		taskList.setTitle("锻炼");
		return taskList;
	}

	@Override
	public RESTfulResponse create(RESTfulRequest request) {
		return null;
	}

	@Override
	public RESTfulResponse modify(RESTfulRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RESTfulResponse delete(RESTfulRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
