package dao.imple;

import java.util.List;

import dao.WordCountDAO;
import po.WordCount;
import web.Page;

public class WordCountDAOImple extends BaseDAO<WordCount> implements WordCountDAO {
	//����������������ͨ��taskId��ȡwordcount
	@Override
	//��taskdetail����ѡȡword��count����װΪWordCount����
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

	//������������������taskdetails���л�ȡȫ����wordcount
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
