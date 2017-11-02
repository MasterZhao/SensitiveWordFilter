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
	
	//���÷�ҳ�ķ�ʽ��ʾ���дʣ��ڲ����ж�
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
		System.out.println("��ǰ�ǵڣ�"+pageNo+"ҳ");
		request.setAttribute("wordpage", page);
		request.setAttribute("wordSymbol", wordSymbol);
		request.getRequestDispatcher("words.jsp").forward(request, response);
	}
	//���÷�ҳ��ʽ��ʾ�����е����д�
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
	
	//��ʼ��������������д�����
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
		//�������ݿ��л�ȡ���ݣ����Ǵ��������л�ȡ�ĵ�
		List<Doc> docs=null;
		try {
			//��service���ж�word
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
	
	//��ȡ����˵��ĵ���indexServlet��queryServlet������ø÷���
	protected void getDocWithoutCheckPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�����ڴ���������ʱ���ø÷�����indexServlet���ã�������size������queryServlet���ã�����size����
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
	
	//��ҳ��ȡdoc�еĺ�����
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
	
	//������������
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
	
	//��������id������������
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
	//���������ĵ���Դ
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
	//����ͨ����˵���Դ
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
	
	//�����дʿ��в�ѯ���д���Ϣ
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
//		System.out.println("����������"+allTaskNum);
//		System.out.println("�������д���������"+taskDiscoveredNum);
//		System.out.println("���ĵ���:"+allDocNum);
//		System.out.println("��������:"+blackListNum);
//		System.out.println("�����д�����"+allWordNum);
//		System.out.println("�������дʣ�"+wordDiscoveredNum);
		request.setAttribute("alltasknum", allTaskNum);
		request.setAttribute("taskdiscoverednum",taskDiscoveredNum);
		request.setAttribute("alldocnum", docNum);
		request.setAttribute("blacklistnum", blackListNum);
		request.setAttribute("allwordnum", wordNum);
		request.setAttribute("worddiscoverednum", wordDiscoveredNum);
		request.setAttribute("levelcountlist", levelCountList);
		//ת����taskreport.jsp
		request.getRequestDispatcher("taskreport.jsp").forward(request, response);
		
	}
}
