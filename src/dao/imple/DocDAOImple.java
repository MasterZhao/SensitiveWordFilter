package dao.imple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import po.Doc;
import dao.DocDAO;

public class DocDAOImple extends BaseDAO<Doc> implements DocDAO {

	@Override
	public Doc getDoc(Integer id) {
		String sql="select id,title,docpath,docsymbol,description from doc where id=?";
		return query(sql, id);
	}

	@Override
	public List<Doc> getDocs() {
		String sql="select id,title,docpath,docsymbol,description from doc";
		return queryForList(sql);
	}
	
	@Override
	public void insert(Doc doc) {
		String sql="insert ignore into doc(title,docpath,docsymbol,description) values(?,?,?,?)";
		update(sql,doc.getTitle(),doc.getDocPath(),doc.getDocSymbol(),doc.getDescription());
	}

	@Override
	public void saveBatch(Collection<Doc> docs) {
		String sql="insert ignore into doc(title,docpath,docsymbol,description) values(?,?,?,?)";
		
		Object[][] params=new Object[docs.size()][4];
		
		List<Doc> docItems=new ArrayList<>(docs);
		
		for(int i=0;i<docItems.size();i++){
			params[i][0]=docItems.get(i).getTitle();
			params[i][1]=docItems.get(i).getDocPath();
			params[i][2]=docItems.get(i).getDocSymbol();
			params[i][3]=docItems.get(i).getDescription();
		}
		batch(sql, params);
	}

	@Override
	//不分页的方法获取blacklist
	public List<Doc> getBlackList() {
		String sql="select id,title,docpath,description from doc where docsymbol=0";
		return queryForList(sql);
	}

	@Override
	public Integer getId(String docpath) {
		String sql="select id from doc where docpath= ?";
		return getSingleValue(sql, docpath);
	}
	@Override
	public void changeSymbol(String path, Integer symbol) {
		String sql="update doc set docsymbol=? where docpath=?";
		update(sql,symbol,path);
	}

	@Override
	public Integer getSymbol(String path) {
		String sql="select docsymbol from doc where docpath=?";
		return getSingleValue(sql, path);		
	}

	@Override
	public void addDescription(String description,String docpath) {
		String sql="update doc set description=? where docpath=?";
		update(sql,description,docpath);
	}

	@Override
	public List<Doc> getPassDoc() {
		String sql="select id,title,docpath,description from doc where docsymbol=1";
		return queryForList(sql);
	}

}
