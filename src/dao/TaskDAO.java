package dao;

import java.util.List;

import po.Task;
import web.Page;

public interface TaskDAO {
	//从数据库中获取指定的task信息
	public abstract Task getTast(Integer id);
	//向数据库中插入task
	public abstract void insert(Task task);
	//批量获取task
	public abstract List<Task> getTasks(int pageNo);
	//获取task总数
	public abstract long getTaskNum();
	
	public abstract Page getTaskPage(int pageNo);
}
