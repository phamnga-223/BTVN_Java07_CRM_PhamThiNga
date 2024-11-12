package filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.http.HttpRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.RoleEntity;
import entity.UserEntity;
import service.RolesService;
import service.UsersService;

//urlPatterns : Duong dan se kich hoat filter
@WebFilter(filterName = "authenFilter", urlPatterns = {"/users", "/user-add", "/user-edit", "/user-detail"
													  	, "/groupworks"})
public class AuthenticationFilter extends HttpFilter {

	private UsersService userService = new UsersService();
	private RolesService roleService = new RolesService();
	
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
		
		//Bước 2		
		if (!role.equals(RoleEntity.ROLE_ADMIN)) {
			req.getRequestDispatcher("index.jsp").forward(req, res);
		}
		
		//Bước 3: Cho đi tiếp
		chain.doFilter(req, res);
	}
	
}
