package controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PathConfig;
import entity.RoleEntity;
import entity.StatusEntity;
import entity.TaskEntity;
import entity.UserEntity;
import service.TasksService;
import service.UsersService;

@WebServlet(name = "profileController", urlPatterns = { 
		PathConfig.PATH_PROFILE, PathConfig.PATH_PROFILE_EDIT
	})
public class ProfileController extends HttpServlet {

	private UsersService userService = new UsersService();
	private TasksService taskService = new TasksService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		switch (path) {
			case PathConfig.PATH_PROFILE:
				profile(req, resp);
				break;
			case PathConfig.PATH_PROFILE_EDIT:
				editProfile(req, resp);
				break;
			default: 
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		if (path.equals(PathConfig.PATH_PROFILE_EDIT)) {
			editProfilePost(req, resp);
		}
	}
	
	private void editProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserEntity user = userService.findById(Integer.parseInt(req.getParameter("userId")));
		
		if (user == null) {
			profile(req, resp);
			return;
		}
		
		req.setAttribute("user", user);
		
		req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
	}

	private void editProfilePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		
		if ( !userService.updateUser(id, email, password, fullname) ) {
			editProfile(req, resp);
			
			return;
		}
		
		profile(req, resp);
	}
	
	private void profile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		
		if (cookies == null) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return;
		}
		
		String email = "";
		
		for (Cookie c : cookies) {
			if (c.getName().equals("email")) {
				email = URLDecoder.decode(c.getValue(), "UTF-8");
			}
		}		
		UserEntity user = userService.findByEmail(email);
		
		if (user == null) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return;
		}
		
		List<TaskEntity> listTask = taskService.findByUserIdWithJobNStatus(user.getId());
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
		
		req.getRequestDispatcher("profile.jsp").forward(req, resp);
	}

}
