package service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import po.Doc;
import po.SenWord;
import po.Task;
import po.TaskDetail;
import dao.DocDAO;
import dao.SenWordsDAO;
import dao.TaskDAO;
import dao.TaskDetailDAO;
import dao.imple.DocDAOImple;
import dao.imple.SenWordsDAOImple;
import dao.imple.TaskDAOImple;
import dao.imple.TaskDetailDAOImple;
import utils.FileUtils;
import utils.SolrConnectUtil;

public class IndexService {
	
	DocDAO docDAO=new DocDAOImple();
	SenWordsDAO senWordDAO=new SenWordsDAOImple();
	TaskDAO taskDAO=new TaskDAOImple();
	TaskDetailDAO taskDetailDAO=new TaskDetailDAOImple();
	
	//HttpSolrServer server=new HttpSolrServer("http://localhost:8080/solr/collection2");
	HttpSolrServer server=new HttpSolrServer(SolrConnectUtil.getSolrCore());
	
	//��������
	public long createIndex(String path) throws Exception{
		
		ContentStreamUpdateRequest up=new ContentStreamUpdateRequest("/update/extract");
		
		String filepath=path;
		
		List<String> office=new ArrayList<>();
		office.add("doc");
		office.add("docx");
		office.add("xls");
		office.add("xlsx");
		office.add("ppt");
		office.add("pptx");
		
		FileUtils fileUtils=new FileUtils();
		List list=fileUtils.readFiles(filepath);
		
		for(int i=0;i<list.size();i++){
			File file=(File) list.get(i);
			String title=file.getName();
			String docpath=file.getAbsolutePath();
			
			String suffix=title.substring(title.lastIndexOf(".")+1);
			String contype=null;
			if(suffix.equals("pdf")){
				contype="pdf";
			}
			if(office.contains(suffix)){
				contype="word";
			}
			up.addFile(file, "application"+contype);
			//�����ĵ���Ψһ��ʶ������·����
			up.setParam("literal.id", docpath);
			up.setParam("fmap.content", "pdftext");
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
			server.request(up);
		}
		server.commit();
		System.out.println("������"+list.size()+"ƪ�ĵ�");
		long size=list.size();
		return size;
	}
	
	//�ڿ�ʼ֮ǰ��ɾ��֮ǰ���е�����
	public boolean deleteAllIndex(){
		try {
			server.deleteByQuery("*:*");
			server.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	//�ڴ���������ͬʱ�������д����������ļ�д�����ݿ�
	public void writeDocIntoDataBase() throws SolrServerException{
		SolrQuery query=new SolrQuery();
		query.set("q","*:*");
		query.setRows(Integer.MAX_VALUE);
		QueryResponse rsp=server.query(query);
		SolrDocumentList docList=rsp.getResults();
		System.out.println(docList.size());
		List<Doc> list=new ArrayList<>();
		for(int i=0;i<docList.size();i++){
			SolrDocument document=docList.get(i);
			String docpath=(String)document.get("id");
			System.out.println(docpath);
			String title = docpath.substring(docpath.lastIndexOf("\\")+1);
			list.add(new Doc(null,title,docpath,1,null));
		}
		docDAO.saveBatch(list);
	}
	
	//����ȫ�����дʣ�������ƥ�䵽���ĵ����Ϊ�����״̬(���˱����Ѿ������Ϊ�����������ͨ������Դ)
	public void signDocWithoutCheck() throws SolrServerException{
		List<SenWord> words=queryWordInUse();
		if(words==null){
			return;
		}
		String queryWords="";
		for(int i=0;i<words.size()-1;i++){
			queryWords+="pdftext:"+words.get(i).getWord()+" OR ";
		}
		queryWords+="pdftext:"+words.get(words.size()-1).getWord();
		
		SolrQuery query=new SolrQuery();
		query.set("q", queryWords);
		query.setRows(Integer.MAX_VALUE);
		query.setHighlight(true);
		query.setHighlightFragsize(3000);
		query.addHighlightField("pdftext");
		query.setHighlightSimplePre("<font style=\"color:red\" class=\"highlight\">");
		query.setHighlightSimplePost("</font>");
		
		QueryResponse rsp=server.query(query);
		SolrDocumentList sdList=rsp.getResults();
		
		Map<String,Map<String,List<String>>> highlighting=rsp.getHighlighting();
		List<Doc> docList=null;
		for(int i=0;i<sdList.size();i++){
			SolrDocument solrDocument=sdList.get(i);
			String docpath=(String) solrDocument.get("id");
			String title=docpath.substring(docpath.lastIndexOf("\\")+1);
			List<String> list=highlighting.get(solrDocument.get("id")).get("pdftext");
			//System.out.println(docpath);
			String description=list.get(0);
			//�жϼ���������Դ��״̬
			int symbol=docDAO.getSymbol(docpath);
			if(symbol==1||symbol==-1){
				//symbolΪ1ʱ����-1��ʱ��һ�ɱ��Ϊ-1�����������˹����ͨ���Ĳ������޸�Ϊ�����״̬��
				writeDocWithoutCheckToDB(docpath, description);
			}
		}
	}
	
	//������ѯ���дʣ���ÿ�β�ѯ�Ľ��д��task��taskdetail����
	public void WriteTaskDetails() throws SolrServerException{
		System.out.println("writeTaskDetail����������������д�����ݿ�");
		
		Task task=new Task();
		task.setExeTime(new Date(new java.util.Date().getTime()));
		
		//����insert������ͬʱ��Ҳ�Զ�Ϊtask��id��ֵ
		taskDAO.insert(task);
		List<TaskDetail> taskDetails=new ArrayList<TaskDetail>();;
		
		String queryWord=null;
		
		List<SenWord> words=queryWordInUse();
		for(SenWord senWord:words){
			queryWord="pdftext:"+senWord.getWord();
			SolrQuery query=new SolrQuery();
			query.set("q", queryWord);
			//��Ϊ�÷���������д��task��taskdetail�����Բ���Ҫ���ø���
			QueryResponse rsp=server.query(query);
			
			//��ȡsolr�������ķ���ֵ��������ÿһƪ���½��д���
			SolrDocumentList sdList=rsp.getResults();
			System.out.println("sdListSize:"+sdList.size());
			for(int i=0;i<sdList.size();i++){
				
				SolrDocument solrDocument=sdList.get(i);
				String docpath=(String)solrDocument.get("id");
				System.out.println(docpath);
				//�жϸ��ĵ���״̬�����Ѿ������������ͨ����ˣ��򲻽���д��taskdetail����
				int symbol=docDAO.getSymbol(docpath);
				System.out.println(symbol);
				if(symbol!=0&&symbol!=2){
				TaskDetail taskDetail=new TaskDetail();
				taskDetail.setDocpath(docpath);
				taskDetail.setWord(senWord.getWord());
				taskDetail.setTaskId(task.getId());
				System.out.println("taskDetail:"+taskDetail);
				taskDetails.add(taskDetail);
				
				}
			}
			System.out.println("taskDetails:"+taskDetails);
			
		}
		taskDetailDAO.batchSave(taskDetails);
	}
	
	//��ѯ�����ã����������е����д�,������ѯ���Ƿ�ҳչʾ��
	public List<SenWord> queryWordInUse(){
		List<SenWord> list=senWordDAO.getWordsInUse();
		return list;
	}
	
	//������ָ�����ĵ����Ϊ�����
	public void writeDocWithoutCheckToDB(String docpath,String description){
		docDAO.changeSymbol(docpath, -1);
		docDAO.addDescription(description, docpath);
	}
}
