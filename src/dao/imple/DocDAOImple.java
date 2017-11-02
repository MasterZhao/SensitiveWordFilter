package dao.imple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import po.Doc;
import web.Page;
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
//	//不分页的方法获取blacklist，不再使用
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
	//分页获取通过人工审核的文档
	public Page<Doc> getPassDocPage(int pageNo) {
		Page<Doc> page=new Page<Doc>(pageNo);
		page.setTotalItemNumber(getPassDocNumber());
		page.setList(getPassDocList(pageNo));
		return page;
	}
	@Override
	//获取通过人工审核的文档的数量
	public long getPassDocNumber() {
		String sql="select count(id) from doc where docsymbol=1 or docsymbol=2";
		return getSingleValue(sql);
	}
	@Override
	//分页获取通过人工审核的文档的list
	public List<Doc> getPassDocList(int pageNo) {
		String sql="select id,title,docpath,docSymbol,description from doc where docsymbol =1 or docsymbol=2 order by id limit ?,?";
		return queryForList(sql, (pageNo-1)*10,10);
	}

	//以下三个方法用来分页获取blacklist
	@Override
	public Page<Doc> getBlackListPage(int pageNo) {
		Page<Doc> page=new Page<>(pageNo);
		page.setTotalItemNumber(getBlackListNumber());
		page.setList(getBlackList(pageNo));
		return page;
	}
	@Override
	public List<Doc> getBlackList(int pageNo) {
		String sql="select id,title,docpath,description from doc where docsymbol=0 limit ?,?";
		return queryForList(sql, (pageNo-1)*10,10);
	}
	@Override
	public long getBlackListNumber() {
		String sql="select count(id) from doc where docsymbol=0";
		return getSingleValue(sql);
	}
	
	//以下三个方法用来分页获取全部资源
	@Override
	public Page<Doc> getAllDocPage(int pageNo) {
		Page<Doc> page=new Page<Doc>(pageNo);
		page.setTotalItemNumber(getAllDocNumber());
		page.setList(getAllDocList(pageNo));
		return page;
	}
	@Override
	public List<Doc> getAllDocList(int pageNo) {
		String sql="select id,title,docpath,docsymbol,description from doc limit ?,?";
		return queryForList(sql, (pageNo-1)*10,10);
	}
	@Override
	public long getAllDocNumber() {
		String sql="select count(id) from doc";
		return getSingleValue(sql);
	}

	@Override
	//分页获取被检索到但是未进行人工处理的文档
	public List<Doc> getDocWithoutCheckList(int pageNo) {
		String sql="select id,title,docpath,docsymbol,description from doc where docsymbol=-1 limit ?,?";
		return queryForList(sql,(pageNo-1)*10,10);
	}
	@Override
	public Page<Doc> getDocWithoutCheckPage(int pageNo) {
		Page<Doc> page=new Page<Doc>(pageNo);
		page.setTotalItemNumber(getDocWithoutCheckNumber());
		page.setList(getDocWithoutCheckList(pageNo));
		return page;
	}
	@Override
	public long getDocWithoutCheckNumber() {
		String sql="select count(id) from doc where docsymbol=-1";
		return getSingleValue(sql);
	}

	@Override
	//不使用
	public List<Doc> getDocByWord(String word) {
		String sql="select id,title,docpath,docsymbol from doc where docpath in"+
				"(select distinct docpath from taskdetails where word=? group by docpath) ";
		return queryForList(sql, word);
	}
	@Override
	public void changeSymbol(int id, Integer symbol) {	
	}
	@Override
	public Integer getSymbol(int id) {	
		return null;
	}

	@Override
	public void deleteDoc(String path) {
		String sql="delete from doc where docpath=?";
		update(sql, path);
	}

	@Override
	public void deleteAll() {
		String sql="delete from doc";
		update(sql);
	}
	
}
