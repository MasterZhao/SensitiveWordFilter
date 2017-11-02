package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import po.SenWord;
import service.WordService;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private WordService wordService=new WordService();   
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
	
	protected void downloadWords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<SenWord> wordlist=wordService.getAllWords();
		String[] title={"敏感词","等级"};
		XSSFWorkbook workBook=new XSSFWorkbook();
		XSSFSheet sheet=workBook.createSheet();
		XSSFRow row=sheet.createRow(0);
		XSSFCell cell=null;
		//插入第一行数据  word,wordlevel
		for(int i=0;i<title.length;i++){
			cell=row.createCell(i);
			cell.setCellValue(title[i]);
		}
		//为每一行写入数据
		for(int i=1;i<=wordlist.size();i++){
			XSSFRow nextrow=sheet.createRow(i);
			
			XSSFCell cell2=nextrow.createCell(0);
			//写入第一列数据
			cell2.setCellValue(wordlist.get(i-1).getWord());
			
			cell2=nextrow.createCell(1);
			//写入第二列数据
			cell2.setCellValue(wordlist.get(i-1).getWordLevel());
		}
//		File file=new File("d:/导出敏感词.xlsx");
//		try {
//			file.createNewFile();
//			//将excel内容写入到流中
//			FileOutputStream stream=FileUtils.openOutputStream(file);
//			workBook.write(stream);
//			stream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String fileName = "敏感词";
	      ByteArrayOutputStream os = new ByteArrayOutputStream();
	      workBook.write(os);
	      byte[] content = os.toByteArray();
	      InputStream is = new ByteArrayInputStream(content);
	      // 设置response参数，可以打开下载页面
	      response.reset();
	      response.setContentType("application/vnd.ms-excel;charset=utf-8");
	      response.setHeader("Content-Disposition", "attachment;filename="
	          + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
	      ServletOutputStream out = response.getOutputStream();
	      BufferedInputStream bis = null;
	      BufferedOutputStream bos = null;
	 
	      try {
	        bis = new BufferedInputStream(is);
	        bos = new BufferedOutputStream(out);
	        byte[] buff = new byte[2048];
	        int bytesRead;
	        
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	          bos.write(buff, 0, bytesRead);
	        }
	      } catch (Exception e) {
	        e.printStackTrace();
	      } finally {
	        if (bis != null)
	          bis.close();
	        if (bos != null)
	          bos.close();
	      }

	}

}
