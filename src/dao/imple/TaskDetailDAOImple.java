package dao.imple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import po.Doc;
import po.TaskDetail;
import dao.TaskDetailDAO;

public class TaskDetailDAOImple extends BaseDAO<TaskDetail> implements TaskDetailDAO {

	@Override
	public void batchSave(Collection<TaskDetail> taskDetails) {
		String sql="insert into taskdetails(docpath,word,taskid) values"
				+ "(?,?,?)";
		Object[][] params=new Object[taskDetails.size()][3];
		List<TaskDetail> detailList=new ArrayList<>(taskDetails);

		for(int i=0;i<detailList.size();i++){
			params[i][0]=detailList.get(i).getDocpath();
			params[i][1]=detailList.get(i).getWord();
			params[i][2]=detailList.get(i).getTaskId();
		}
		batch(sql, params);
	}

	@Override
	public Set<TaskDetail> getTaskDetails(Integer docpath) {
		String sql="select docpath,word,taskid from taskdetails";
		return new HashSet<>(queryForList(sql));
	}

	@Override
	public TaskDetail getTaskDetail(Integer id) {
		String  sql="select docpath,word,taskid from taskdetails where taskid=?";
		return query(sql, id);
	}

	@Override
	public long getWordNum(String docpath) {
		String sql="select count(distinct word) from taskdetail where docpath=?";
		return getSingleValue(sql, docpath);
	}
	
	@Override
	public void deletedocdetail(String docpath) {
		String sql="";
		
	}

	@Override
	public long getDocNumber(Integer taskId) {
		String sql="select count(distinct word) from taskdetails where taskid=?";
		return getSingleValue(sql, taskId);
	}

	@Override
	public List<String> getWordsByTaskId(Integer taskId) {
		String sql="select DISTINCT word from taskdetails where taskid=?";
		return getSingleValue(sql, taskId);
	}

	@Override
	public long getWordNumByTaskId(Integer taskId) {
		String sql="select count(word) from taskdetails where taskid=?";
		return getSingleValue(sql, taskId);
	}

	@Override
	public long getWordDistinctNumByTaskId(Integer taskId) {
		String sql="select count(distinct word) from taskdetails where taskid=?";
		return getSingleValue(sql, taskId);
	}

	@Override
	public long getDiscoveredTaskNum() {
		String sql="select count(distinct taskid) from taskdetails";
		return getSingleValue(sql);
	}

	@Override
	public long getDiscoveredWordNum() {
		String sql="select count(distinct word) from taskdetails";
		return getSingleValue(sql);
	}


}
