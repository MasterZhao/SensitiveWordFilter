package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import po.TaskDetail;
import dao.TaskDetailDAO;
import dao.imple.TaskDetailDAOImple;


public class TaskDetailDAOTest {

	private TaskDetailDAO taskDetailDAO=new TaskDetailDAOImple();
	
	@Test
	public void testBatchSave() {
		
		Collection<TaskDetail> details=new ArrayList<>();
		TaskDetail taskDetail1=new TaskDetail();
		taskDetail1.setDocpath("D:\\�ҵ��ĵ�\\���������ʧ��");
		taskDetail1.setWord("������");
		taskDetail1.setNum(3);
		taskDetail1.setTaskId(1);
		
		TaskDetail taskDetail2=new TaskDetail();
		taskDetail2.setDocpath("D:\\�ҵ��ĵ�\\ҹ�ܻ�ָ��");
		taskDetail2.setWord("���");
		taskDetail2.setNum(3);
		taskDetail2.setTaskId(2);
		
		details.add(taskDetail1);
		details.add(taskDetail2);
		taskDetailDAO.batchSave(details);
	}

	@Test
	public void testGetTaskDetails() {
		Set<TaskDetail> details=taskDetailDAO.getTaskDetails();
		for(TaskDetail taskDetail:details){
			System.out.println(taskDetail);
		}
		
	}

	@Test
	public void testGetTaskDetail(){
		Integer id=2;
		TaskDetail taskDetail=taskDetailDAO.getTaskDetail(id);
		System.out.println(taskDetail);
	}
}
