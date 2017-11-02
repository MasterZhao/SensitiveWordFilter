package servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import po.SenWord;
import service.QueryService;
import service.WordService;
import web.Page;

import com.google.gson.Gson;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/wordServlet")
public class WordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WordService wordService=new WordService();
	QueryService queryService=new QueryService();
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
	
	//插入单条敏感词
	protected void insertWord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newword=request.getParameter("newword");
		int pageNo=1;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(newword.length()>20){
			request.getRequestDispatcher("error-2.jsp").forward(request, response);;
		}else{
		int level=Integer.parseInt(request.getParameter("level"));
		SenWord senWord=new SenWord(null,newword,1,level);
		wordService.insertWord(senWord);
		request.getRequestDispatcher("queryServlet?method=getWordPage&pageNo="+pageNo).forward(request, response);
		}
	}
	
	//更改wordSymbol(停用/启用)
	protected void changeSymbol(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=1;
		int pageNo=1;
		int symbol=0;
		String pageSymbol=null;
		
		try {
			id=Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
			return;
		}
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try {
			symbol=Integer.parseInt(request.getParameter("symbol"));
		} catch (Exception e) {
			symbol=1;
		}
		
		if(symbol==0){
			symbol=1;
		}else if(symbol==1){
			symbol=0;
		}else{
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
			return;
		}
		try{
			wordService.changeSymbol(id, symbol);	
		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
			return;
		}
		request.getRequestDispatcher("queryServlet?method=getWordPage&pageNo="+pageNo).forward(request, response);
	}
	
	protected void deleteWord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id=-1;
		int pageNo=-1;
		int wordSymbol=-1;
		try {
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			pageNo=1;
		}
		try {
			id=Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id=1;
		}
		try {
			wordSymbol=Integer.parseInt(request.getParameter("wordSymbol"));
		} catch (Exception e) {
			wordSymbol=1;
		}
		System.out.println(id);
		wordService.deleteWord(id);
		
		//删除敏感词后，重新获取当前页面的page，但是有可能删除的词是当前页面的唯一一个词
		//所以要判断删除后，当前页面是否为空，所为空且当前页面不是第一页，则显示上一页的内容
		Page<SenWord> page=queryService.getWordPage(pageNo);
		if(page.getList().isEmpty()==true&&pageNo>1){
			pageNo=pageNo-1;
		}
		System.out.println("当前是第："+pageNo);
		request.getRequestDispatcher("queryServlet?method=getWordPage&pageNo="+pageNo+"&wordSymbol="+wordSymbol).forward(request, response);
	}
	
	//修改敏感词等级(AJAX)
	protected void changeLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		String levelStr=request.getParameter("level");
		boolean success=true;
		//System.out.println(id);
		//System.out.println(levelStr);
		int level=1;
		try {
				level=Integer.parseInt(levelStr);
				//System.out.println(level);
			
		} catch (Exception e) {
			success=false;
		}
		if(success==true){
			wordService.changeLevel(id, level);
		}
		
		Map<String, Object> result=new HashMap<String,Object>();
		result.put("success", success);
		Gson gson=new Gson();
		String jsonStr=gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}
	
	protected void deleteAllWords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean success=wordService.deleteAllWord();
		
		if(success==true){
			request.getRequestDispatcher("queryServlet?method=getWordPage").forward(request, response);
		}else{
			request.getRequestDispatcher(request.getContextPath()+"/param-error.jsp");
		}
	}
	
}
