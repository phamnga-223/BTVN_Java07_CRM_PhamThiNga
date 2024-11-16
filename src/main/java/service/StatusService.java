package service;

import java.util.List;

import entity.StatusEntity;
import repository.StatusRepository;

public class StatusService {

	private StatusRepository repository = new StatusRepository();
	
	public List<StatusEntity> findAll() {
		return repository.findAll();
	}
	
	public int findByName(String name) {
		return repository.findByName(name);
	}
}
