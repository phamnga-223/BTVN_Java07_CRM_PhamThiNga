package service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import entity.RoleEntity;
import entity.UserEntity;
import repository.MD5;
import repository.RolesRepository;
import repository.UsersRepository;

public class UsersService {

	private UsersRepository repository = new UsersRepository();
	private RolesRepository roleRepository = new RolesRepository();
	
	public boolean checkLogin(String email, String password) {		
		String passwordEncoded = MD5.genMd5(password);
		
		List<UserEntity> listUser = repository.findByEmailAndPassword(email, passwordEncoded);	
		
		return listUser.size() > 0;
	}
	
	public UserEntity findByEmailAndPassword(String email, String password) {
		UserEntity user = null;
		String passwordEncoded = MD5.genMd5(password);

		List<UserEntity> listUser = repository.findByEmailAndPassword(email, passwordEncoded);
		if (listUser.size() > 0) {
			user = listUser.get(0);
		}
		
		return user;
	}
	
	public List<UserEntity> findAll() {
		List<UserEntity> list = repository.findAll();
		
		for (UserEntity user : list) {
			String fullName = user.getFullname();
			user.setFirstName(getFirstName(fullName));
			user.setLastName(getLastName(fullName));
			
			List<RoleEntity> roles = roleRepository.findById(user.getRoleId());
			if (roles != null && roles.size() > 0) {
				user.setRoleName(roles.get(0).getName());
			}
			
			user.setUserName(getUserName(user.getEmail()));
		}
		
		return list;
	}
	
	public String getFirstName(String fullName) {
		String first = "";
		
		String[] nameSplit = fullName.split(" ");
		int length = nameSplit.length;
		if (length > 0) {
			first = nameSplit[length - 1];
		}
		
		return first;
	}
	
	
	public String getLastName(String fullName) {
		String last = "";
		
		String[] nameSplit = fullName.split(" ");
		int length = nameSplit.length;
		if (length > 1) {
			last = nameSplit[0];
		}
		
		return last;
	}
	
	public String getUserName(String email) {
		String userName = "@";
		
		if (email != null && email.contains("@")) {
			String[] splits = email.split("@");
			userName += splits[0];
		}
		
		return userName;
	}
	
	public boolean deleteUserById(int id) {
		int count = repository.deleteById(id);
		
		return count > 0;
	}
	
	public List<RoleEntity> getAllRole() {
		return roleRepository.findAll();
	}
	
	public boolean insertUser(String email, String password, String fullname, int roleId) {
		return repository.insert(email, MD5.genMd5(password), fullname, roleId) > 0;
	}
	
	public UserEntity findById(int id) {
		return repository.findById(id);
	}
	
	public boolean updateUser(int id, String email, String password, String fullname, int roleId) {
		return repository.update(id, email, MD5.genMd5(password), fullname, roleId) > 0;
	}

}
