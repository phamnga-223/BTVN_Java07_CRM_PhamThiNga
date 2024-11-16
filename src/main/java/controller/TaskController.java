package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

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
import service.StatusService;
import service.TasksService;
import service.UsersService;

@WebServlet(name="taskServlet", urlPatterns={"/tasks", "/task-add", "/task-edit",
		"/task-detail"})
public class TaskController extends HttpServlet {

	private TasksService taskService = new TasksService();
	private UsersService userService = new UsersService();
	private JobsService jobService = new JobsService();
	private StatusService statusService = new StatusService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/tasks")) {
			loadTasks(req, resp);
		} else if (path.equals("/task-add")) {
			addTask(req, resp);
		} else if (path.equals("/task-edit")) {
			editTask(req, resp);
		} else if (path.equals("/task-detail")) {
			detailTask(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/task-add")) {
			addTaskPost(req, resp);
		} else if (path.equals("/task-edit")) {
				editTaskPost(req, resp);
		}
	}
	
	private void loadTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TaskEntity> listTasks = taskService.findAll();
		
		req.setAttribute("tasks", listTasks);
		
		req.getRequestDispatcher("tasks.jsp").forward(req, resp);
	}
	
	private void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserEntity> users = userService.findAll();
		List<JobEntity> jobs = jobService.findAll();
		
		req.setAttribute("users", users);
		req.setAttribute("jobs", jobs);
		
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	
	private void addTaskPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int jobId = Integer.parseInt(req.getParameter("jobId"));
		String name = req.getParameter("name");
		int userId = Integer.parseInt(req.getParameter("userId"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		//Validate
		if (name == null || name.equals("")) {
			addTask(req, resp);
			return;
		}
		
		if (taskService.insert(jobId, name, userId, startDate, endDate)) {
			loadTasks(req, resp);
		} else {
			addTask(req, resp);
		}
		
	}
	
	private void editTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		List<TaskEntity> tasks = taskService.findById(id);
		
		if (tasks.size() > 0) {
			List<UserEntity> users = userService.findAll();
			List<JobEntity> jobs = jobService.findAll();
			List<StatusEntity> statuses = statusService.findAll();
			
			req.setAttribute("task", tasks.get(0));
			req.setAttribute("users", users);
			req.setAttribute("jobs", jobs);
			req.setAttribute("statuses", statuses);
			
			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
		} else {
			loadTasks(req, resp);
		}
	}
	
	private void editTaskPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int userId = Integer.parseInt(req.getParameter("userId"));
		int jobId = Integer.parseInt(req.getParameter("jobId"));
		int statusId = Integer.parseInt(req.getParameter("statusId"));
		
		//Validate
		if (name == null || name.equals("")) {
			editTask(req, resp);
			return;
		}
		
		if (taskService.update(id, name, startDate, endDate, userId, jobId, statusId)) {
			loadTasks(req, resp);
		} else {
			editTask(req, resp);
		}
	}
	
	private void detailTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		List<TaskEntity> tasks = taskService.findByIdWithUserNJobNStatus(id);
		
		if (tasks.size() > 0) {
			req.setAttribute("task", tasks.get(0));
			
			req.getRequestDispatcher("task-details.jsp").forward(req, resp);
		} else {
			loadTasks(req, resp);
		}
	}
}
