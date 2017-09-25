package dao;

import java.util.Set;

import po.Task;

public interface TaskDAO {
	//从数据库中获取指定的task信息
	public abstract Task getTast(Integer id);
	//向数据库中插入task
	public abstract void insert(Task task);
	//批量获取task
	public abstract Set<Task> getTasks();
	
}
