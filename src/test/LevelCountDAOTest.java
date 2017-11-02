package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.LevelCountDAO;
import dao.imple.LevelCountDAOImple;

public class LevelCountDAOTest {

	LevelCountDAO levelCountDAO=new LevelCountDAOImple();
	
	@Test
	public void testGetLevelCountById() {
		System.out.println(levelCountDAO.getLevelCountById(18));
	}

	@Test
	public void testGetAllLevelCount() {
		System.out.println(levelCountDAO.getAllLevelCount());
	}

}
