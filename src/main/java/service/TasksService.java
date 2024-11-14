package service;

import java.util.ArrayList;
import java.util.List;

import entity.StatusEntity;
import entity.TaskEntity;
import repository.TasksRepository;

public class TasksService {

	private TasksRepository repository = new TasksRepository();
	
	public List<TaskEntity> findByUserId(int userId) {
		return repository.findByUserId(userId);
	}
	
	public List<TaskEntity> resumeTaskByStatusName(List<TaskEntity> listTask, String statusName) {
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		if (listTask == null || listTask.size() == 0) {
			return list;
		}
				
		for (TaskEntity task : listTask) {
			if (task.getStatusName().equals(statusName)) {
				list.add(task);
			}
		}
		
		return list;
	}
	
	public List<TaskEntity> findByUserIdAndJobId(int userId, int jobId) {
		return repository.findByUserIdAndJobId(userId, jobId);
	}
	
	public List<TaskEntity> countTasksByStatus(int jobId) {
		return repository.countTasksByStatus(jobId);
	}
}
