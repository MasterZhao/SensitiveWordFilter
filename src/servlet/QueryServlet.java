package servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;

import po.Doc;
import po.SenWord;
import service.DocService;
import service.QueryService;
import web.Page;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/queryServlet")
public class QueryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private QueryService queryService=new QueryService();
	DocService docService=new DocService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName=request.getParameter("method");
		try {
			Method method=getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	//不采用分页的方式
//	protected void queryWords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		List words=queryService.queryWords();
//		System.out.println(words);
//		request.setAttribute("words", words);
//		request.getRequestDispatcher("words.jsp").forward(request, response);
//		
//	}
	
	//采用分页的方式显示敏感词，内部做判断
	protected void getWordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		int wordSymbol=-1;
		Page<SenWord> page=null;
		try{
			wordSymbol=Integer.parseInt(request.getParameter("wordSymbol"));
		}catch(Exception e){
		}
		System.out.println(wordSymbol);
		if(wordSymbol==-1){
			page=queryService.getWordPage(pageNo);
		}if(wordSymbol==0){
			page=queryService.getStopWordPage(pageNo);
		}if(wordSymbol==1){
			page=queryService.getStartWordPage(pageNo);
		}
		System.out.println(page.getList());
		System.out.println(page.getPageNo());
		request.setAttribute("wordpage", page);
		request.setAttribute("wordSymbol", wordSymbol);
		request.getRequestDispatcher("words.jsp").forward(request, response);
		
	}
	//采用分页方式显示启用中的敏感词
	protected void getStartWordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		Page<SenWord> page=queryService.getWordPage(pageNo);
		System.out.println(page.getList());
		System.out.println(page.getPageNo());
		request.setAttribute("wordpage", page);
		request.getRequestDispatcher("words.jsp").forward(request, response);
		
	}
	
	
	//开始对solr索引库进行敏感词搜索
	protected void startSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SolrServerException{
		String word=null;
		if(request.getParameter("word")==null){
			word="";
		}else{
			word=request.getParameter("word");
		}
		List<Doc> docs=null;
		try {
			//不论word参数的值是否为空，都对solr索引库执行startSearch(word)操作
			//在service中判断word
			docs=queryService.startSearch(word);
			
			//为每一个doc添加symbol，判断集合中的doc是否已经存在与黑名单中
			docs=docService.addSymbol(docs);
			System.out.println(docs);
			request.setAttribute("docs", docs);
			request.getRequestDispatcher("results.jsp").forward(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
		}
		
	}
	
	//通过QueryService来获取doc表中的黑名单
	protected void getBlackList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Doc> list=queryService.getBlackList();
		request.setAttribute("blacklist", list);
		request.getRequestDispatcher("blacklist.jsp").forward(request, response);
	}
	
	protected void checkDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doc=request.getParameter("doc");
		request.getRequestDispatcher("docdetail.jsp").forward(request, response);
	}
	
}
