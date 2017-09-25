package dao.imple;

import java.util.LinkedHashSet;
import java.util.Set;

import po.Task;
import dao.TaskDAO;

public class TaskDAOImple extends BaseDAO<Task> implements TaskDAO {

	@Override
	public Task getTast(Integer id) {
		String sql="select id,exetime,description from task where id=?";
		return query(sql, id);
	}

	@Override
	public void insert(Task task) {
		String sql="insert into task(exetime,description)values(?,?)";
		long taskId=insert(sql, task.getExetime(),task.getDescription());
		task.setId((int)taskId);
	}

	@Override
	public Set<Task> getTasks() {
		String sql="select id,exetime,description from task";
		return new LinkedHashSet<Task>(queryForList(sql));
	}

}
