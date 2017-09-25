package servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import po.SenWord;
import service.WordService;
import dao.SenWordsDAO;
import dao.imple.SenWordsDAOImple;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/wordServlet")
public class WordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WordService wordService=new WordService();
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
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
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
		int id=Integer.parseInt(request.getParameter("id"));
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		int symbol=Integer.parseInt(request.getParameter("wordSymbol"));
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
		request.getRequestDispatcher("queryServlet?method=getWordPage&pageNo="+pageNo).forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/param-error.jsp");
		}
	}
	protected void deleteWord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id=Integer.parseInt(request.getParameter("id"));
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		System.out.println(id);
		wordService.deleteWord(id);
		request.getRequestDispatcher("queryServlet?method=getWordPage&pageNo="+pageNo).forward(request, response);
	}
	protected void changeLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		String levelStr=request.getParameter("level");
		boolean success;
		//System.out.println(id);
		//System.out.println(levelStr);
		int level=-1;
		try {
			if(levelStr.equals("1级敏感词")){
				level=1;
				//System.out.println(level);
			}
			if(levelStr.equals("2级敏感词")){
				level=2;
				//System.out.println(level);
			}
			if(levelStr.equals("3级敏感词")){
				level=3;
				//System.out.println(level);
			}
			wordService.changeLevel(id, level);
			success=true;
		} catch (Exception e) {
			success=false;
		}
		Map<String, Object> result=new HashMap<String,Object>();
		result.put("success", success);
		Gson gson=new Gson();
		String jsonStr=gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}
}
