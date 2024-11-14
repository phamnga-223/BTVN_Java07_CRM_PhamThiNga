package service;

import java.util.List;

import entity.JobEntity;
import repository.JobsRepository;

public class JobsService {
	private JobsRepository repository = new JobsRepository();
	
	public List<JobEntity> findAll() {
		return repository.findAll();
	}
	
	public List<JobEntity> findById(int id) {
		return repository.findById(id);
	}
	
	public boolean isExist(int id) {
		List<JobEntity> listJobs = repository.findById(id);
		
		return (listJobs.size() > 0);
	}
}
