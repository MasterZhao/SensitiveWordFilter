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
		String pathStr=request.getParameter("path");
		String path=pathStr.trim();
		System.out.println(path);
		long size=0;
		try {
			//为了提高系统创建索引的速度，在执行操作前不再删除之前的索引文件
			//indexService.deleteAllIndex();
			size=indexService.createIndex(path);
			//对Doc表执行写入操作
			indexService.writeDocIntoDataBase();
			//将待审核的文档做标记
			indexService.signDocWithoutCheck();
			//写入任务详情
			indexService.WriteTaskDetails();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}
			//request.setAttribute("size", size);
			//request.getRequestDispatcher("success.jsp").forward(request, response);
		//在执行完任务后，跳转进入待审核资源界面，查看当前待审核任务
		if(size>0){
			request.getRequestDispatcher("queryServlet?method=getDocWithoutCheckPage&pageNo=1&size="+size).forward(request, response);
		}else{
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
		}
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
