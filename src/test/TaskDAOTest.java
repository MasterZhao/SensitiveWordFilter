package test;

import java.sql.Date;

import org.junit.Test;

import po.Task;
import web.Page;
import dao.TaskDAO;
import dao.imple.TaskDAOImple;

public class TaskDAOTest {

	TaskDAO taskDAO=new TaskDAOImple();
	
	@Test
	public void testGetTast() {
		Task task=taskDAO.getTast(1);
		System.out.println(task.getExeTime());
		
	}

	@Test
	public void testInsertTask() {
		Task task=new Task();
		task.setExeTime(new Date(new java.util.Date().getTime()));
		taskDAO.insert(task);
		System.out.println(task.getId());
	}

	@Test
	public void testGetTaskPage() {
		int pageNo=1;
		Page<Task> page=taskDAO.getTaskPage(pageNo);
		System.out.println(page.getList());
		System.out.println(page.getTotalPageNumber());
	}
	@Test
	public void testGetTaskNum(){
		System.out.println(taskDAO.getTaskNum());
	}
	
	
}
