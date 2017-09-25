package dao;

import java.util.Collection;
import java.util.Set;

import po.TaskDetail;

public interface TaskDetailDAO {
	//����д��detail
	public void batchSave(Collection<TaskDetail> details);
	//������ȡdetails
	public Set<TaskDetail> getTaskDetails();
	
	public TaskDetail getTaskDetail(Integer id);
	
}
