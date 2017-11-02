package dao;

import java.util.Collection;
import java.util.List;

import po.SenWord;
import web.Page;

public interface SenWordsDAO {
	//getWords方法获取敏感词
	public abstract List<SenWord> getWords();
	//根据level来获取全部敏感词
	public abstract List<SenWord> getWordsByLevel(Integer level);
	//插入单个word
	public abstract void insert(SenWord senWord);
	//想数据库批量写入senWord
	public abstract void batchSave(Collection<SenWord> senWords);
	//停用/启用敏感词
	public abstract void changeSymbol(Integer id,Integer symbol);
	//更改敏感词的等级
	public abstract void changeLevel(Integer id,Integer level);
	//获取正在使用的敏感词
	public abstract List<SenWord> getWordsInUse();
	//删除敏感词
	public abstract void delete(Integer id);
	
	public abstract int getWordSymbol(Integer id);
	
	//以下是分页相关方法
	//获取Page<SenWord>，在页面上显示其中的List
	public abstract Page<SenWord> getPage(int pageNo);
	//获取查询出的结果数量,用来计算总共要分几页
	public abstract long getTotalNumber();
	//获取存放SenWord的list，被存放到page中
	public abstract List<SenWord> getPageList(int pageNo);
	
	//获取启用的敏感词
	public abstract Page<SenWord> getStartWordPage(int pageNo);
	public abstract long getTotalStartWordNumber();
	public abstract List<SenWord> getStartWordPageList(int pageNo);
	//获取停用的敏感词
	public abstract Page<SenWord> getStopWordPage(int pageNo);
	public abstract long getTotalStopWordNumber();
	public abstract List<SenWord> getStopWordPageList(int pageNo);
	//从任务表中读取某一任务中出现的敏感词
	public abstract List<SenWord> getWordsFromTaskDetails(int taskId);
	
//	public abstract List<SenWord> getWordUseLike(String word);
	
	public abstract SenWord getWord(String word);
	
	public abstract void deleteAll();
}
