package dao;

import java.util.Collection;
import java.util.List;

import po.Doc;

public interface DocDAO {
	//获取Doc
	public abstract Doc getDoc(Integer id);
	//获取多个Doc
	public abstract List<Doc> getDocs();
	
	public abstract void insert(Doc doc);
	
	public abstract void saveBatch(Collection<Doc> docs);
	
	public abstract List<Doc> getBlackList();
	
	public abstract Integer getId(String docpath);
	
	public abstract void changeSymbol(String path,Integer symbol);
	
	public abstract Integer getSymbol(String path);
	
	public abstract void addDescription(String description,String docpath);
}
