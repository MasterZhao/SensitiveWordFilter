package dao;

import java.util.List;

import po.Task;
import web.Page;

public interface TaskDAO {
	//�����ݿ��л�ȡָ����task��Ϣ
	public abstract Task getTast(Integer id);
	//�����ݿ��в���task
	public abstract void insert(Task task);
	//������ȡtask
	public abstract List<Task> getTasks(int pageNo);
	//��ȡtask����
	public abstract long getTaskNum();
	
	public abstract Page getTaskPage(int pageNo);
}
