package service;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.DocDAO;
import dao.imple.DocDAOImple;
import po.Doc;

public class DocService {

	DocDAO docDAO=new DocDAOImple();
	
	public void insertBlackList(String docpath) {
		docDAO.changeSymbol(docpath,0);
	}

	public List<Doc> addSymbol(Collection<Doc> docs){
		List<Doc> doclist=new ArrayList<>(docs);
		for(Doc doc:doclist){
			Integer symbol=docDAO.getSymbol(doc.getDocPath());
			doc.setDocSymbol(symbol);
		}
		return doclist;
	}

	//通过审核或从黑名单中还原
	public void pass(String path) {
		//将symbol改为2，意思是这是通过了人工审核的文档
		docDAO.changeSymbol(path, 2);
	}

	public void deleteDoc(String path) {
		docDAO.deleteDoc(path);
		
	}
	
	
	//添加备注信息，不再使用此方法，在indexService中完成操作
//	public void addDescription(String description,String docpath) {
//		docDAO.addDescription(description, docpath);
//	}	
}
