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
	
	//分页获取task
	public Page<Task> getAllTasks(int pageNo) {
		Page<Task> taskpage=taskDAO.getTaskPage(pageNo);
		return taskpage;
	}
	//根据taskid获取敏感词详情
	public List<WordCount> getWordCountListById(int taskId) {
		
		return wordCountDAO.getWordCountListById(taskId);
	}
	
	//根据taskid获取敏感词分级详情
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
