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

@WebServlet(name="taskServlet", urlPatterns={"/tasks", "/task-add"})
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
		} if (path.equals("/task-add")) {
			addTask(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/task-add")) {
			addTaskPost(req, resp);
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
		Date startDate = Date.valueOf(req.getParameter("startDate"));
		Date endDate = Date.valueOf(req.getParameter("endDate"));
		
		if (taskService.insert(jobId, name, userId, startDate, endDate)) {
			loadTasks(req, resp);
		} else {
			addTask(req, resp);
		}
		
	}
}
