package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import config.MySQLConfig;
import entity.JobEntity;

public class JobsRepository {

	public List<JobEntity> findAll() {
		List<JobEntity> listJobs = new ArrayList<JobEntity>();
		String query = "SELECT * FROM jobs";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				JobEntity job = new JobEntity();
				job.setId(result.getInt("id"));
				job.setName(result.getString("name"));
				job.setStartDate(result.getDate("start_date"));
				job.setEndDate(result.getDate("end_date"));
				
				listJobs.add(job);
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return listJobs;
	}

	public List<JobEntity> findById(int id) {
		List<JobEntity> listJobs = new ArrayList<JobEntity>();
		String query = "SELECT * FROM jobs WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				JobEntity job = new JobEntity();
				job.setId(id);
				job.setName(result.getString("name"));
				job.setStartDate(result.getDate("start_date"));
				job.setEndDate(result.getDate("end_date"));
				listJobs.add(job);
			}
		} catch (Exception ex) {
			System.err.println("Error findById!");
			ex.printStackTrace();
		}
		
		return listJobs;
	}
	
	public int update(int id, String name, String startDate, String endDate) {
		int rowUpdate = 0;
		String query = "UPDATE jobs SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			statement.setInt(4, id);
			
			rowUpdate = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error update!");
			ex.printStackTrace();
		}
		
		return rowUpdate;
	}
	
	public int insert(String name, String startDate, String endDate) {
		int rowInsert = 0;
		String query = "INSERT INTO jobs(name, start_date, end_date) VALUES (?, ?, ?)";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);
			
			rowInsert = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error insert!");
			ex.printStackTrace();
		}
		
		return rowInsert;
	}
	
	public int delete(int id) {
		int rowDelete = 0;
		String query = "DELETE FROM jobs WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			rowDelete = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error delete!");
			ex.printStackTrace();
		}
		
		return rowDelete;
	}
}
