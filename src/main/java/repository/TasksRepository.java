package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.TaskEntity;

public class TasksRepository {

	public List<TaskEntity> findByUserId(int userid) {
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks t JOIN status s ON t.status_id = s.id WHERE t.user_id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, userid);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(result.getInt("t.id"));
				task.setName(result.getString("t.name"));
				task.setStartDate(result.getDate("t.start_date"));
				task.setEndDate(result.getDate("t.end_date"));
				task.setUserId(userid);
				task.setJobId(result.getInt("t.job_id"));
				task.setStatusId(result.getInt("t.status_id"));
				task.setStatusName(result.getString("s.name"));
				
				list.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return list;
	}

	public List<TaskEntity> findByUserIdAndJobId(int userId, int jobId) {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();
		String query = "SELECT *"
				+ "FROM tasks t "
				+ "JOIN status s ON s.id = t.status_id "
				+ "WHERE t.user_id = ? "
				+ "AND t.job_id = ? ";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, userId);
			statement.setInt(2, jobId);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(result.getInt("t.id"));
				task.setName(result.getString("t.name"));
				task.setStartDate(result.getDate("t.start_date"));
				task.setEndDate(result.getDate("t.end_date"));
				task.setUserId(userId);
				task.setJobId(jobId);
				task.setStatusId(result.getInt("t.status_id"));
				task.setStatusName(result.getString("s.name"));
				listTasks.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error findByUserIdAndJobId!");
			ex.printStackTrace();
		}
		return listTasks;
	}

	public List<TaskEntity> countTasksByStatus(int jobId) {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();
		String query = "SELECT t.status_id, s.name, count(*) AS count_task "
				+ "FROM tasks t "
				+ "JOIN status s ON t.status_id = s.id "
				+ "WHERE t.job_id = ? "
				+ "GROUP BY t.status_id";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, jobId);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				TaskEntity task = new TaskEntity();
				task.setStatusId(result.getInt("t.status_id"));
				task.setStatusName(result.getString("s.name"));
				task.setCountTask(result.getInt("count_task"));
				listTasks.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error countTasksByStatus!");
			ex.printStackTrace();
		}
		return listTasks;
	}
	
	public List<TaskEntity> findAll() {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(result.getInt("id"));
				task.setName(result.getString("name"));
				task.setStartDate(result.getDate("start_date"));
				task.setEndDate(result.getDate("end_date"));
				task.setUserId(result.getInt("user_id"));
				task.setJobId(result.getInt("job_id"));
				task.setStatusId(result.getInt("status_id"));
				listTasks.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error findAll!");
			ex.printStackTrace();
		}
		
		return listTasks;
	}
}
