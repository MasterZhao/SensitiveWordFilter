package dao;

import java.util.List;

import po.LevelCount;

public interface LevelCountDAO {

	//����taskId��ȡlevelcount��list
	public abstract List<LevelCount> getLevelCountById(int taskId);
	//��ȡȫ����levelcount
	public abstract List<LevelCount> getAllLevelCount();
	
	
}
