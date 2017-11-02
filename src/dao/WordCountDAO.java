package dao;

import java.util.List;

import po.WordCount;
import web.Page;

public interface WordCountDAO {

	//根据taskId，从taskdetails返回wordcount对象的List
	public abstract List<WordCount> getWordCountListById(Integer taskId);
	//根据taskId分页获取WordCount
	//public abstract Page<WordCount> getWordCountPageById(Integer taskId);
	//根据taskId获取WordCount总数
	//public abstract long getWordCountNumberById(Integer taskId);
	
	//获取全部wordcount的list
	public abstract List<WordCount> getAllWordCountList(int pageNo);
	//分页获取wordcount
	public abstract Page<WordCount> getAllWordPage(int pageNo);
	//统计全部的wordcount数量
	public abstract long getAllWordNumber();
	
}

