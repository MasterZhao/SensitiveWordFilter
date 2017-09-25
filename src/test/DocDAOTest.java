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
		doc.setTitle("工厂主义的失败");
		doc.setDocPath("D:\\我的文档\\共产主义的失败");
		doc.setDocSymbol(0);
		doc.setDescription("共产党");
		docDAO.insert(doc);
	}

	@Test
	public void testSaveBatch() {
		Collection<Doc> docItems=new ArrayList<>();
		docItems.add(new Doc(null,"我的青春", "D:我的文档\\我的青春",1, "友情，学业"));
		docItems.add(new Doc(null,"倔强的小红军", "D:我的文档\\倔强的小红军",1, "抗战"));
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
		System.out.println(docDAO.getId("示例.docx"));
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
		docDAO.addDescription("一篇示例文档", docpath);
	}
}
