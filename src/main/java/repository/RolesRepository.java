package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import config.MySQLConfig;
import entity.RoleEntity;

public class RolesRepository {
	
	public List<RoleEntity> findById(int id) {
		List<RoleEntity> list = new ArrayList<RoleEntity>();
		
		try {
			String query = "SELECT * FROM roles WHERE id = ?";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				RoleEntity role = new RoleEntity();
				role.setId(id);
				role.setName(result.getString("name"));
				role.setDescription(result.getString("description"));
				
				list.add(role);
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return list;
	}

	public List<RoleEntity> findAll() {
		List<RoleEntity> listRoles = new ArrayList<RoleEntity>();
		String query = "SELECT * FROM roles";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				RoleEntity entity = new RoleEntity();
				entity.setId(result.getInt("id"));
				entity.setName(result.getString("name"));
				entity.setDescription(result.getString("description"));
				
				listRoles.add(entity);
			}
		} catch (Exception e) {
			System.out.println("findAll : " + e.getLocalizedMessage());
		}
		
		return listRoles;
	}
}
