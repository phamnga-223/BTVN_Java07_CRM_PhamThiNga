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
}
