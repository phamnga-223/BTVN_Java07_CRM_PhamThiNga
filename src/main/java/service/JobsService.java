package service;

import java.util.List;

import entity.JobEntity;
import repository.JobsRepository;

public class JobsService {
	private JobsRepository repository = new JobsRepository();
	
	public List<JobEntity> findAll() {
		return repository.findAll();
	}
}
