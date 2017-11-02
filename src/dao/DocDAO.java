package dao;

import java.util.Collection;
import java.util.List;

import po.Doc;
import web.Page;

public interface DocDAO {
	//��ȡDoc
	public abstract Doc getDoc(Integer id);
	//��ȡ���Doc
	public abstract List<Doc> getDocs();
	
	public abstract void insert(Doc doc);
	
	public abstract void saveBatch(Collection<Doc> docs);
	
	//����ҳ��ȡ����������ʹ�ã�
	public abstract List<Doc> getBlackList();
	
	public abstract Integer getId(String docpath);
	
	public abstract void changeSymbol(String path,Integer symbol);
	
	public abstract Integer getSymbol(String path);
	
	public abstract void addDescription(String description,String docpath);
	
	//��ҳ��ȡͨ����˵��ĵ�
	public abstract Page<Doc> getPassDocPage(int pageNo);
	//��ȡ��ҳ��ͨ������ĵ���list
	public abstract List<Doc> getPassDocList(int pageNo);
	//��ȡͨ����˵��ĵ�������
	public abstract long getPassDocNumber();
	
	//��ҳ��ȡ�������ĵ�
	public abstract Page<Doc> getBlackListPage(int pageNo);
	//��ȡ��ҳ�º�������list
	public abstract List<Doc> getBlackList(int pageNo);
	//��ȡ������������
	public abstract long getBlackListNumber();
	
	//��ҳ��ȡ���е��ĵ�
	public abstract Page<Doc> getAllDocPage(int pageNo);
	//��ȡ��ǰҳ��docpagelist
	public abstract List<Doc> getAllDocList(int pageNo);
	//��ȡ�����ĵ�����
	public abstract long getAllDocNumber();
	
	//��ҳ��ȡδ��˵��ĵ�
	public abstract Page<Doc> getDocWithoutCheckPage(int pageNo);
	//��ȡ������δ��˵��ĵ���List
	public abstract List<Doc> getDocWithoutCheckList(int pageNo);
	//������δ��˵��ĵ�������
	public abstract long getDocWithoutCheckNumber();

	//ͨ��id�����в���
	public abstract void changeSymbol(int id,Integer symbol);
	
	public abstract Integer getSymbol(int id);
	//��taskdetails����ָ��word��Ӧ��doc
	public abstract List<Doc> getDocByWord(String word);
	
	public abstract void deleteDoc(String path);
	
	public abstract void deleteAll();
}
