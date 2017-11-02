package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import po.Doc;
import po.LevelCount;
import po.SenWord;
import po.Task;
import utils.SolrConnectUtil;
import web.Page;
import dao.DocDAO;
import dao.SenWordsDAO;
import dao.TaskDAO;
import dao.TaskDetailDAO;
import dao.imple.DocDAOImple;
import dao.imple.SenWordsDAOImple;
import dao.imple.TaskDetailDAOImple;

public class QueryService {
	
	
	//HttpSolrServer server=new HttpSolrServer("http://localhost:8080/solr/collection2");
	HttpSolrServer server=new HttpSolrServer(SolrConnectUtil.getSolrCore());
	SenWordsDAO senWordDAO=new SenWordsDAOImple();
	DocDAO docDAO=new DocDAOImple();
	TaskDetailDAO taskDetailDAO=new TaskDetailDAOImple();
	
	//查询数据库中所有的敏感词，无法用作分页
//	public List<SenWord> queryWords(){
//		List list=senWordDAO.getWords();
//		return list;
//	}
	
	//这里的getWordPage用作分页显示
	public Page<SenWord> getWordPage(int pageNo){
		Page<SenWord> page=senWordDAO.getPage(pageNo);
		return page;
	}
	//getStopWordPage用作分页展示停用的词汇
	public Page<SenWord> getStopWordPage(int pageNo){
		Page<SenWord> page=senWordDAO.getStopWordPage(pageNo);
		return page;
	}
	
	//getStartWordPage用于分页展示启用的词汇
	public Page<SenWord> getStartWordPage(int pageNo){
		Page<SenWord> page=senWordDAO.getStartWordPage(pageNo);
		return page;
	}
	//查询（调用）所有启用中的敏感词,用作查询（非分页展示）
	public List<SenWord> queryWordInUse(){
		List<SenWord> list=senWordDAO.getWordsInUse();
		return list;
	}
	//startSearch方法，开始进行solr查询（一次性搜索所有的敏感词）
	
	public Page<Doc> startSearch(String word,int pageNo) throws SolrServerException{
		System.out.println("startsearch方法");
		String queryWord="pdftext:"+word;
		SolrQuery query=new SolrQuery();
		query.set("q", queryWord);
		//设置分页显示
		query.setStart((pageNo-1)*10);
		query.setRows(10);
		query.setHighlight(true);
		query.addHighlightField("pdftext");
		//由于是对单个敏感词进行查询，所以可以把分片分得尽量小一些
		query.setHighlightFragsize(50);
		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
		query.setHighlightSimplePost("</font>");
		
		QueryResponse rsp=server.query(query);
//		获得SolrDocument的结果集
		SolrDocumentList sdList=rsp.getResults();
		//获得高亮的结果
		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
		System.out.println("一共检索到"+sdList.getNumFound()+"篇文档");
		List<Doc> docList=new ArrayList<Doc>();
		for(int i=0;i<sdList.size();i++){
			SolrDocument solrDocument=sdList.get(i);
			//从返回的solrDocument中获取id，即docpath
			String docPath=(String) solrDocument.get("id");
			String title = docPath.substring(docPath.lastIndexOf("\\")+1);
			//获取到文档对应的高亮区域list
			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
			System.out.println(docPath);
//			//description即为高亮区域
			String description=list.get(0);
			System.out.println(description);
			Doc doc=new Doc();
			doc.setTitle(title);
			doc.setDocPath(docPath);
			doc.setDescription(description);
			doc.setDocSymbol(docDAO.getSymbol(docPath));
			docList.add(doc);
		}
		Page<Doc> page=new Page(pageNo);
		page.setList(docList);
		page.setTotalItemNumber(sdList.getNumFound());
		return page;
	}
	
	//老方法，暂停使用
//	public List<Doc> startSearch(String word,int pageNo) throws SolrServerException{
//		System.out.println("startsearch方法");
//		String queryWords="";
//		if(word!=""){
//			queryWords="pdftext:"+word;
//		}else{
//		//若word参数为空，则查询数据库获得所有敏感词，进行查找
//		List<SenWord> words=queryWordInUse();
//		System.out.println(words);
//		for(int i=0;i<words.size()-1;i++){
//			queryWords+="pdftext:"+words.get(i).getWord()+" OR ";
//		  }
//		queryWords+="pdftext:"+words.get(words.size()-1).getWord();
//		}
//		//搜索语句
//		SolrQuery query=new SolrQuery();
//		query.set("q", queryWords);
//		//设置高亮
//		query.setRows(Integer.MAX_VALUE);
//		query.setHighlight(true);
//		//将product_name域设置为高亮
//		query.addHighlightField("pdftext");
//		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
//		query.setHighlightSimplePost("</font>");
//		
//		QueryResponse rsp=server.query(query);
//		//获得SolrDocument的结果集
//		SolrDocumentList sdList=rsp.getResults();
//		//获得高亮的结果
//		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
//		System.out.println("一共检索到"+sdList.getNumFound()+"篇文档");
//		for(int i=0;i<sdList.size();i++){
//			SolrDocument solrDocument=sdList.get(i);
//			//从返回的solrDocument中获取id，即docpath
//			String docpath=(String) solrDocument.get("id");
//			String title = docpath.substring(docpath.lastIndexOf("\\")+1);
//			//获取到文档对应的高亮区域list
//			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
//			System.out.println(docpath);
//			//description即为高亮区域
//			String description=list.get(0);
//			System.out.println(description);
//			//先查看这个文档的标识。如果标识为1，即不是人工审核通过的（2）或者黑名单中的（0），就将其标识改为-1
//			int symbol=docDAO.getSymbol(docpath);
//			if(symbol==1){
//				//将搜索到的文档改为待审核状态，并且将高亮区域值写入description
//				writeDocWithoutCheckToDB(docpath, description);
//			}
//		}
//			List<Doc> docList=null;
//			docList=docDAO.getDocWithoutCheckList(1);
//			return docList;
//	}
	
	//分页获取待审核资源
	public Page<Doc> getDocWithoutCheckPage(int pageNo){
		Page<Doc> page=docDAO.getDocWithoutCheckPage(pageNo);
		return page;
	}

	//不分页获取黑名单，不再使用
	public List<Doc> getBlackList() {
		List<Doc> list=docDAO.getBlackList();
		return list;
	}
	
	//分页获取黑名单
	public Page<Doc> getBlackListPage(int pageNo){
		Page<Doc> page=docDAO.getBlackListPage(pageNo);
//		if(page.getList().isEmpty()&&page.getPageNo()>1){
//			DocDAO docDAO=new DocDAOImple();
//			page=docDAO.getBlackListPage(pageNo-1);
//		}
		return page;
	}
	//返回全部的资源
	public Page<Doc> getAllDoc(int pageNo) {
		Page<Doc> page=docDAO.getAllDocPage(pageNo);
		return page;
	}
	public SenWord getOneWord(String word) {
		SenWord senword=senWordDAO.getWord(word);
		return senword;
	}
	public Page<Doc> getPassDocPage(int pageNo) {
		Page<Doc> page=new Page<Doc>(pageNo);
		page=docDAO.getPassDocPage(pageNo);
		return page;
	}
	public long getAllTaskNum() { 
		return 0;
	}
	public long getAllDocNum() {
		
		return docDAO.getAllDocNumber();
	}
	public long getBlackListNum() {
	
		return docDAO.getBlackListNumber();
	}
	public long getAllWordNum() {
		return senWordDAO.getTotalNumber();
	}
	public long getWordDiscoveredNum() {
		return taskDetailDAO.getDiscoveredWordNum();
	}
	public LevelCount getAllLevelCount() {
		
		return null;
	}
	
	//将文件标记为未审核，并将高亮区域放入description字段
//	public void writeDocWithoutCheckToDB(String docpath,String description){
//		docDAO.changeSymbol(docpath, -1);
//		docDAO.addDescription(description, docpath);
//	}
}
