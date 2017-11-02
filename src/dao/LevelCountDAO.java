package dao;

import java.util.List;

import po.LevelCount;

public interface LevelCountDAO {

	//根据taskId获取levelcount的list
	public abstract List<LevelCount> getLevelCountById(int taskId);
	//获取全部的levelcount
	public abstract List<LevelCount> getAllLevelCount();
	
	
}
