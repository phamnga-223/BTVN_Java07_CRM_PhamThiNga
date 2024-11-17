package controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PathConfig;
import entity.RoleEntity;
import entity.UserEntity;
import service.RolesService;
import service.TasksService;
import service.UsersService;

@WebServlet(name = "loginServlet", urlPatterns = {PathConfig.PATH_LOGIN})
public class LoginController extends HttpServlet {

	private UsersService usersService = new UsersService();
	private RolesService rolesService = new RolesService();
	private TasksService tasksService = new TasksService();
	
	/**
	 * 
	 * Tính năng đăng nhập : Tức là lấy email và mật khẩu người dùng nhập kiểm tra nó có tồn tại trong CSDL hay ko
	 * 
	 * Bước 1 : Lấy giá trị tham số email và mật khẩu người dùng truyền vào
	 * Bước 2 : Viết câu truy vấn kiểm tra xem email và mật khẩu đó có tồn tại trong CSDL không
	 * Bước 3 : Lấy dữ liệu từ câu truy vấn và kiểm tra xem có dữ liệu hay không. Nếu có dữ liệu thì sẽ là đăng nhập thành công
	 * 		ngược lại: đăng nhập thất bại
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = "";
		String password = "";
		String remember = "";
		
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for(Cookie item : cookies) {
				String name = item.getName();
				String value = item.getValue();
				
				if (name.equals("email")) {
					email = value;
				}
				
				if (name.equals("password")) {
					password = value;
				} 
				
				if (name.equals("remember")) {
					remember = value;
				}
			}
		}
		
		req.setAttribute("email", URLDecoder.decode(email, "UTF-8"));
		req.setAttribute("password", URLDecoder.decode(password, "UTF-8"));
		req.setAttribute("remember", URLDecoder.decode(remember, "UTF-8"));
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String rememeber = req.getParameter("remember");
				
		boolean isSuccess = usersService.checkLogin(email, password);
		System.out.println(" " + isSuccess);
		
		if (isSuccess && rememeber != null && rememeber.equals("on")) {
			Cookie emailCookie = new Cookie("email", URLEncoder.encode(email, "UTF-8"));
			Cookie passwordCookie = new Cookie("password", URLEncoder.encode(password, "UTF-8"));
			Cookie rememberCookie = new Cookie("remember", URLEncoder.encode(rememeber, "UTF-8"));
			resp.addCookie(emailCookie);
			resp.addCookie(passwordCookie);
			resp.addCookie(rememberCookie);
			
			UserEntity user = usersService.findByEmailAndPassword(email, password);
			RoleEntity userRole = rolesService.findById(user.getRoleId());
			Cookie roleCookie = new Cookie("role", userRole.getName());
			resp.addCookie(roleCookie);
		} else if (isSuccess && rememeber == null) {
			resp.addCookie(new Cookie("remember", ""));
		}
		
		if (isSuccess) {
			List<Integer> counts = tasksService.countTaskByStatus();
			int sumTask = 0;
			for (int count : counts) {
				sumTask += count;
			}
			
			req.setAttribute("counts", counts);
			req.setAttribute("sumTask", sumTask);
			
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} else {
			doGet(req, resp);
		}
	}
}
