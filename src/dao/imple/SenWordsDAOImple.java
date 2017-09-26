package dao.imple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import po.SenWord;
import web.Page;
import dao.SenWordsDAO;

public class SenWordsDAOImple extends BaseDAO<SenWord> implements SenWordsDAO {

	@Override
	public List<SenWord> getWords() {
		String sql="select id,word,wordsymbol,wordlevel from senword order by wordsymbol desc";
		return queryForList(sql);
	}

	@Override
	public List<SenWord> getWordsByLevel(Integer level) {
		String sql="select id,word,wordsymbol,wordlevel from senword where level=?";
		return queryForList(sql, level);
	}

	@Override
	public void insert(SenWord senWord) {
		String sql="insert ignore into senword(word,wordsymbol,wordlevel)values(?,?,?)";
		update(sql, senWord.getWord(),senWord.getWordSymbol(),senWord.getWordLevel());
	}

	@Override
	public void batchSave(Collection<SenWord> senWords) {
		String sql="insert ignore into senword(word,wordsymbol,wordlevel)values(?,?,?)";
		Object[][] params=new Object[senWords.size()][3];
		//ת��ΪArrayList������Ϊparams��ֵ
		List<SenWord> wordlist=new ArrayList<>(senWords);
		for(int i=0;i<wordlist.size();i++){
			params[i][0]=wordlist.get(i).getWord();
			params[i][1]=wordlist.get(i).getWordSymbol();
			params[i][2]=wordlist.get(i).getWordLevel();
		}
		batch(sql, params);
	}

	@Override
	public void changeSymbol(Integer id,Integer symbol) {
		String sql="update senword set wordsymbol=? where id=?";
		update(sql,symbol,id);
	}

	@Override
	public void changeLevel(Integer id,Integer level) {
		String sql="update senword set wordlevel=? where id=?";
		update(sql, level,id);
	}

	@Override
	public List<SenWord> getWordsInUse() {
		String sql="select id,word,wordsymbol,wordlevel from senword where wordsymbol=1";
		return new ArrayList(queryForList(sql));
	}

	@Override
	public void delete(Integer id) {
		String sql="delete from senword where id=?";
		update(sql, id);
	}

	@Override
	//��ȡ��ǰҳ�����������ݺͷ�ҳ��Ϣ
	public Page<SenWord> getPage(int pageNo) {
		Page page=new Page<>(pageNo);
		//����getTotalNumber()������������������Page
		page.setTotalItemNumber(getTotalNumber());
		//��ȡ��ǰҳ��ŵĵ�list
		List<SenWord> wordlist=getPageList(pageNo);
		page.setList(wordlist);
		return page;
	}

	@Override
	public long getTotalNumber() {
		//��ȡ���д���������Ҫע�⣬��getSingleValue�У�count��������long���ͷ���
		String sql="select count(id) from senword"; 
		return getSingleValue(sql);
	}

	@Override
	//ÿҳ��ʾ10������
	public List<SenWord> getPageList(int pageNo) {
		String sql="select id,word,wordsymbol,wordlevel from senword limit ?,? ";
		return queryForList(sql, (pageNo-1)*10,10);
	}

	//��ȡ���õ����дʷ�ҳ
	@Override
	public Page<SenWord> getStartWordPage(int pageNo) {
		Page<SenWord> page=new Page<>(pageNo);
		page.setTotalItemNumber(getTotalStartWordNumber());
		page.setList(getStartWordPageList(pageNo));
		return page;
	}
	
	//��ȡ���õ����д�������
	@Override
	public long getTotalStartWordNumber() {
		String sql="select count(id) from senword where wordsymbol=1";
		return getSingleValue(sql);
	}
	
	//��ҳ��ȡ�����е����д�
	@Override
	public List<SenWord> getStartWordPageList(int pageNo) {
		String sql="select id,word,wordsymbol,wordlevel from senword where wordsymbol=1 limit ?,? ";
		return queryForList(sql, (pageNo-1)*10,10);
	}

	
	@Override
	public Page<SenWord> getStopWordPage(int pageNo) {
		Page<SenWord> page=new Page<>(pageNo);
		page.setList(getStopWordPageList(pageNo));
		page.setTotalItemNumber(getTotalStopWordNumber());
		return page;
	}

	@Override
	
	public long getTotalStopWordNumber() {
		String sql="select count(id) from senword where wordsymbol=0";
		return getSingleValue(sql);
	}

	@Override
	public List<SenWord> getStopWordPageList(int pageNo) {
		String sql="select id,word,wordsymbol,wordlevel from senword where wordsymbol=0 limit ?,?";
		return queryForList(sql, (pageNo-1)*10,10);
	}

}
