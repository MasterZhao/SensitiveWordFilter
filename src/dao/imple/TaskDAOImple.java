package dao.imple;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import po.Task;
import web.Page;
import dao.TaskDAO;

public class TaskDAOImple extends BaseDAO<Task> implements TaskDAO {

	@Override
	public Task getTast(Integer id) {
		String sql="select id,exetime from task where id=?";
		return query(sql, id);
	}

	@Override
	public void insert(Task task) {
		String sql="insert into task(exetime)values(?)";
		long taskId=insert(sql, task.getExeTime());
		//在完成insert操作的同时，把对应的id传递给当前的task对象，用来传递给taskdetail表中taskid
		task.setId((int)taskId);
	}

	@Override
	public List<Task> getTasks(int pageNo) {
		String sql="select id,exetime from task order by id desc limit ?,?";
		return queryForList(sql, (pageNo-1)*10,10);
	}

	@Override
	public long getTaskNum() {
		String sql="select count(id) from task";
		return getSingleValue(sql);
	}

	@Override
	public Page<Task> getTaskPage(int pageNo) {
		Page<Task> page=new Page<Task>(pageNo);
		page.setTotalItemNumber(getTaskNum());
		page.setList(getTasks(pageNo));
		return page;
	}
	
	
	
}
