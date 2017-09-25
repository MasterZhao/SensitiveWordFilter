package dao.imple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import po.TaskDetail;
import dao.TaskDetailDAO;

public class TaskDetailDAOImple extends BaseDAO<TaskDetail> implements TaskDetailDAO {

	@Override
	public void batchSave(Collection<TaskDetail> details) {
		String sql="insert into taskdetails(docpath,word,num,taskid) values"
				+ "(?,?,?,?)";
		Object[][] params=new Object[details.size()][4];
		List<TaskDetail> detailList=new ArrayList<>(details);

		for(int i=0;i<detailList.size();i++){
			params[i][0]=detailList.get(i).getDocpath();
			params[i][1]=detailList.get(i).getWord();
			params[i][2]=detailList.get(i).getNum();
			params[i][3]=detailList.get(i).getTaskId();
		}
		batch(sql, params);
	}

	@Override
	public Set<TaskDetail> getTaskDetails() {
		String sql="select docpath,word,num,taskid from taskdetails";
		return new HashSet<>(queryForList(sql));
	}

	@Override
	public TaskDetail getTaskDetail(Integer id) {
		String  sql="select docpath,word,num,taskid from taskdetails where taskid=?";
		return query(sql, id);
	}

}
