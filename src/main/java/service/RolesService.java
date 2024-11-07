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
	
}
