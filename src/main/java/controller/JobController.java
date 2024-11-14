package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.JobEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.JobsService;
import service.TasksService;
import service.UsersService;

@WebServlet(name = "groupworkServlet", urlPatterns = {"/groupworks", "/groupwork-details", "/groupwork-edit"})
public class JobController extends HttpServlet {

	private JobsService jobService = new JobsService();
	private TasksService taskService = new TasksService();
	private UsersService userService = new UsersService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/groupworks")) {
			loadGroupWorks(req, resp);
		} else if (path.equals("/groupwork-details")) {
			detailGroupWorks(req, resp);
		} else if (path.equals("/groupwork-edit")) {
			editGroupWorks(req, resp);
		}
	}
	
	private void loadGroupWorks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<JobEntity> listJobs = jobService.findAll();
		
		req.setAttribute("listJobs", listJobs);
		
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
	}
	
	private void detailGroupWorks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int jobId = Integer.parseInt(req.getParameter("id"));
		//Check job is exist?
		
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
	
	private void editGroupWorks(HttpServletRequest req, HttpServletResponse resp) {
		int jobId = Integer.parseInt(req.getParameter("id"));
		
		
	}
}
