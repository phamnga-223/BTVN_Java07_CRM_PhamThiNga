package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PathConfig;
import entity.JobEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.JobsService;
import service.TasksService;
import service.UsersService;

@WebServlet(name = "groupworkServlet", urlPatterns = {
		PathConfig.PATH_JOB, PathConfig.PATH_JOB_DTL, PathConfig.PATH_JOB_ADD, PathConfig.PATH_JOB_EDIT, PathConfig.PATH_JOB_DEL
	})
public class JobController extends HttpServlet {

	private JobsService jobService = new JobsService();
	private TasksService taskService = new TasksService();
	private UsersService userService = new UsersService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		switch (path) {
			case PathConfig.PATH_JOB, PathConfig.PATH_JOB_DEL:
				loadGroupWorks(req, resp);
				break;
			case PathConfig.PATH_JOB_DTL:
				detailGroupWorks(req, resp);
				break;
			case PathConfig.PATH_JOB_ADD:
				addGroupWork(req, resp);
				break;
			case PathConfig.PATH_JOB_EDIT:
				editGroupWork(req, resp);
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		switch (path) {
			case PathConfig.PATH_JOB_EDIT:
				editGroupWorkPost(req, resp);
				break;
			case PathConfig.PATH_JOB_ADD:
				addGroupWorkPost(req, resp);
				break;
			default:
				break;
		}
	}
	
	private void loadGroupWorks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		String id = req.getParameter("id");
		if (id != null && path.equals(PathConfig.PATH_JOB_DEL)) {
			//Tinh nang xoa
			jobService.deleteJob(Integer.parseInt(id));
		}
		
		List<JobEntity> listJobs = jobService.findAll();
		
		req.setAttribute("listJobs", listJobs);
		
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
	}
	
	private void detailGroupWorks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int jobId = Integer.parseInt(req.getParameter("id"));
		
		//Check job is exist?
		if (!jobService.isExist(jobId)) {
			loadGroupWorks(req, resp);
		}
		
		List<TaskEntity> listTaskByStatus = taskService.countTasksByStatus(jobId);
		String notStartPercent = "0";
		String inProgressPercent = "0";
		String finishPercent = "0";
		if (listTaskByStatus.size() > 0) {
			int sumTasks = 0;
			int notStart = 0;
			int inProgress = 0;
			int finish = 0;
					
			for (TaskEntity task : listTaskByStatus) {
				sumTasks += task.getCountTask();

				if (task.getStatusName().equals(StatusEntity.STATUS_NOT_START)) {
					notStart = task.getCountTask();
				} else if (task.getStatusName().equals(StatusEntity.STATUS_IN_PROGRESS)) {
					inProgress = task.getCountTask();
				} else if (task.getStatusName().equals(StatusEntity.STATUS_FINISH)) {
					finish = task.getCountTask();
				}				
			}
			
			String format = "%.2f";
			notStartPercent = String.format(format, ((double)notStart / sumTasks) * 100);
			inProgressPercent = String.format(format, ((double)inProgress / sumTasks) * 100);
			finishPercent = String.format(format, ((double)finish / sumTasks) * 100);
		}
		
		List<UserEntity> listUsers = userService.findByJobId(jobId);
		Map<UserEntity, List<TaskEntity>> map = new HashMap<UserEntity, List<TaskEntity>>();
		for (UserEntity user : listUsers) {
			List<TaskEntity> listTasks = taskService.findByUserIdAndJobId(user.getId(), jobId);
			map.put(user, listTasks);
		}
		
		req.setAttribute("notStartPercent", notStartPercent);
		req.setAttribute("inProgressPercent", inProgressPercent);
		req.setAttribute("finishPercent", finishPercent);
		req.setAttribute("map", map);
		
		req.getRequestDispatcher("groupwork-details.jsp").forward(req, resp);
	}
	
	private void editGroupWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int jobId = Integer.parseInt(req.getParameter("id"));
		
		if (!jobService.isExist(jobId)) {
			loadGroupWorks(req, resp);
		}
		
		JobEntity job = jobService.findById(jobId).get(0);
		
		req.setAttribute("job", job);
		
		req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
	}
	
	private void editGroupWorkPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		if (name == null || name.equals("")) {
			editGroupWork(req, resp);
			return;
		}
		
		if (!jobService.updateJob(id, name, startDate, endDate)) {
			editGroupWork(req, resp);
			return;
		}
		
		loadGroupWorks(req, resp);
	}
	
	private void addGroupWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	
	private void addGroupWorkPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		if (name == null || name.equals("")) {
			addGroupWork(req, resp);
			return;
		}
		
		if (!jobService.insertJob(name, startDate, endDate)) {
			addGroupWork(req, resp);
			return;
		}
		
		loadGroupWorks(req, resp);
	}
}
