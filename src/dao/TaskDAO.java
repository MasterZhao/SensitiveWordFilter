package dao;

import java.util.Set;

import po.Task;

public interface TaskDAO {
	//�����ݿ��л�ȡָ����task��Ϣ
	public abstract Task getTast(Integer id);
	//�����ݿ��в���task
	public abstract void insert(Task task);
	//������ȡtask
	public abstract Set<Task> getTasks();
	
}
