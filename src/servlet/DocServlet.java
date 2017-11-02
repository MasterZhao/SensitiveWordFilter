package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import po.Doc;
import service.DocService;
import service.QueryService;
import web.Page;

/**
 * Servlet implementation class DocServlet
 */
@WebServlet("/docServlet")
public class DocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DocService docService=new DocService();
	private QueryService queryService=new QueryService();
   
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
	
	//加入黑名单
	protected void insertBlackList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=-1;
		String pageSymbol=null;
		String path=null;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try {
			pageSymbol=request.getParameter("pageSymbol");
			if(pageSymbol==null){
				pageSymbol="checkdoc";
			}
		} catch (Exception e) {
			pageSymbol="checkdoc";
		}
		path=request.getParameter("path");
		System.out.println("获取到的path为："+path);
		try {
			docService.insertBlackList(path);
			System.out.println("已将"+path+"加入黑名单");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
		}
		System.out.println(pageSymbol);
		if(pageSymbol.equals("checkdoc")){
			Page<Doc> page=queryService.getDocWithoutCheckPage(pageNo);
			if(page.getList().isEmpty()&&pageNo>1){
				pageNo=pageNo-1;
			}
			request.getRequestDispatcher("queryServlet?method=getDocWithoutCheckPage&pageNo="+pageNo).forward(request, response);
		}
		if(pageSymbol.equals("alldoc")){
			request.getRequestDispatcher("queryServlet?method=getAllDocPage&pageNo="+pageNo).forward(request, response);
		}
		if(pageSymbol.equals("passdoc")){
			request.getRequestDispatcher("queryServlet?method=getPassDocPage&pageNo="+pageNo).forward(request, response);
		}
	}
	
	//审核通过
	protected void pass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=null;
		int pageNo=1;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try {
			path=request.getParameter("path");
			if(path==null){
				response.sendRedirect(request.getContextPath()+"/param-error.jsp");
				return;
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
		}
		//调用docService的pass方法，修改doc的symbol
		try {
			docService.pass(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(pageNo);
		Page<Doc> page=queryService.getDocWithoutCheckPage(pageNo);
		if(page.getList().isEmpty()&&pageNo>1){
			pageNo=pageNo-1;
		}
		request.getRequestDispatcher("queryServlet?method=getDocWithoutCheckPage&pageNo+"+pageNo).forward(request, response);
		
	}
	
	//从黑名单中移除(审核通过)
		protected void removeFromBlackList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String path=request.getParameter("path");
			String pageSymbol=null;
			int pageNo=1;
			try {
				pageSymbol=request.getParameter("pageSymbol");
				System.out.println(pageSymbol);
				if(pageSymbol==null){
					pageSymbol="blacklist";
				}
			} catch (Exception e) {
				pageSymbol="blacklist";
			}		
			try {
				 pageNo=Integer.parseInt(request.getParameter("pageNo"));
				 System.out.println(pageNo);
			} catch (Exception e) {
				pageNo=1;
			}
			try {
				docService.pass(path);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath()+"/param-error.jsp");
			}
			//开始判断转发到哪个页面
			if(pageSymbol.equals("blacklist")){
				//调用getBlackListPage()获取page，是为了先判断当前页数获取到的页面是否为空
				//因为要返回的页面的blacklistpage，当前页面为空，且页面的页数>1，则显示上一页面
				Page<Doc> page=queryService.getBlackListPage(pageNo);
				if(page.getList().isEmpty()==true&&pageNo>1){
					pageNo=pageNo-1;
				}
				request.getRequestDispatcher("queryServlet?method=getBlackListPage&pageNo="+pageNo).forward(request, response);
			}
			if(pageSymbol.equals("alldoc")){
				request.getRequestDispatcher("queryServlet?method=getAllDocPage&pageNo="+pageNo).forward(request, response);
			}
		}
		
	//从数据库中删除该文档的记录
	protected void deleteDoc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=null;
		int pageNo=1;
		String pageSymbol="";
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try {
			pageSymbol=request.getParameter("pageSymbol");
		} catch (Exception e) {
			pageSymbol="";
		}
		try {
			path=request.getParameter("path");
			if(path==null){
				response.sendRedirect(request.getContextPath()+"/param-error.jsp");
				return;
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
		}
		//调用docService的pass方法，修改doc的symbol
		try {
			docService.deleteDoc(path);
		} catch (Exception e) {
		}
		request.getRequestDispatcher("queryServlet?method=getDocWithoutCheckPage&pageNo+"+pageNo).forward(request, response);
	}
//	protected void addDescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String description=request.getParameter("description");
//		String docpath=request.getParameter("docpath");
//		System.out.println(description);
//		System.out.println(docpath);
//		docService.addDescription(description,docpath);
//		request.getRequestDispatcher("queryServlet?method=startSearch").forward(request, response);
//	}
}
