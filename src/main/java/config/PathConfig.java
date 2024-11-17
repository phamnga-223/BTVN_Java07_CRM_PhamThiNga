package config;

import java.util.List;

public class PathConfig {
	public static final String PATH_LOGIN = "/login";
	public static final String PATH_LOGOUT = "/logout";
	
	public static final String PATH_USER = "/users";
	public static final String PATH_USER_ADD = "/user-add";
	public static final String PATH_USER_EDIT = "/user-edit";
	public static final String PATH_USER_DTL = "/user-detail";
	
	public static final String PATH_JOB = "/groupworks";
	public static final String PATH_JOB_ADD = "/groupwork-add";
	public static final String PATH_JOB_EDIT = "/groupwork-edit";
	public static final String PATH_JOB_DTL = "/groupwork-details";
	public static final String PATH_JOB_DEL = "/groupwork-delete";	
	
	public static final String PATH_TASK = "/tasks";
	public static final String PATH_TASK_ADD = "/task-add";
	public static final String PATH_TASK_EDIT = "/task-edit";
	public static final String PATH_TASK_DTL = "/task-details";
	public static final String PATH_TASK_DEL = "/task-delete";
	
	public static final String PATH_ROLE = "/roles";
	public static final String PATH_ROLE_ADD = "/role-add";
	public static final String PATH_ROLE_EDIT = "/role-edit";
	
	public static final String PATH_PROFILE = "/profile";
	public static final String PATH_PROFILE_EDIT = "/profile-edit";
	
	public static final List<String> PATH_ADMIN = List.of(
		PATH_USER, PATH_USER_ADD, PATH_USER_EDIT, PATH_USER_DTL,
		PATH_JOB, PATH_JOB_DTL,
		PATH_ROLE, PATH_ROLE_ADD, PATH_ROLE_EDIT
	);
	
	public static final List<String> PATH_MANAGER = List.of(
		PATH_JOB, PATH_JOB_ADD, PATH_JOB_EDIT, PATH_JOB_DTL, PATH_JOB_DEL,
		PATH_TASK, PATH_TASK_ADD, PATH_TASK_EDIT, PATH_TASK_DTL, PATH_TASK_DEL,
		PATH_PROFILE, PATH_PROFILE_EDIT,
		PATH_USER
	);
	
	public static final List<String> PATH_AU_USER = List.of(
		PATH_TASK, PATH_TASK_EDIT, PATH_TASK_DTL,
		PATH_PROFILE, PATH_PROFILE_EDIT
	); 
}
 