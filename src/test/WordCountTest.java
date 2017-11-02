package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.WordCountDAO;
import dao.imple.WordCountDAOImple;

public class WordCountTest {
	WordCountDAO wordCountDAO=new WordCountDAOImple();
	
	@Test
	public void testGetWordCountListById() {
		int taskId=18;
		System.out.println(wordCountDAO.getWordCountListById(taskId));
		System.out.println(wordCountDAO.getWordCountListById(19));
	}
	
//	@Test
//	public void testGetWordCountPageById(){
//		int taskId=19;
//		System.out.println(wordCountDAO.getWordCountPageById(taskId, 1).getList());
//		System.out.println(wordCountDAO.getWordCountPageById(taskId, 1).getTotalItemNumber());
//	} 

	@Test
	public void testGetAllWordCountPage(){
		int pageNo=1;
		System.out.println(wordCountDAO.getAllWordPage(pageNo).getList());
		System.out.println(wordCountDAO.getAllWordPage(pageNo).getTotalItemNumber());
	}
}
