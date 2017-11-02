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
	//startSearch��������ʼ����solr��ѯ��һ�����������е����дʣ�
	
	public Page<Doc> startSearch(String word,int pageNo) throws SolrServerException{
		System.out.println("startsearch����");
		String queryWord="pdftext:"+word;
		SolrQuery query=new SolrQuery();
		query.set("q", queryWord);
		//���÷�ҳ��ʾ
		query.setStart((pageNo-1)*10);
		query.setRows(10);
		query.setHighlight(true);
		query.addHighlightField("pdftext");
		//�����ǶԵ������дʽ��в�ѯ�����Կ��԰ѷ�Ƭ�ֵþ���СһЩ
		query.setHighlightFragsize(50);
		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
		query.setHighlightSimplePost("</font>");
		
		QueryResponse rsp=server.query(query);
//		���SolrDocument�Ľ����
		SolrDocumentList sdList=rsp.getResults();
		//��ø����Ľ��
		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
		System.out.println("һ��������"+sdList.getNumFound()+"ƪ�ĵ�");
		List<Doc> docList=new ArrayList<Doc>();
		for(int i=0;i<sdList.size();i++){
			SolrDocument solrDocument=sdList.get(i);
			//�ӷ��ص�solrDocument�л�ȡid����docpath
			String docPath=(String) solrDocument.get("id");
			String title = docPath.substring(docPath.lastIndexOf("\\")+1);
			//��ȡ���ĵ���Ӧ�ĸ�������list
			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
			System.out.println(docPath);
//			//description��Ϊ��������
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
	
	//�Ϸ�������ͣʹ��
//	public List<Doc> startSearch(String word,int pageNo) throws SolrServerException{
//		System.out.println("startsearch����");
//		String queryWords="";
//		if(word!=""){
//			queryWords="pdftext:"+word;
//		}else{
//		//��word����Ϊ�գ����ѯ���ݿ����������дʣ����в���
//		List<SenWord> words=queryWordInUse();
//		System.out.println(words);
//		for(int i=0;i<words.size()-1;i++){
//			queryWords+="pdftext:"+words.get(i).getWord()+" OR ";
//		  }
//		queryWords+="pdftext:"+words.get(words.size()-1).getWord();
//		}
//		//�������
//		SolrQuery query=new SolrQuery();
//		query.set("q", queryWords);
//		//���ø���
//		query.setRows(Integer.MAX_VALUE);
//		query.setHighlight(true);
//		//��product_name������Ϊ����
//		query.addHighlightField("pdftext");
//		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
//		query.setHighlightSimplePost("</font>");
//		
//		QueryResponse rsp=server.query(query);
//		//���SolrDocument�Ľ����
//		SolrDocumentList sdList=rsp.getResults();
//		//��ø����Ľ��
//		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
//		System.out.println("һ��������"+sdList.getNumFound()+"ƪ�ĵ�");
//		for(int i=0;i<sdList.size();i++){
//			SolrDocument solrDocument=sdList.get(i);
//			//�ӷ��ص�solrDocument�л�ȡid����docpath
//			String docpath=(String) solrDocument.get("id");
//			String title = docpath.substring(docpath.lastIndexOf("\\")+1);
//			//��ȡ���ĵ���Ӧ�ĸ�������list
//			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
//			System.out.println(docpath);
//			//description��Ϊ��������
//			String description=list.get(0);
//			System.out.println(description);
//			//�Ȳ鿴����ĵ��ı�ʶ�������ʶΪ1���������˹����ͨ���ģ�2�����ߺ������еģ�0�����ͽ����ʶ��Ϊ-1
//			int symbol=docDAO.getSymbol(docpath);
//			if(symbol==1){
//				//�����������ĵ���Ϊ�����״̬�����ҽ���������ֵд��description
//				writeDocWithoutCheckToDB(docpath, description);
//			}
//		}
//			List<Doc> docList=null;
//			docList=docDAO.getDocWithoutCheckList(1);
//			return docList;
//	}
	
	//��ҳ��ȡ�������Դ
	public Page<Doc> getDocWithoutCheckPage(int pageNo){
		Page<Doc> page=docDAO.getDocWithoutCheckPage(pageNo);
		return page;
	}

	//����ҳ��ȡ������������ʹ��
	public List<Doc> getBlackList() {
		List<Doc> list=docDAO.getBlackList();
		return list;
	}
	
	//��ҳ��ȡ������
	public Page<Doc> getBlackListPage(int pageNo){
		Page<Doc> page=docDAO.getBlackListPage(pageNo);
//		if(page.getList().isEmpty()&&page.getPageNo()>1){
//			DocDAO docDAO=new DocDAOImple();
//			page=docDAO.getBlackListPage(pageNo-1);
//		}
		return page;
	}
	//����ȫ������Դ
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
	
	//���ļ����Ϊδ��ˣ����������������description�ֶ�
//	public void writeDocWithoutCheckToDB(String docpath,String description){
//		docDAO.changeSymbol(docpath, -1);
//		docDAO.addDescription(description, docpath);
//	}
}
