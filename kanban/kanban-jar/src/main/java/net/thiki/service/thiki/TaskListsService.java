package net.thiki.service.thiki;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.thiki.core.endpoint.StandardRESTfulService;
import net.thiki.domain.tasklist.TaskList;

@Service
public class TaskListsService implements StandardRESTfulService<TaskList> {

	@Override
	public List<TaskList> findAll() {
		
		List<TaskList> list = new ArrayList<>();
		TaskList taskList = new TaskList();
		taskList.setContent("跑15公里");
		taskList.setTitle("锻炼");
		list.add(taskList);
		
		return list;
	}

	@Override
	public TaskList find(Integer id) {
		TaskList taskList = new TaskList();
		taskList.setContent("跑15公里");
		taskList.setTitle("锻炼");
		return taskList;
	}

}
