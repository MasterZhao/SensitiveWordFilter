package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import po.Doc;
import dao.DocDAO;
import dao.imple.DocDAOImple;
import utils.FileUtils;

public class IndexService {
	
	DocDAO docDao=new DocDAOImple();
	HttpSolrServer server=new HttpSolrServer("http://localhost:8080/solr/collection2");
	
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
	
	public boolean deleteAllIndex(){
		
		HttpSolrServer server=new HttpSolrServer("http://localhost:8080/solr/collection2");
		try {
			server.deleteByQuery("*:*");
			server.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	//将创建索引的文件写入数据库
	public void writeDocIntoDataBase() throws SolrServerException{
		SolrQuery query=new SolrQuery();
		query.set("q","*:*");
		QueryResponse rsp=server.query(query);
		SolrDocumentList docList=rsp.getResults();
		List<Doc> list=new ArrayList<>();
		for(int i=0;i<docList.size();i++){
			SolrDocument document=docList.get(i);
			String docpath=(String)document.get("id");
			String title = docpath.substring(docpath.lastIndexOf("\\")+1);
			list.add(new Doc(null,title,docpath,1,null));
		}
		docDao.saveBatch(list);
	}
}
