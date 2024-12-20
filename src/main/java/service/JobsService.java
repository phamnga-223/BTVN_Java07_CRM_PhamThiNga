package service;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
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
	
	public boolean insertJob(String name, String startDate, String endDate) throws UnsupportedEncodingException {
		byte[] nameBytes = name.getBytes("ISO-8859-1");
		name = new String(nameBytes);
		
		if (startDate != null && startDate.equals("")) {
			startDate = null;
		} 
		if (endDate != null && endDate.equals("")) {
			endDate = null;
		}
		
		return (repository.insert(name, startDate, endDate) > 0);
	}
	
	public boolean updateJob(int id, String name, String startDate, String endDate) throws UnsupportedEncodingException {
		byte[] nameBytes = name.getBytes("ISO-8859-1"); 
		name = new String(nameBytes);
		
		if (startDate != null && startDate.equals("")) {
			startDate = null;
		} 
		if (endDate != null && endDate.equals("")) {
			endDate = null;
		}

		return (repository.update(id, name, startDate, endDate) > 0);
	}
	
	public boolean deleteJob(int id) {
		return (repository.delete(id) > 0);
	}
}
