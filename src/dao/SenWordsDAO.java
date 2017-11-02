package dao;

import java.util.Collection;
import java.util.List;

import po.SenWord;
import web.Page;

public interface SenWordsDAO {
	//getWords������ȡ���д�
	public abstract List<SenWord> getWords();
	//����level����ȡȫ�����д�
	public abstract List<SenWord> getWordsByLevel(Integer level);
	//���뵥��word
	public abstract void insert(SenWord senWord);
	//�����ݿ�����д��senWord
	public abstract void batchSave(Collection<SenWord> senWords);
	//ͣ��/�������д�
	public abstract void changeSymbol(Integer id,Integer symbol);
	//�������дʵĵȼ�
	public abstract void changeLevel(Integer id,Integer level);
	//��ȡ����ʹ�õ����д�
	public abstract List<SenWord> getWordsInUse();
	//ɾ�����д�
	public abstract void delete(Integer id);
	
	public abstract int getWordSymbol(Integer id);
	
	//�����Ƿ�ҳ��ط���
	//��ȡPage<SenWord>����ҳ������ʾ���е�List
	public abstract Page<SenWord> getPage(int pageNo);
	//��ȡ��ѯ���Ľ������,���������ܹ�Ҫ�ּ�ҳ
	public abstract long getTotalNumber();
	//��ȡ���SenWord��list������ŵ�page��
	public abstract List<SenWord> getPageList(int pageNo);
	
	//��ȡ���õ����д�
	public abstract Page<SenWord> getStartWordPage(int pageNo);
	public abstract long getTotalStartWordNumber();
	public abstract List<SenWord> getStartWordPageList(int pageNo);
	//��ȡͣ�õ����д�
	public abstract Page<SenWord> getStopWordPage(int pageNo);
	public abstract long getTotalStopWordNumber();
	public abstract List<SenWord> getStopWordPageList(int pageNo);
	//��������ж�ȡĳһ�����г��ֵ����д�
	public abstract List<SenWord> getWordsFromTaskDetails(int taskId);
	
//	public abstract List<SenWord> getWordUseLike(String word);
	
	public abstract SenWord getWord(String word);
	
	public abstract void deleteAll();
}
