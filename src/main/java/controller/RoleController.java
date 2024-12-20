package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PathConfig;
import entity.RoleEntity;
import service.RolesService;

@WebServlet(name="roleServlet", urlPatterns={ 
	PathConfig.PATH_ROLE, PathConfig.PATH_ROLE_ADD, PathConfig.PATH_ROLE_EDIT
})
public class RoleController extends HttpServlet {
	
	private RolesService roleService = new RolesService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		switch(path) {
			case PathConfig.PATH_ROLE:
				loadRoles(req, resp);
				break;
			case PathConfig.PATH_ROLE_ADD:
				addRole(req, resp);
				break;
			case PathConfig.PATH_ROLE_EDIT:
				editRole(req, resp);
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		switch(path) {
			case PathConfig.PATH_ROLE_ADD:
				addRolePost(req, resp);
				break;
			case PathConfig.PATH_ROLE_EDIT:
				editRolePost(req, resp);
				break;
			case PathConfig.PATH_ROLE:
				deleteRolePost(req, resp);
				break;
			default:
				break;
		}
	}
	
	private void loadRoles(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> roles = roleService.findAll();
		
		req.setAttribute("roles", roles);
		
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);
	}

	private void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
	
	private void addRolePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		
		if (name == null || name.equals("")) {
			addRole(req, resp);
			return;
		} 
		
		if (roleService.insert(name, description)) {
			loadRoles(req, resp);
		} else {
			addRole(req, resp);
		}
	}
	
	private void editRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		RoleEntity role = roleService.findById(id);
		
		if (role == null) {
			loadRoles(req, resp);
		} else {
			req.setAttribute("role", role);
			
			req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
		}
	}
	
	private void editRolePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		
		//Validate
		if (name == null || name.equals("")) {
			editRole(req, resp);
			return;
		} 
		
		if (roleService.update(id, name, description)) {
			loadRoles(req, resp);
		} else {
			editRole(req, resp);
		}
	}
	
	private void deleteRolePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		roleService.delete(id);
		
		loadRoles(req, resp);
	}
}
