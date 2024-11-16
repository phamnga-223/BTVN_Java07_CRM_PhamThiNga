package service;

import java.util.List;

import entity.RoleEntity;
import repository.RolesRepository;

public class RolesService {

	private RolesRepository rolesRepository = new RolesRepository();
	
	public RoleEntity findById(int id) {
		List<RoleEntity> list = rolesRepository.findById(id);
		if (list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	public List<RoleEntity> findAll() {
		return rolesRepository.findAll();
	}
	
	public boolean insert(String name, String description) {
		return (rolesRepository.insert(name, description) > 0);
	}
	
	public boolean update(int id, String name, String description) {
		return (rolesRepository.update(id, name, description) > 0);
	}
	
	public boolean delete(int id) {
		return (rolesRepository.delete(id) > 0);
	}
}
