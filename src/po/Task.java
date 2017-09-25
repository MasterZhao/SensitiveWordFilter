package po;

import java.sql.Date;

public class Task {

	private Integer id;
	private Date exeTime;
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getExetime() {
		return exeTime;
	}
	public void setExetime(Date exeTime) {
		this.exeTime = exeTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	//构造方法
	public Task() {
		
	}
	
	public Task(Integer id, Date exeTime, String description) {
		super();
		this.id = id;
		this.exeTime = exeTime;
		this.description = description;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", exetime=" + exeTime + ", description="
				+ description + "]";
	}
}
