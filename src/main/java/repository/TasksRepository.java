package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
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
	
	public List<TaskEntity> findAllWithUserNJobNStatus() {
		List<TaskEntity> listTasks = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks t "
				+ "JOIN users u ON t.user_id = u.id "
				+ "JOIN jobs j ON t.job_id = j.id "
				+ "JOIN status s ON t.status_id = s.id";
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
				task.setUserName(result.getString("u.fullname"));
				task.setJobName(result.getString("j.name"));
				task.setStatusName(result.getString("s.name"));
				
				listTasks.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error findAll!");
			ex.printStackTrace();
		}
		
		return listTasks;
	}

	public int insert(int jobId, String name, int userId, String startDate, String endDate, int statusId) {
		int rowInserted = 0;
		String query = "INSERT INTO tasks (name, start_date, end_date, user_id, job_id, status_id) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, userId);
			statement.setInt(5, jobId);
			statement.setInt(6, statusId);
			
			rowInserted = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error insert task!");
			ex.printStackTrace();
		}
		
		return rowInserted;
	}

	public List<TaskEntity> findById(int id) {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(id);
				task.setName(result.getString("name"));
				task.setStartDate(result.getDate("start_date"));
				task.setEndDate(result.getDate("end_date"));
				task.setUserId(result.getInt("user_id"));
				task.setJobId(result.getInt("job_id"));
				task.setStatusId(result.getInt("status_id"));
				
				tasks.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error task findById!");
			ex.printStackTrace();
		}
		
		return tasks;
	}

	public int update(int id, String name, String startDate, String endDate,
			int userId, int jobId, int statusId) {
		int rowUpdated = 0;
		String query = "UPDATE tasks SET name = ?, start_date = ?, end_date = ?, "
				+ " user_id = ?, job_id = ?, status_id = ? "
				+ " WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, userId);
			statement.setInt(5, jobId);
			statement.setInt(6, statusId);
			statement.setInt(7, id);
			
			rowUpdated = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error task update!");
			ex.printStackTrace();
		}
		
		return rowUpdated;
	}

	public List<TaskEntity> findByIdWithUserNJobNStatus(int id) {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		String query = "SELECT * FROM tasks t "
				+ "JOIN users u ON t.user_id = u.id "
				+ "JOIN jobs j ON t.job_id = j.id "
				+ "JOIN status s ON t.status_id = s.id "
				+ "WHERE t.id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(id);
				task.setName(result.getString("t.name"));
				task.setStartDate(result.getDate("t.start_date"));
				task.setEndDate(result.getDate("t.end_date"));
				task.setUserId(result.getInt("t.user_id"));
				task.setJobId(result.getInt("t.job_id"));
				task.setStatusId(result.getInt("t.status_id"));
				task.setUserName(result.getString("u.fullname"));
				task.setJobName(result.getString("j.name"));
				task.setStatusName(result.getString("s.name"));
				
				tasks.add(task);
			}
		} catch (Exception ex) {
			System.err.println("Error task findById!");
			ex.printStackTrace();
		}
		
		return tasks;
	}
	
	public int delete(int id) {
		int rowDeleted = 0;
		String query = "DELETE FROM tasks WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			rowDeleted = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error task delete!");
			ex.printStackTrace();
		}
		
		return rowDeleted;
	}
	
	public List<Integer> countTaskByStatus() {
		List<Integer> count = new ArrayList<>();
		String query = "SELECT count(t.status_id) AS count_task FROM tasks t "
				+ "RIGHT JOIN status s ON t.status_id = s.id "
				+ "GROUP BY s.id "
				+ "ORDER BY s.id ASC";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				count.add(result.getInt("count_task"));
			}
		} catch (Exception ex) {
			System.err.println("Error task countTaskByStatusName!");
			ex.printStackTrace();
		}
		
		return count;
	}
}
