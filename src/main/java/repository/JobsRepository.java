package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
}
