package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import service.WordService;


/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WordService wordService=new WordService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ע�ⲻҪ����tomcat�İ�������ᱨ��
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);  
	    upload.setFileSizeMax(1024*100);                       //�����ϴ��ļ����������  
	    //�ж��Ƿ��ϴ��ɹ�
	    boolean success=false;
	    try {
			List<FileItem> items = upload.parseRequest(request);
			//��ȡ��׺��
			String ext=null;
			try {
				ext = items.get(0).getName().split("\\.")[1];
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath()+"/param-error.jsp");
				return;
			}
			//��ȡ�������ļ�������������wordService��upload����
			InputStream in=items.get(0).getInputStream();
			success=wordService.upload(in,ext);
			System.out.println(success);
			if(success==false){
				response.sendRedirect(request.getContextPath()+"/error-2.jsp");
				return;
			}
			in.close();
//			if(ext.equals("xls")){
//				
//				HSSFWorkbook workbook=new HSSFWorkbook(in);
//				//��ȡ��һ��������
//				//HSSFSheet sheet=workbook.getSheet("Sheet0");
//				HSSFSheet sheet=workbook.getSheetAt(0);
//				int firstRowNum=0;
//				//��ȡ���һ�е��к�
//				int lastRowNum=sheet.getLastRowNum();
//				for(int i=firstRowNum;i<lastRowNum;i++){
//					HSSFRow row=sheet.getRow(i);
//					//��ȡ��ǰ�����һ�еĵ�Ԫ���к�
//					int lastCellNum=row.getLastCellNum();
//					for(int j=0;j<lastCellNum;j++){
//						HSSFCell cell=row.getCell(j);
//						String value=cell.getStringCellValue();
//						System.out.println(value+"   ");
//					}
//					System.out.println();
//				}
//			}
//			if(ext.equals("xlsx")){
//				XSSFWorkbook workbook=new XSSFWorkbook(in);
//				//��ȡ��һ��������
//				//XSSFSheet sheet=workbook.getSheet("Sheet0");
//				XSSFSheet sheet=workbook.getSheetAt(0);
//				int firstRowNum=0;
//				//��ȡ���һ�е��к�
//				int lastRowNum=sheet.getLastRowNum();
//				//ѭ����ÿ��ִ�в���
//				for(int i=firstRowNum;i<lastRowNum;i++){
//					XSSFRow row=sheet.getRow(i);
//					//��ȡ��ǰ�����һ�еĵ�Ԫ���к�
//					int lastCellNum=row.getLastCellNum();
//					//ѭ����ÿһ�н��ж�ȡ
//					for(int j=0;j<lastCellNum;j++){
//						XSSFCell cell=row.getCell(j);
//						cell.setCellType(Cell.CELL_TYPE_STRING);
//						String value=cell.getStringCellValue();
//						System.out.println(value);
//					}
//				}
//			}
//			
		} catch (FileUploadException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/error-2.jsp");
			return;
		} 
		if(success==true){
			response.sendRedirect(request.getContextPath()+"/uploadsuccess.jsp");
		}
				
	}

}
