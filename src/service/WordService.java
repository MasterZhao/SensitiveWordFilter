package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import po.SenWord;
import dao.SenWordsDAO;
import dao.imple.SenWordsDAOImple;

public class WordService {

	SenWordsDAO senWordsDAO=new SenWordsDAOImple();
	
	public void insertWord(SenWord senWord){
		SenWord word=senWordsDAO.getWord(senWord.getWord());
		if(word==null){
			senWordsDAO.insert(senWord);
		}else{
			senWordsDAO.changeLevel(word.getId(),senWord.getWordLevel() );
		}
	}
	public void changeSymbol(Integer id,Integer symbol){
		senWordsDAO.changeSymbol(id, symbol);
	}
	public void deleteWord(int id){
		senWordsDAO.delete(id);
	}
	public void changeLevel(int id,int level){
		senWordsDAO.changeLevel(id, level);
	}
	public int getWordSymbol(int id) {
		
		return senWordsDAO.getWordSymbol(id);
	}
	
//导入上传的excel文件	
public boolean upload(InputStream in, String ext){
		
		List<SenWord> wordlist=new ArrayList<SenWord>();
		
		//若后缀为xls，执行该操作
		if(ext.equals("xls")){
			HSSFWorkbook workbook = null;
			try {
				workbook = new HSSFWorkbook(in);
				//获取第一个工作表
				//HSSFSheet sheet=workbook.getSheet("Sheet0");
				HSSFSheet sheet=workbook.getSheetAt(0);
				int firstRowNum=1;
				//获取最后一行的行号
				int lastRowNum=sheet.getLastRowNum();
				for(int i=firstRowNum;i<=lastRowNum;i++){
					HSSFRow row=sheet.getRow(i);
					//获取当前行最后一列的单元格列号
//					int lastCellNum=row.getLastCellNum();
//					for(int j=0;j<lastCellNum;j++){
//						HSSFCell cell=row.getCell(j);
//						String value=cell.getStringCellValue();
//						System.out.println(value+"   ");
//					}
					HSSFCell wordcell=row.getCell(0);
					wordcell.setCellType(Cell.CELL_TYPE_STRING);
					String word=wordcell.getStringCellValue();
					if(word.length()>20){
						return false;
					}
					HSSFCell levelcell=row.getCell(1);
					levelcell.setCellType(Cell.CELL_TYPE_STRING);
					int level=Integer.parseInt(levelcell.getStringCellValue());
					if(level>5){
						return false;
					}
					SenWord senword=new SenWord();
					senword.setWord(word);
					senword.setWordLevel(level);
					senword.setWordSymbol(1);
					wordlist.add(senword);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			System.out.println(wordlist.size());
			senWordsDAO.batchSave(wordlist);
			System.out.println("导入的是xls类型");
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		//若后缀为xlsx，执行该操作
		else if(ext.equals("xlsx")){
				XSSFWorkbook workbook=null;
				try {
					workbook = new XSSFWorkbook(in);
					//获取第一个工作表
					//XSSFSheet sheet=workbook.getSheet("Sheet0");
					XSSFSheet sheet=workbook.getSheetAt(0);
					//获取第二行的行号（第一行为0）
					int firstRowNum=1;
					//获取最后一行的行号
					int lastRowNum=sheet.getLastRowNum();
					//循环对每行执行操作
					for(int i=firstRowNum;i<=lastRowNum;i++){
						XSSFRow row=sheet.getRow(i);
//						//获取当前行最后一列的单元格列号
//						int lastCellNum=row.getLastCellNum();
//						//循环对每一列进行读取
//						for(int j=0;j<lastCellNum;j++){
//							XSSFCell cell=row.getCell(j);
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							String value=cell.getStringCellValue();
//							
//							System.out.println(value);
//						}
						//获取词汇单元格的值
						XSSFCell wordcell=row.getCell(0);
						wordcell.setCellType(Cell.CELL_TYPE_STRING);
						String word=wordcell.getStringCellValue();
						if(word.length()>20){
							return false;
						}
						//获取级别单元格的值
						XSSFCell levelcell=row.getCell(1);
						levelcell.setCellType(Cell.CELL_TYPE_STRING);
						int level=Integer.parseInt(levelcell.getStringCellValue());
						if(level>5){
							return false;
						}
						//将敏感词放入list
						SenWord senword=new SenWord();
						senword.setWord(word);
						senword.setWordLevel(level);
						senword.setWordSymbol(1);
						wordlist.add(senword);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				System.out.println(wordlist.size());
				System.out.println("导入的是xlsx类型");
				senWordsDAO.batchSave(wordlist);
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
		}else{
			//后缀名格式不符合要求，返回false
			return false;
		}
	}
	
	public List<SenWord> getAllWords(){
		return senWordsDAO.getWords();
	}
	public boolean deleteAllWord() {
		try {
			senWordsDAO.deleteAll();
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	
}
