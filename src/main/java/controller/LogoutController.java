package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PathConfig;

@WebServlet(name="logoutServlet", urlPatterns = {PathConfig.PATH_LOGOUT})
public class LogoutController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie emailCookie = new Cookie("email", "");
		emailCookie.setMaxAge(0);
		resp.addCookie(emailCookie);
		Cookie roleCookie = new Cookie("role", "");
		roleCookie.setMaxAge(0);
		resp.addCookie(roleCookie);
		Cookie passCookie = new Cookie("password", "");
		passCookie.setMaxAge(0);
		resp.addCookie(passCookie);
		Cookie rememberCookie = new Cookie("remember", "");
		rememberCookie.setMaxAge(0);
		resp.addCookie(rememberCookie);
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
