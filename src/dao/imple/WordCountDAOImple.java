package dao.imple;

import java.util.List;

import dao.WordCountDAO;
import po.WordCount;
import web.Page;

public class WordCountDAOImple extends BaseDAO<WordCount> implements WordCountDAO {
	//下面三个方法用来通过taskId获取wordcount
	@Override
	//从taskdetail表中选取word和count，封装为WordCount对象
	public List<WordCount> getWordCountListById(Integer taskId) {
		
		String sql="select word,count(word) as count from taskdetails where taskid=? group by word";
		return queryForList(sql, taskId);
	}

//	@Override
//	public Page<WordCount> getWordCountPageById(Integer taskId) {
//		Page<WordCount> page=new Page<WordCount>(pageNo);
//		page.setTotalItemNumber(getWordCountNumberById(taskId));
//		page.setList(getWordCountListById(taskId, pageNo));
//		return page;
//	}

//	@Override
//	public long getWordCountNumberById(Integer taskId) {
//		String sql="select count(distinct word) from taskdetails where taskid=?";
//		return getSingleValue(sql, taskId);
//	}

	//下面三个方法用来从taskdetails表中获取全部的wordcount
	@Override
	public List<WordCount> getAllWordCountList(int pageNo) {
		String sql="select word,count(word) as count from taskdetails group by word limit ?,?";
		return queryForList(sql,(pageNo-1)*10,10);
	}

	@Override
	public Page<WordCount> getAllWordPage(int pageNo) {
		Page<WordCount> page=new Page<WordCount>(pageNo);
		page.setTotalItemNumber(getAllWordNumber());
		page.setList(getAllWordCountList(pageNo));
		return page;
	}

	@Override
	public long getAllWordNumber() {
		String sql="select count(distinct word) from taskdetails";
		return getSingleValue(sql);
	}

	
}
