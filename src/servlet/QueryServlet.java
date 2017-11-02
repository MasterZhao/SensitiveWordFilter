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
import po.LevelCount;
import po.SenWord;
import po.Task;
import po.WordCount;
import service.QueryService;
import service.TaskService;
import web.Page;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/queryServlet")
public class QueryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private QueryService queryService=new QueryService();
	private TaskService taskService=new TaskService();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName=request.getParameter("method");
		try {
			Method method=getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this,request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//采用分页的方式显示敏感词，内部做判断
	protected void getWordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int pageNo=1;
		int wordSymbol=-1;
		Page<SenWord> page=null;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try{
			wordSymbol=Integer.parseInt(request.getParameter("wordSymbol"));
		}catch(Exception e){
			wordSymbol=-1;
		}
		System.out.println(wordSymbol);
		if(wordSymbol==-1){
			page=queryService.getWordPage(pageNo);
		}else if(wordSymbol==0){
			page=queryService.getStopWordPage(pageNo);
		}else if(wordSymbol==1){
			page=queryService.getStartWordPage(pageNo);
		}
//		System.out.println(page.getList());
		System.out.println("当前是第："+pageNo+"页");
		request.setAttribute("wordpage", page);
		request.setAttribute("wordSymbol", wordSymbol);
		request.getRequestDispatcher("words.jsp").forward(request, response);
	}
	//采用分页方式显示启用中的敏感词
//	protected void getStartWordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		int pageNo=1;
//		try {
//			pageNo=Integer.parseInt(request.getParameter("pageNo"));
//		} catch (Exception e) {
//		}
//		Page<SenWord> page=queryService.getWordPage(pageNo);
//		System.out.println(page.getList());
//		System.out.println(page.getPageNo());
//		request.setAttribute("wordpage", page);
//		request.getRequestDispatcher("words.jsp").forward(request, response);		
//	}
//	
	
	//开始对索引库进行敏感词搜索
	protected void startSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SolrServerException{
		String word=null;
		int pageNo=1;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try {
			word=request.getParameter("word");
			if(word==null){
				response.sendRedirect(request.getContextPath()+"/param-error.jsp");
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
			return;
		}
		//不从数据库中获取数据，而是从索引库中获取文档
		List<Doc> docs=null;
		try {
			//在service中判断word
			Page<Doc> page=queryService.startSearch(word,pageNo);
			System.out.println(docs);

			request.setAttribute("searchdocpage", page);
			request.setAttribute("size", page.getTotalItemNumber());
			request.setAttribute("word", word);
			request.getRequestDispatcher("searchdocpage.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			//response.sendRedirect(request.getContextPath()+"/error-1.jsp");
		}
	}
	
	//获取待审核的文档，indexServlet和queryServlet都会调用该方法
	protected void getDocWithoutCheckPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//若是在创建索引后时调用该方法（indexServlet调用），则有size。若是queryServlet调用，则无size参数
		int size=-1;
		int pageNo=-1;
		try {
			size=Integer.parseInt(request.getParameter("size"));
		} catch (Exception e) {
			size=-1;
		}
		
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			if(pageNo==-1){
				pageNo=1;
			}
		} catch (Exception e) {
			pageNo=1;
		}
		Page<Doc> page=queryService.getDocWithoutCheckPage(pageNo);
		if(size>0){
			request.setAttribute("size", size);
		}
		System.out.println(pageNo);
		System.out.println(page.getList().size());
		request.setAttribute("checkdocpage", page);
		request.getRequestDispatcher("checkdocpage.jsp").forward(request, response);
	}
	
	//分页获取doc中的黑名单
	protected void getBlackListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=-1;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		
		Page<Doc> page=queryService.getBlackListPage(pageNo);
		request.setAttribute("blacklistpage", page);
		request.getRequestDispatcher("blacklistpage.jsp").forward(request, response);
	}
	
	//返回所有任务
	protected void getAllTaskPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=-1;
		try{
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		}catch(Exception e){
			pageNo=1;
		}
		Page<Task> taskpage=taskService.getAllTasks(pageNo);
		request.setAttribute("taskpage", taskpage);
		request.getRequestDispatcher("tasks.jsp").forward(request, response);
	}
	
	//根据任务id返回任务详情
	protected void getTaskDetailsById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taskId=-1;
		try{
			taskId=Integer.parseInt(request.getParameter("taskId"));
			if(taskId<0){
				taskId=1;
			}
		}catch(Exception e){
			taskId=1;
		}
		System.out.println(taskId);
		List<WordCount> wordCountList=taskService.getWordCountListById(taskId);
		System.out.println(wordCountList);
		List<LevelCount> levelCountList=taskService.getLevelCountById(taskId);
		System.out.println(levelCountList);
		request.setAttribute("wordcountlist", wordCountList);
		request.setAttribute("levelcountlist", levelCountList);
		request.setAttribute("taskId", taskId);
		request.getRequestDispatcher("taskdetails.jsp").forward(request, response);
	}
	//返回所有文档资源
	protected void getAllDocPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=1;
		try{
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		}catch(Exception e){
			pageNo=1;
		}
		Page<Doc> alldocpage=queryService.getAllDoc(pageNo);
		request.setAttribute("alldocpage",alldocpage);
		request.getRequestDispatcher("alldoc.jsp").forward(request, response);
	}
	//返回通过审核的资源
	protected void getPassDocPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=1;
		try{
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			if(pageNo==0){
				pageNo=1;
			}
		}catch(Exception e){
			pageNo=1;
		}
		Page<Doc> passDocPage=queryService.getPassDocPage(pageNo);
		request.setAttribute("passdocpage",passDocPage);
		request.getRequestDispatcher("passdocpage.jsp").forward(request, response);
	}
	
	//在敏感词库中查询敏感词信息
	protected void getOneWord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String word=null;
		try {
			word=request.getParameter("word");
			System.out.println(word);
			if(word==null){
				response.sendRedirect(request.getContextPath()+"/param-error.jsp");
				return;
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
		}
		SenWord senword=queryService.getOneWord(word);
		request.setAttribute("senword",senword);
		System.out.println(senword);
		request.getRequestDispatcher("words.jsp").forward(request, response);
	}
	
	protected void getTaskReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long allTaskNum=0;
		long taskDiscoveredNum=0;
		long docNum=0;
		long blackListNum=0;
		long wordNum=0;
		long wordDiscoveredNum=0;
		List<LevelCount> levelCountList=null;
		try {
			allTaskNum=taskService.getAllTaskNum();
			taskDiscoveredNum=taskService.getTaskDiscoveredNum();
			docNum=queryService.getAllDocNum();
			blackListNum=queryService.getBlackListNum();
			wordNum=queryService.getAllWordNum();
			wordDiscoveredNum=queryService.getWordDiscoveredNum();
			levelCountList=taskService.getAllLevelCount();
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/error-2.jsp");
			return;
		}
//		System.out.println("总任务数："+allTaskNum);
//		System.out.println("发现敏感词任务数："+taskDiscoveredNum);
//		System.out.println("总文档数:"+allDocNum);
//		System.out.println("黑名单数:"+blackListNum);
//		System.out.println("总敏感词数："+allWordNum);
//		System.out.println("发现敏感词："+wordDiscoveredNum);
		request.setAttribute("alltasknum", allTaskNum);
		request.setAttribute("taskdiscoverednum",taskDiscoveredNum);
		request.setAttribute("alldocnum", docNum);
		request.setAttribute("blacklistnum", blackListNum);
		request.setAttribute("allwordnum", wordNum);
		request.setAttribute("worddiscoverednum", wordDiscoveredNum);
		request.setAttribute("levelcountlist", levelCountList);
		//转发到taskreport.jsp
		request.getRequestDispatcher("taskreport.jsp").forward(request, response);
		
	}
}
