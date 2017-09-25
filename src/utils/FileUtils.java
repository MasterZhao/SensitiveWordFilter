package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	
	public  List<File> list=new ArrayList<>();
	
	public  List<File> readFiles(String path){
		
		//��ȡ�����е�Ŀ¼
		File filepath=new File(path);
		//����List�������Ŀ¼�µ������ļ�������·����
		if(filepath.isDirectory()){
			File[] filelist=filepath.listFiles();
			for(File file:filelist){
				
				if(file.isDirectory()){
					//����ȡ�����ļ���һ��Ŀ¼��������ȡ��Ŀ¼�µ������ļ�
					readFiles(file.getAbsolutePath());
				}else if(!file.isDirectory()){
					//����ȡ���Ĳ���Ŀ¼�����¼��pathlist��
					File readfile=new File(file.getAbsolutePath());
					System.out.println(readfile);
					list.add(readfile);
				}
			}
			System.out.println("�������Ŀ¼");
	} 
		return list;
	}
}
