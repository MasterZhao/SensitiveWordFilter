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
import po.SenWord;
import web.Page;
import dao.DocDAO;
import dao.SenWordsDAO;
import dao.imple.DocDAOImple;
import dao.imple.SenWordsDAOImple;

public class QueryService {
	
	
	HttpSolrServer server=new HttpSolrServer("http://localhost:8080/solr/collection2");
	
	SenWordsDAO senWordDAO=new SenWordsDAOImple();
	DocDAO docDAO=new DocDAOImple();
	
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
	
	//startSearch方法，开始进行solr查询
	public List<Doc> startSearch(String word) throws SolrServerException{
		System.out.println("startsearch方法");
		String queryWords="";
		//当word参数不为空时，则对该参数进行查找
		if(word!=""){
			queryWords="pdftext:"+word;
		}else{
		//若word参数为空，则查询数据库获得所有敏感词，进行查找
		List<SenWord> words=queryWordInUse();
		System.out.println(words);
		for(int i=0;i<words.size()-1;i++){
			queryWords+="pdftext:"+words.get(i).getWord()+" OR ";
		  }
		queryWords+="pdftext:"+words.get(words.size()-1).getWord();
		}
		
		//搜索语句
		SolrQuery query=new SolrQuery();
		query.set("q", queryWords);
		//设置高亮
		query.setHighlight(true);
		//将product_name域设置为高亮
		query.addHighlightField("pdftext");
		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
		query.setHighlightSimplePost("</font>");
		
		QueryResponse rsp=server.query(query);
		//获得SolrDocument的结果集
		SolrDocumentList sdList=rsp.getResults();
		//获得高亮的结果
		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
		System.out.println("一共检索到"+sdList.getNumFound()+"篇文档");
		
		List<Doc> docList=new ArrayList<>();
		for(int i=0;i<sdList.size();i++){
			SolrDocument solrDocument=sdList.get(i);
			String docpath=(String) solrDocument.get("id");
			String title = docpath.substring(docpath.lastIndexOf("\\")+1);
			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
			System.out.println(docpath);
			//从doc表中查找description
			String description=null;
			Integer id=docDAO.getId(docpath);
			System.out.println("id="+id);
			description=docDAO.getDoc(id).getDescription();
			System.out.println("description="+description);
			String content=list.get(0);
			System.out.println(content);
			docList.add(new Doc(null,title,docpath,1,description));
			docList.get(i).setContent(content);
			System.out.println("the end");
		}
		return docList;
	}
	
	public List<Doc> getBlackList() {
		List<Doc> list=docDAO.getBlackList();
		return list;
	}
}
