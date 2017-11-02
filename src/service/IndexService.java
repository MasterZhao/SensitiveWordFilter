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
	
	//创建索引
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
			//设置文档的唯一标识（完整路径）
			up.setParam("literal.id", docpath);
			up.setParam("fmap.content", "pdftext");
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
			server.request(up);
		}
		server.commit();
		System.out.println("共导入"+list.size()+"篇文档");
		long size=list.size();
		return size;
	}
	
	//在开始之前先删除之前所有的索引
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
	
	//在创建索引的同时，将所有创建索引的文件写入数据库
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
	
	//检索全部敏感词，将所有匹配到的文档标记为待审核状态(除了表中已经被标记为黑名单和审核通过的资源)
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
			//判断检索到的资源的状态
			int symbol=docDAO.getSymbol(docpath);
			if(symbol==1||symbol==-1){
				//symbol为1时（或-1）时，一律标记为-1（黑名单和人工审核通过的不可以修改为待审核状态）
				writeDocWithoutCheckToDB(docpath, description);
			}
		}
	}
	
	//逐条查询敏感词，将每次查询的结果写入task和taskdetail表中
	public void WriteTaskDetails() throws SolrServerException{
		System.out.println("writeTaskDetail方法，将任务数据写入数据库");
		
		Task task=new Task();
		task.setExeTime(new Date(new java.util.Date().getTime()));
		
		//调用insert方法的同时，也自动为task的id赋值
		taskDAO.insert(task);
		List<TaskDetail> taskDetails=new ArrayList<TaskDetail>();;
		
		String queryWord=null;
		
		List<SenWord> words=queryWordInUse();
		for(SenWord senWord:words){
			queryWord="pdftext:"+senWord.getWord();
			SolrQuery query=new SolrQuery();
			query.set("q", queryWord);
			//因为该方法是用于写入task和taskdetail表，所以不需要设置高亮
			QueryResponse rsp=server.query(query);
			
			//获取solr服务器的返回值，遍历对每一篇文章进行处理
			SolrDocumentList sdList=rsp.getResults();
			System.out.println("sdListSize:"+sdList.size());
			for(int i=0;i<sdList.size();i++){
				
				SolrDocument solrDocument=sdList.get(i);
				String docpath=(String)solrDocument.get("id");
				System.out.println(docpath);
				//判断该文档的状态，若已经加入黑名单或通过审核，则不进行写入taskdetail操作
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
	
	//查询（调用）所有启用中的敏感词,用作查询（非分页展示）
	public List<SenWord> queryWordInUse(){
		List<SenWord> list=senWordDAO.getWordsInUse();
		return list;
	}
	
	//用来将指定的文档标记为待审核
	public void writeDocWithoutCheckToDB(String docpath,String description){
		docDAO.changeSymbol(docpath, -1);
		docDAO.addDescription(description, docpath);
	}
}
