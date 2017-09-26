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
	
	//��ѯ���ݿ������е����дʣ��޷�������ҳ
//	public List<SenWord> queryWords(){
//		List list=senWordDAO.getWords();
//		return list;
//	}
	
	//�����getWordPage������ҳ��ʾ
	public Page<SenWord> getWordPage(int pageNo){
		Page<SenWord> page=senWordDAO.getPage(pageNo);
		return page;
	}
	//getStopWordPage������ҳչʾͣ�õĴʻ�
	public Page<SenWord> getStopWordPage(int pageNo){
		Page<SenWord> page=senWordDAO.getStopWordPage(pageNo);
		return page;
	}
	
	//getStartWordPage���ڷ�ҳչʾ���õĴʻ�
	public Page<SenWord> getStartWordPage(int pageNo){
		Page<SenWord> page=senWordDAO.getStartWordPage(pageNo);
		return page;
	}
	//��ѯ�����ã����������е����д�,������ѯ���Ƿ�ҳչʾ��
	public List<SenWord> queryWordInUse(){
		List<SenWord> list=senWordDAO.getWordsInUse();
		return list;
	}
	
	//startSearch��������ʼ����solr��ѯ
	public List<Doc> startSearch(String word) throws SolrServerException{
		System.out.println("startsearch����");
		String queryWords="";
		//��word������Ϊ��ʱ����Ըò������в���
		if(word!=""){
			queryWords="pdftext:"+word;
		}else{
		//��word����Ϊ�գ����ѯ���ݿ����������дʣ����в���
		List<SenWord> words=queryWordInUse();
		System.out.println(words);
		for(int i=0;i<words.size()-1;i++){
			queryWords+="pdftext:"+words.get(i).getWord()+" OR ";
		  }
		queryWords+="pdftext:"+words.get(words.size()-1).getWord();
		}
		
		//�������
		SolrQuery query=new SolrQuery();
		query.set("q", queryWords);
		//���ø���
		query.setHighlight(true);
		//��product_name������Ϊ����
		query.addHighlightField("pdftext");
		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
		query.setHighlightSimplePost("</font>");
		
		QueryResponse rsp=server.query(query);
		//���SolrDocument�Ľ����
		SolrDocumentList sdList=rsp.getResults();
		//��ø����Ľ��
		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
		System.out.println("һ��������"+sdList.getNumFound()+"ƪ�ĵ�");
		
		List<Doc> docList=new ArrayList<>();
		for(int i=0;i<sdList.size();i++){
			SolrDocument solrDocument=sdList.get(i);
			String docpath=(String) solrDocument.get("id");
			String title = docpath.substring(docpath.lastIndexOf("\\")+1);
			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
			System.out.println(docpath);
			//��doc���в���description
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
