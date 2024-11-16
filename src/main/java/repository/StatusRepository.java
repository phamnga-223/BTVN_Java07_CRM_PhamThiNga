package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.StatusEntity;

public class StatusRepository {

	public List<StatusEntity> findAll() {
		List<StatusEntity> listStatus = new ArrayList<StatusEntity>();
		String query = "SELECT * FROM status";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				StatusEntity status = new StatusEntity();
				status.setId(result.getInt("id"));
				status.setName(result.getString("name"));
				
				listStatus.add(status);
			}
		} catch (Exception ex) {
			System.err.println("Error status findAll!");
			ex.printStackTrace();
		}
		
		return listStatus;
	}
	
	public int findByName(String name) {
		int id = 0;
		String query = "SELECT id FROM status WHERE name = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				id = result.getInt("id");				
			}
		} catch (Exception ex) {
			System.err.println("Error status findByName!");
			ex.printStackTrace();
		}
		
		return id;
	}
}
