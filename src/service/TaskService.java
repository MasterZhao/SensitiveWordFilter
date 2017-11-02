package service;

import java.util.List;

import po.LevelCount;
import po.Task;
import po.TaskDetail;
import po.WordCount;
import web.Page;
import dao.LevelCountDAO;
import dao.TaskDAO;
import dao.TaskDetailDAO;
import dao.WordCountDAO;
import dao.imple.LevelCountDAOImple;
import dao.imple.TaskDAOImple;
import dao.imple.TaskDetailDAOImple;
import dao.imple.WordCountDAOImple;

public class TaskService {

	TaskDAO taskDAO=new TaskDAOImple();
	TaskDetailDAO taskDetailDAO=new TaskDetailDAOImple();
	WordCountDAO wordCountDAO=new WordCountDAOImple();
	LevelCountDAO levelCountDAO=new LevelCountDAOImple();
	
	//��ҳ��ȡtask
	public Page<Task> getAllTasks(int pageNo) {
		Page<Task> taskpage=taskDAO.getTaskPage(pageNo);
		return taskpage;
	}
	//����taskid��ȡ���д�����
	public List<WordCount> getWordCountListById(int taskId) {
		
		return wordCountDAO.getWordCountListById(taskId);
	}
	
	//����taskid��ȡ���дʷּ�����
	public List<LevelCount> getLevelCountById(int taskId) {
		
		return levelCountDAO.getLevelCountById(taskId);
	}
	public long getAllTaskNum() {
		
		return taskDAO.getTaskNum();
	}
	public long getTaskDiscoveredNum() {
		
		return taskDetailDAO.getDiscoveredTaskNum();
	}
	public List<LevelCount> getAllLevelCount() {
		
		return levelCountDAO.getAllLevelCount();
	}
}
