package entity;

public class StatusEntity {
	public static final String STATUS_NOT_START = "NOT_START";
	public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
	public static final String STATUS_FINISH = "FINISH";
	
	private int id;
	private String name;
	
	public StatusEntity() {
	}

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
}
