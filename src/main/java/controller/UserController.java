package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.RoleEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.TasksService;
import service.UsersService;

@WebServlet(name = "userController", urlPatterns = { "/users", "/user-add", "/user-edit", "/user-detail" })
public class UserController extends HttpServlet {

	private UsersService userService = new UsersService();
	private TasksService taskService = new TasksService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/users")) {
			loadUsers(req, resp);
		} else if (path.equals("/user-add")) {
			addUser(req, resp);
		} else if (path.equals("/user-edit")) {
			editUser(req, resp);
		} else if (path.equals("/user-detail")) {
			detailUser(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals("/user-add")) {
			addUserPost(req, resp);
		} else if (path.equals("/user-edit")) {
			editUserPost(req, resp);
		}
	}
	
	private void loadUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id != null && !req.getServletPath().contains("edit") && !req.getServletPath().contains("detail")) {
			//Tinh nang xoa
			userService.deleteUserById(Integer.parseInt(id));
		}
		
		List<UserEntity> users = userService.findAll();
		
		req.setAttribute("users", users);
		
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}

	private void addUserPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		byte[] fullnameBytes = req.getParameter("fullname").getBytes("ISO-8859-1"); 
		String fullname = new String(fullnameBytes);
		int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");
		
		if( !userService.insertUser(email, password, fullname, roleId) ) {
			addUser(req, resp);
			
			return;
		}
		
		loadUsers(req, resp);
	}
	
	private void addUser(HttpServletRequest req, HttpServletResponse  resp) throws ServletException, IOException {		
		List<RoleEntity> listRole = userService.getAllRole();
		
		req.setAttribute("listRole", listRole);
		
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}
	
	private void editUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> listRole = userService.getAllRole();
		UserEntity user = userService.findById(Integer.parseInt(req.getParameter("id")));
		
		req.setAttribute("listRole", listRole);
		req.setAttribute("user", user);
		
		req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
	}

	private void editUserPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		byte[] fullnameBytes = req.getParameter("fullname").getBytes("ISO-8859-1");
		String fullname = new String(fullnameBytes);
		int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");
		
		if ( !userService.updateUser(id, email, password, fullname, roleId) ) {
			editUser(req, resp);
			
			return;
		}
		
		loadUsers(req, resp);
	}
	
	private void detailUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("id"));
		UserEntity user = userService.findById(userId);
		
		if (user == null) {
			loadUsers(req, resp);
		}
		
		List<TaskEntity> listTask = taskService.findByUserId(userId);
		List<TaskEntity> listNotStartTask = taskService.resumeTaskByStatusName(listTask, StatusEntity.STATUS_NOT_START);
		List<TaskEntity> listInProgressTask = taskService.resumeTaskByStatusName(listTask, StatusEntity.STATUS_IN_PROGRESS);
		List<TaskEntity> listFinishTask = taskService.resumeTaskByStatusName(listTask, StatusEntity.STATUS_FINISH);
		int tasksSum = listTask.size();
		String notStartPercent = "0";
		String inProgressPercent = "0";
		String finishPercent = "0";
		if (tasksSum > 0) {
			double notStart = ((double)listNotStartTask.size() / tasksSum) * 100;
			double inProgress = ((double)listInProgressTask.size() / tasksSum) * 100;
			double finish = ((double)listFinishTask.size() / tasksSum) * 100;
			
			String format = "%.2f";
			notStartPercent = String.format(format, notStart);
			inProgressPercent = String.format(format, inProgress);
			finishPercent = String.format(format, finish);
		}
		
		req.setAttribute("user", user);
		req.setAttribute("listTask", listTask);
		req.setAttribute("listNotStartTask", listNotStartTask);
		req.setAttribute("listInProgressTask", listInProgressTask);
		req.setAttribute("listFinishTask", listFinishTask);
		req.setAttribute("notStartPercent", notStartPercent);
		req.setAttribute("inProgressPercent", inProgressPercent);
		req.setAttribute("finishPercent", finishPercent);
		
		req.getRequestDispatcher("user-details.jsp").forward(req, resp);
	}
}
