package service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import config.StringUtil;
import entity.StatusEntity;
import entity.TaskEntity;
import repository.TasksRepository;

public class TasksService {

	private TasksRepository repository = new TasksRepository();
	private StatusService statusService = new StatusService();
	
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
	
	public List<TaskEntity> findAll() {
		return repository.findAllWithUserNJobNStatus();
	}
	
	public boolean insert(int jobId, String name, int userId, String startDate, String endDate) throws UnsupportedEncodingException {
		int statusId = statusService.findByName(StatusEntity.STATUS_NOT_START);
		
		if (statusId == 0) {
			return false;
		}
		
		if (startDate != null && startDate.equals("")) {
			startDate = null;
		}
		
		if (endDate != null && endDate.equals("")) {
			endDate = null;
		}
		
		return (repository.insert(jobId, StringUtil.convert(name), userId, startDate, endDate, statusId) > 0);
	}
	
	public List<TaskEntity> findById(int id) {
		return repository.findById(id);
	}
	
	public boolean update(int id, String name, String startDate, String endDate, 
			int userId, int jobId, int statusId) throws UnsupportedEncodingException {
		if (startDate != null && startDate.equals("")) {
			startDate = null;
		}
		
		if (endDate != null && endDate.equals("")) {
			endDate = null;
		}
		
		return (repository.update(id, StringUtil.convert(name), startDate, endDate, userId, jobId, statusId) > 0);
	}
	
	public List<TaskEntity> findByIdWithUserNJobNStatus(int id) {
		return repository.findByIdWithUserNJobNStatus(id);
	}
	
	public boolean delete(int id) {
		return (repository.delete(id) > 0);
	}
	
	public List<Integer> countTaskByStatus() {
		return repository.countTaskByStatus();
	}
}
