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
	
//�����ϴ���excel�ļ�	
public boolean upload(InputStream in, String ext){
		
		List<SenWord> wordlist=new ArrayList<SenWord>();
		
		//����׺Ϊxls��ִ�иò���
		if(ext.equals("xls")){
			HSSFWorkbook workbook = null;
			try {
				workbook = new HSSFWorkbook(in);
				//��ȡ��һ��������
				//HSSFSheet sheet=workbook.getSheet("Sheet0");
				HSSFSheet sheet=workbook.getSheetAt(0);
				int firstRowNum=1;
				//��ȡ���һ�е��к�
				int lastRowNum=sheet.getLastRowNum();
				for(int i=firstRowNum;i<=lastRowNum;i++){
					HSSFRow row=sheet.getRow(i);
					//��ȡ��ǰ�����һ�еĵ�Ԫ���к�
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
			System.out.println("�������xls����");
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		//����׺Ϊxlsx��ִ�иò���
		else if(ext.equals("xlsx")){
				XSSFWorkbook workbook=null;
				try {
					workbook = new XSSFWorkbook(in);
					//��ȡ��һ��������
					//XSSFSheet sheet=workbook.getSheet("Sheet0");
					XSSFSheet sheet=workbook.getSheetAt(0);
					//��ȡ�ڶ��е��кţ���һ��Ϊ0��
					int firstRowNum=1;
					//��ȡ���һ�е��к�
					int lastRowNum=sheet.getLastRowNum();
					//ѭ����ÿ��ִ�в���
					for(int i=firstRowNum;i<=lastRowNum;i++){
						XSSFRow row=sheet.getRow(i);
//						//��ȡ��ǰ�����һ�еĵ�Ԫ���к�
//						int lastCellNum=row.getLastCellNum();
//						//ѭ����ÿһ�н��ж�ȡ
//						for(int j=0;j<lastCellNum;j++){
//							XSSFCell cell=row.getCell(j);
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							String value=cell.getStringCellValue();
//							
//							System.out.println(value);
//						}
						//��ȡ�ʻ㵥Ԫ���ֵ
						XSSFCell wordcell=row.getCell(0);
						wordcell.setCellType(Cell.CELL_TYPE_STRING);
						String word=wordcell.getStringCellValue();
						if(word.length()>20){
							return false;
						}
						//��ȡ����Ԫ���ֵ
						XSSFCell levelcell=row.getCell(1);
						levelcell.setCellType(Cell.CELL_TYPE_STRING);
						int level=Integer.parseInt(levelcell.getStringCellValue());
						if(level>5){
							return false;
						}
						//�����дʷ���list
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
				System.out.println("�������xlsx����");
				senWordsDAO.batchSave(wordlist);
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
		}else{
			//��׺����ʽ������Ҫ�󣬷���false
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
