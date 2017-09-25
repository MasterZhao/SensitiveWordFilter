package test;

import java.sql.Date;
import java.util.Set;

import org.junit.Test;

import po.Task;
import dao.TaskDAO;
import dao.imple.TaskDAOImple;

public class TaskDAOTest {

	TaskDAO taskDAO=new TaskDAOImple();
	
	@Test
	public void testGetTast() {
		
		Task task=taskDAO.getTast(1);
		System.out.println(task);
	}

	@Test
	public void testInsertTask() {
		Task task=new Task();
		task.setExetime(new Date(new java.util.Date().getTime()));
		task.setDescription("本次任务没有发现敏感词");
		taskDAO.insert(task);
		System.out.println(task.getId());
	}

	@Test
	public void testGetTasks() {
		Set<Task> tasks=taskDAO.getTasks();
		System.out.println(tasks);
	}

}
