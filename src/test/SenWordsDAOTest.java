package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import po.SenWord;
import dao.SenWordsDAO;
import dao.imple.SenWordsDAOImple;

public class SenWordsDAOTest {

	SenWordsDAO senWordsDAO=new SenWordsDAOImple();
	@Test
	public void testGetWords() {
		List<SenWord> senWordList= senWordsDAO.getWords();
		for(SenWord senWord:senWordList){
			System.out.println(senWord);
		}
	}

	@Test
	public void testGetWordsByLevel() {
		Integer level=1;
		senWordsDAO.getWordsByLevel(level);
	}

	@Test
	public void testInsertSenWord() {
		SenWord senWord=new SenWord();
		senWord.setWord("变态");
		senWord.setWordSymbol(1);
		senWord.setWordLevel(1);
		senWordsDAO.insert(senWord);
	}

	@Test
	public void testBatchSave() {
		
		Collection<SenWord> words=new ArrayList<>();
		SenWord senWord1=new SenWord();
		senWord1.setWord("堕胎");
		senWord1.setWordSymbol(1);
		senWord1.setWordLevel(1);
		
		SenWord senWord2=new SenWord();
		senWord2.setWord("黄片");
		senWord2.setWordSymbol(1);
		senWord2.setWordLevel(1);
		
		words.add(senWord1);
		words.add(senWord2);
		senWordsDAO.batchSave(words);
		
	}

	@Test
	public void testChangeSymbol(){
		int symbol=0;
		int id=2;
		senWordsDAO.changeSymbol(id, symbol);
	}
	
	@Test
	public void testChangeLevel(){
		int level=1;
		int id=19;
		senWordsDAO.changeLevel(id, level);
	}
	
	@Test
	public void delete(){
		senWordsDAO.delete(30);
	}
	
	//测试获得总词汇数
	@Test
	public void testGetTotalNumber(){
		System.out.println(senWordsDAO.getTotalNumber());
		System.out.println("===============");
	}
	
	@Test
	public void testGetPageList(){
		
		System.out.println(senWordsDAO.getPageList(1));
	}
	
	@Test
	//分页获取全部的敏感词
	public void getPage(){	
		System.out.println(senWordsDAO.getPage(1).getPageNo());
		System.out.println(senWordsDAO.getPage(1).getList());
	}
	
	//分页获取全部启用的敏感词
	@Test
	public void testGetStopWordPage(){
		System.out.println(senWordsDAO.getStartWordPage(1).getPageNo());
		System.out.println(senWordsDAO.getStartWordPage(1).getList());
		
	}
	
	//分页获取全部停用的敏感词
	@Test
	public void testGetStartWordPage(){
		System.out.println(senWordsDAO.getStopWordPage(1).getPageNo());
		System.out.println(senWordsDAO.getStopWordPage(1).getList());
	}
}
