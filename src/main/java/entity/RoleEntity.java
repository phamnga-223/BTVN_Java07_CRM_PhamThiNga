package entity;

public class RoleEntity {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_MANAGER = "ROLE_MANAGER";
	public static final String ROLE_USER = "ROLE_USER";
	
	private int id;
	private String name;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	public RoleEntity() {
	}
}
