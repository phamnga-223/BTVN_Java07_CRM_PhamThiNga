package service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import config.StringUtil;
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
	
	public boolean insert(String name, String description) throws UnsupportedEncodingException {
		return (rolesRepository.insert(StringUtil.convert(name), StringUtil.convert(description)) > 0);
	}
	
	public boolean update(int id, String name, String description) throws UnsupportedEncodingException {
		return (rolesRepository.update(id, StringUtil.convert(name), StringUtil.convert(description)) > 0);
	}
	
	public boolean delete(int id) {
		return (rolesRepository.delete(id) > 0);
	}
}
