package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrServerException;

import service.IndexService;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	protected void createIndex(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
		
		String path=request.getParameter("path");
		System.out.println(path);
		long size=0;
		try {
			//执行createIndex操作
			indexService.deleteAllIndex();
			size=indexService.createIndex(path);
			//对Doc表执行写入操作
			indexService.writeDocIntoDataBase();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}
			request.setAttribute("size", size);
			request.getRequestDispatcher("success.jsp").forward(request, response);
		}
	
	protected void deleteAllIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SolrServerException {
		boolean sign=indexService.deleteAllIndex();
		if(sign){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		if(!sign){
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
		}
	}	
}
