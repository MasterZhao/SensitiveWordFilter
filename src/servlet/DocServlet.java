package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DocService;
import service.IndexService;

/**
 * Servlet implementation class DocServlet
 */
@WebServlet("/docServlet")
public class DocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DocService docService=new DocService();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	private IndexService indexService=new IndexService();
	
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
		
		String docpath=request.getParameter("docpath");
		//若使用超链接的方式，为防止乱码出现
		//String title=new String(titlestr.getBytes("iso8859-1"),"UTF-8");
		//docService.insertBlackList(title);
		docService.insertBlackList(docpath);
		request.getRequestDispatcher("queryServlet?method=startSearch").forward(request, response);
	}
	
	//从黑名单中移除
	protected void removeFromBlackList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getParameter("path");
		System.out.println(path);
		docService.pass(path);
		request.getRequestDispatcher("queryServlet?method=getBlackList").forward(request, response);
	}
	
	//审核通过
	protected void pass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getParameter("docpath");
		docService.pass(path);
		request.getRequestDispatcher("queryServlet?method=startSearch").forward(request, response);
	}
	protected void addDescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String description=request.getParameter("description");
		String docpath=request.getParameter("docpath");
		System.out.println(description);
		System.out.println(docpath);
		docService.addDescription(description,docpath);
		request.getRequestDispatcher("queryServlet?method=startSearch").forward(request, response);
	}
}
