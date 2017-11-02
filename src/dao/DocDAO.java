package dao;

import java.util.Collection;
import java.util.List;

import po.Doc;
import web.Page;

public interface DocDAO {
	//获取Doc
	public abstract Doc getDoc(Integer id);
	//获取多个Doc
	public abstract List<Doc> getDocs();
	
	public abstract void insert(Doc doc);
	
	public abstract void saveBatch(Collection<Doc> docs);
	
	//不分页获取黑名单（不使用）
	public abstract List<Doc> getBlackList();
	
	public abstract Integer getId(String docpath);
	
	public abstract void changeSymbol(String path,Integer symbol);
	
	public abstract Integer getSymbol(String path);
	
	public abstract void addDescription(String description,String docpath);
	
	//分页获取通过审核的文档
	public abstract Page<Doc> getPassDocPage(int pageNo);
	//获取此页下通过审核文档的list
	public abstract List<Doc> getPassDocList(int pageNo);
	//获取通过审核的文档的数量
	public abstract long getPassDocNumber();
	
	//分页获取黑名单文档
	public abstract Page<Doc> getBlackListPage(int pageNo);
	//获取此页下黑名单的list
	public abstract List<Doc> getBlackList(int pageNo);
	//获取黑名单总数量
	public abstract long getBlackListNumber();
	
	//分页获取所有的文档
	public abstract Page<Doc> getAllDocPage(int pageNo);
	//获取当前页的docpagelist
	public abstract List<Doc> getAllDocList(int pageNo);
	//获取所有文档数量
	public abstract long getAllDocNumber();
	
	//分页获取未审核的文档
	public abstract Page<Doc> getDocWithoutCheckPage(int pageNo);
	//获取所有尚未审核的文档的List
	public abstract List<Doc> getDocWithoutCheckList(int pageNo);
	//计算尚未审核的文档的数量
	public abstract long getDocWithoutCheckNumber();

	//通过id来进行操作
	public abstract void changeSymbol(int id,Integer symbol);
	
	public abstract Integer getSymbol(int id);
	//从taskdetails表中指定word对应的doc
	public abstract List<Doc> getDocByWord(String word);
	
	public abstract void deleteDoc(String path);
	
	public abstract void deleteAll();
}
