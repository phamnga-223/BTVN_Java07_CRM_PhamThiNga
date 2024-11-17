package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.UserEntity;

public class UsersRepository {
	
	public List<UserEntity> findByEmailAndPassword(String email, String password) {
		List<UserEntity> list = new ArrayList<UserEntity>(); 

		String query = "SELECT * FROM users u WHERE u.email = ? AND u.password = ?";
		
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				UserEntity user = new UserEntity();
				user.setId(result.getInt("id"));
				user.setEmail(email);
				user.setRoleId(result.getInt("role_id"));
				
				list.add(user);
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return list;
	}
	
	public List<UserEntity> findAll() {
		List<UserEntity> list = new ArrayList<UserEntity>();
		
		try {
			String query = "SELECT * FROM users WHERE 1";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				UserEntity user = new UserEntity();
				user.setId(result.getInt("id"));
				String fullName = result.getString("fullname");
				user.setFullname(fullName);
				user.setEmail(result.getString("email"));
				user.setRoleId(result.getInt("role_id"));
				
				list.add(user);
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return list;
	}

	public int deleteById(int id) {
		int rowDeleted = 0;
		
		String query = "DELETE FROM users u WHERE u.id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			rowDeleted = statement.executeUpdate();
			
		} catch (Exception ex) {
			System.out.println("deleteById : " + ex.getLocalizedMessage());
		}
		
		return rowDeleted;
	}
	
	public int insert(String email, String password, String fullname, int roleId) {
		int rowInsert = 0;
		String query = "INSERT INTO users(email, password, fullname, role_id) VALUES (?, ?, ?, ?)";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setString(3, fullname);
			statement.setInt(4, roleId);
			
			rowInsert = statement.executeUpdate();
			
		} catch (Exception ex) {
			System.out.println("insert : " + ex.getLocalizedMessage());
		}
		
		return rowInsert;
	}

	public UserEntity findById(int id) {
		UserEntity user = null;
		String query = "SELECT * FROM users WHERE id = ?";
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				user = new UserEntity();
				user.setId(id);
				user.setEmail(result.getString("email"));
				user.setPassword(result.getString("password"));
				user.setFullname(result.getString("fullname"));
				user.setRoleId(result.getInt("role_id"));	
				user.setAvatar(result.getString("avatar"));
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return user;
	}

	public int update(int id, String email, String password, String fullname, int roleId) {
		int rowUpdate = 0;
		String query = "UPDATE users set email = ?, password = ?, fullname = ?, role_id = ? WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setString(3, fullname);
			statement.setInt(4, roleId);
			statement.setInt(5, id);
			
			rowUpdate = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return rowUpdate;
	}
	
	public int update(int id, String email, String password, String fullname) {
		int rowUpdate = 0;
		String query = "UPDATE users set email = ?, password = ?, fullname = ? WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setString(3, fullname);
			statement.setInt(4, id);
			
			rowUpdate = statement.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return rowUpdate;
	}

	public List<UserEntity> findByJobId(int jobId) {
		List<UserEntity> listUsers = new ArrayList<UserEntity>();
		String query = "SELECT u.* "
				+ "FROM jobs j "
				+ "JOIN tasks t ON j.id = t.job_id "
				+ "JOIN users u ON u.id = t.user_id "
				+ "WHERE j.id = ? "
				+ "GROUP BY u.id";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, jobId);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				UserEntity user = new UserEntity();
				user.setId(result.getInt("id"));
				user.setEmail(result.getString("email"));
				user.setFullname(result.getString("fullname"));
				user.setAvatar(result.getString("avatar"));
				listUsers.add(user);
			}
		} catch (Exception ex) {
			System.err.println("Error findByJobId!");
			ex.printStackTrace();
		}
		return listUsers;
	}
	
	public List<UserEntity> findByEmail(String email) {
		List<UserEntity> list = new ArrayList<UserEntity>(); 

		String query = "SELECT * FROM users u WHERE u.email = ?";
		
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				UserEntity user = new UserEntity();
				user.setId(result.getInt("id"));
				user.setEmail(email);
				user.setRoleId(result.getInt("role_id"));
				user.setFullname(result.getString("fullname"));
				
				list.add(user);
			}
		} catch (Exception ex) {
			System.err.println("Error!");
			ex.printStackTrace();
		}
		
		return list;
	}
	
}
