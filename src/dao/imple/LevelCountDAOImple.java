package dao.imple;

import java.util.List;

import po.LevelCount;
import dao.LevelCountDAO;

public class LevelCountDAOImple extends BaseDAO<LevelCount> implements LevelCountDAO{

	@Override
	public List<LevelCount> getLevelCountById(int taskId) {
		String sql="select"
				+ "(select distinct wordlevel from senword w where w.word=taskdetails.word) as wordlevel,"
				+ "(select count(word)) as levelcount from taskdetails where taskid=? group by wordlevel";
		return queryForList(sql, taskId);
	}

	@Override
	public List<LevelCount> getAllLevelCount() {
		String sql="select"
				+ "(select distinct wordlevel from senword w where w.word=taskdetails.word) as wordlevel,"
				+ "(select count(word)) as levelcount from taskdetails group by wordlevel";
		return queryForList(sql);
	}

}
