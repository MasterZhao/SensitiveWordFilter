package service;

import po.SenWord;
import dao.SenWordsDAO;
import dao.imple.SenWordsDAOImple;

public class WordService {

	private SenWordsDAO senWordsDAO=new SenWordsDAOImple();
	
	public void insertWord(SenWord senWord){
		senWordsDAO.insert(senWord);
	}
	public void changeSymbol(Integer id,Integer symbol){
		senWordsDAO.changeSymbol(id, symbol);
	}
	public void deleteWord(int id){
		senWordsDAO.delete(id);
	}
	public void changeLevel(int id,int level){
		senWordsDAO.changeLevel(id, level);
	}
	
	
}
