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

	//ͨ����˻�Ӻ������л�ԭ
	public void pass(String path) {
		//��symbol��Ϊ2����˼������ͨ�����˹���˵��ĵ�
		docDAO.changeSymbol(path, 2);
	}

	public void deleteDoc(String path) {
		docDAO.deleteDoc(path);
		
	}
	
	
	//��ӱ�ע��Ϣ������ʹ�ô˷�������indexService����ɲ���
//	public void addDescription(String description,String docpath) {
//		docDAO.addDescription(description, docpath);
//	}	
}
