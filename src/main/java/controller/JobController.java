package controller;

import java.io.IOException;
import java.sql.Date;
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
		"/groupworks", "/groupwork-details"
		, "/groupwork-edit", "/groupwork-add", PathConfig.PATH_JOB_DEL
	})
public class JobController extends HttpServlet {

	private JobsService jobService = new JobsService();
	private TasksService taskService = new TasksService();
	private UsersService userService = new UsersService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/groupworks") || path.equals(PathConfig.PATH_JOB_DEL)) {
			loadGroupWorks(req, resp);
		} else if (path.equals("/groupwork-details")) {
			detailGroupWorks(req, resp);
		} else if (path.equals("/groupwork-edit")) {
			editGroupWork(req, resp);
		} else if (path.equals("/groupwork-add")) {
			addGroupWork(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/groupwork-edit")) {
			editGroupWorkPost(req, resp);
		} else if (path.equals("/groupwork-add")) {
			addGroupWorkPost(req, resp);
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
		Date startDate = Date.valueOf(req.getParameter("startDate"));
		Date endDate = Date.valueOf(req.getParameter("endDate"));
		
		if (!jobService.updateJob(id, name, startDate, endDate)) {
			editGroupWork(req, resp);
		}
		
		loadGroupWorks(req, resp);
	}
	
	private void addGroupWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	
	private void addGroupWorkPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		Date startDate = Date.valueOf(req.getParameter("startDate"));
		Date endDate = Date.valueOf(req.getParameter("endDate"));
		
		if (!jobService.insertJob(name, startDate, endDate)) {
			addGroupWork(req, resp);
		}
		
		loadGroupWorks(req, resp);
	}
}
