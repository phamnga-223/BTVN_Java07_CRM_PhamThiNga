package filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PathConfig;
import entity.RoleEntity;
import service.TasksService;

//urlPatterns : Duong dan se kich hoat filter
@WebFilter(filterName = "authenFilter", urlPatterns = {
		PathConfig.PATH_USER, PathConfig.PATH_USER_ADD, PathConfig.PATH_USER_EDIT, PathConfig.PATH_USER_DTL,
		PathConfig.PATH_JOB, PathConfig.PATH_JOB_ADD, PathConfig.PATH_JOB_EDIT, PathConfig.PATH_JOB_DTL, PathConfig.PATH_JOB_DEL,
		PathConfig.PATH_TASK, PathConfig.PATH_TASK_ADD, PathConfig.PATH_TASK_EDIT, PathConfig.PATH_TASK_DTL, PathConfig.PATH_TASK_DEL,
		PathConfig.PATH_ROLE, PathConfig.PATH_ROLE_ADD, PathConfig.PATH_ROLE_EDIT,
		PathConfig.PATH_PROFILE, PathConfig.PATH_PROFILE_EDIT
	})
public class AuthenticationFilter extends HttpFilter {
	
	private TasksService tasksService = new TasksService();
	
	/**
	 * 
	 * Khi người dùng gọi link /users
	 * B1: Kiểm tra xem người đã đăng nhập rồi hay chưa. nếu đã đăng nhập thì cho thấy nội dung còn chưa đăng nhập thì đá ra trang
	 * login.
	 * B2: Đối với link /users chỉ có user có quyền là admin thì mới vô được còn không có quyền admin đá về trang dashboard
	 */
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Filter actived");		
		Cookie [] cookies = req.getCookies();
		if (cookies == null) {
			req.getRequestDispatcher("login.jsp").forward(req, res);
		}
		String email = "";
		String role = "";
		for (Cookie cookie : cookies) {
			String value = URLDecoder.decode(cookie.getValue(), "UTF-8");
			if (cookie.getName().equals("email")) {
				email = value;
			} else if (cookie.getName().equals("role")) {
				role = value;
			}
		}		
		
		//Bước 1:
		if (email.equals("")) {
			req.getRequestDispatcher("login.jsp").forward(req, res);
		}
		
		//Bước 2: Check role
		String path = req.getServletPath();
		if (canAccessAsRole(role, path)) {
			//Cho đi tiếp
			chain.doFilter(req, res);
		} else {
			List<Integer> counts = tasksService.countTaskByStatus();
			int sumTask = 0;
			for (int count : counts) {
				sumTask += count;
			}
			
			req.setAttribute("counts", counts);
			req.setAttribute("sumTask", sumTask);
			
			req.getRequestDispatcher("index.jsp").forward(req, res);
		}		
		
	}
	
	private boolean canAccessAsRole(String role, String path) {
		boolean result = false;
		
		switch (role) {
			case RoleEntity.ROLE_ADMIN:
				if (PathConfig.PATH_ADMIN.contains(path)) {
					result = true;
				}
				break;
			case RoleEntity.ROLE_MANAGER:
				if (PathConfig.PATH_MANAGER.contains(path)) {
					result = true;
				}
			case RoleEntity.ROLE_USER:
				if (PathConfig.PATH_AU_USER.contains(path)) {
					result = true;
				}
			default:
				break;
		}
		
		return result;
	}
}
