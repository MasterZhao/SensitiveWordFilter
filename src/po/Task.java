package po;

import java.sql.Date;
import java.util.List;

public class Task {

	private Integer id;
	private Date exeTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getExeTime() {
		return exeTime;
	}
	public void setExeTime(Date exeTime) {
		this.exeTime = exeTime;
	}
	//构造方法
	public Task() {
		
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", exeTime=" + exeTime + "]";
	}

//	public Task(Integer id, Date exeTime) {
//		super();
//		this.id = id;
//		this.exeTime = exeTime;
//	}

}
