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
	
	//���������
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
		System.out.println("��ȡ����pathΪ��"+path);
		try {
			docService.insertBlackList(path);
			System.out.println("�ѽ�"+path+"���������");
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
	
	//���ͨ��
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
		//����docService��pass�������޸�doc��symbol
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
	
	//�Ӻ��������Ƴ�(���ͨ��)
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
			//��ʼ�ж�ת�����ĸ�ҳ��
			if(pageSymbol.equals("blacklist")){
				//����getBlackListPage()��ȡpage����Ϊ�����жϵ�ǰҳ����ȡ����ҳ���Ƿ�Ϊ��
				//��ΪҪ���ص�ҳ���blacklistpage����ǰҳ��Ϊ�գ���ҳ���ҳ��>1������ʾ��һҳ��
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
		
	//�����ݿ���ɾ�����ĵ��ļ�¼
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
		//����docService��pass�������޸�doc��symbol
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
