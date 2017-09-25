package dao;

import java.util.Collection;
import java.util.Set;

import po.TaskDetail;

public interface TaskDetailDAO {
	//批量写入detail
	public void batchSave(Collection<TaskDetail> details);
	//批量读取details
	public Set<TaskDetail> getTaskDetails();
	
	public TaskDetail getTaskDetail(Integer id);
	
}
