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
	
//	@Test
//	public void testBatchSave() {
//		
//		Collection<TaskDetail> details=new ArrayList<>();
//		TaskDetail taskDetail1=new TaskDetail();
//		taskDetail1.setDocpath("D:\\我的文档\\共产主义的失败");
//		taskDetail1.setWord("共产党");
//		taskDetail1.setNum(3);
//		taskDetail1.setTaskId(1);
//		
//		TaskDetail taskDetail2=new TaskDetail();
//		taskDetail2.setDocpath("D:\\我的文档\\夜总会指南");
//		taskDetail2.setWord("泡妞");
//		taskDetail2.setNum(3);
//		taskDetail2.setTaskId(2);
//		
//		details.add(taskDetail1);
//		details.add(taskDetail2);
//		taskDetailDAO.batchSave(details);
//	}

	@Test
	public void testGetTaskDetails() {
		int id=1;
		Set<TaskDetail> details=taskDetailDAO.getTaskDetails(id);
		for(TaskDetail taskDetail:details){
			System.out.println(taskDetail);
		}
		
	}

	@Test
	public void testGetTaskDetail(){
		Integer id=18;
		TaskDetail taskDetail=taskDetailDAO.getTaskDetail(id);
		System.out.println(taskDetail);
	}
	
	@Test
	public void testWordByTaskId(){
		Integer taskId=18;
		System.out.println(taskDetailDAO.getWordsByTaskId(taskId));
	}
	
	@Test
	public void testGetWordNumByTaskId(){
		Integer taskId=18;
		System.out.println(taskDetailDAO.getWordNumByTaskId(taskId));
	}
	
	@Test
	public void testGetWordDistinctNumByTaskId(){
		Integer taskId=18;
		System.out.println(taskDetailDAO.getWordDistinctNumByTaskId(taskId));
	}
	
	@Test
	public void testGetDiscoveredTaskNum(){
		System.out.println(taskDetailDAO.getDiscoveredTaskNum());
		
	}
	
}
