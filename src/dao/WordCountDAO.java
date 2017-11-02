package dao;

import java.util.List;

import po.WordCount;
import web.Page;

public interface WordCountDAO {

	//����taskId����taskdetails����wordcount�����List
	public abstract List<WordCount> getWordCountListById(Integer taskId);
	//����taskId��ҳ��ȡWordCount
	//public abstract Page<WordCount> getWordCountPageById(Integer taskId);
	//����taskId��ȡWordCount����
	//public abstract long getWordCountNumberById(Integer taskId);
	
	//��ȡȫ��wordcount��list
	public abstract List<WordCount> getAllWordCountList(int pageNo);
	//��ҳ��ȡwordcount
	public abstract Page<WordCount> getAllWordPage(int pageNo);
	//ͳ��ȫ����wordcount����
	public abstract long getAllWordNumber();
	
}

