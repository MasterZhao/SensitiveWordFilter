package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import po.Doc;
import dao.DocDAO;
import dao.imple.DocDAOImple;

public class DocDAOTest {

	private DocDAO docDAO=new DocDAOImple();
	
	@Test
	public void testGetDoc() {
		Doc doc=docDAO.getDoc(131);
		System.out.println(doc);
		System.out.println(doc.getDescription());
	}

	@Test
	public void testGetDocs() {
		List<Doc> list=docDAO.getDocs();
		for(Doc doc:list){
			System.out.println(doc);
		}
	}

	@Test
	public void testInsertDoc() {
		Doc doc=new Doc();
		doc.setTitle("���������ʧ��");
		doc.setDocPath("D:\\�ҵ��ĵ�\\���������ʧ��");
		doc.setDocSymbol(0);
		doc.setDescription("������");
		docDAO.insert(doc);
	}

	@Test
	public void testSaveBatch() {
		Collection<Doc> docItems=new ArrayList<>();
		docItems.add(new Doc(null,"�ҵ��ഺ", "D:�ҵ��ĵ�\\�ҵ��ഺ",1, "���飬ѧҵ"));
		docItems.add(new Doc(null,"��ǿ��С���", "D:�ҵ��ĵ�\\��ǿ��С���",1, "��ս"));
		docDAO.saveBatch(docItems);
	}
	
	@Test
	public void testGetBlackList(){
		List<Doc> docs=docDAO.getBlackList();
		for(Doc doc:docs){
			System.out.println(doc);
		}
	}	
	
	@Test
	public void testGetId(){
		System.out.println(docDAO.getId("ʾ��.docx"));
	}
	
	@Test
	public void testChangeSymbol(){
		Doc doc=docDAO.getDoc(131);
		docDAO.changeSymbol(doc.getDocPath(),1);
	}
	
	@Test
	public void testGetSymbol(){
		Doc doc=docDAO.getDoc(131);
		docDAO.getSymbol(doc.getDocPath());
		System.out.println(docDAO.getSymbol(doc.getDocPath()));
	}
	@Test
	public void testAddDescription(){
		String docpath=docDAO.getDoc(131).getDocPath();
		docDAO.addDescription("һƪʾ���ĵ�", docpath);
	}
}