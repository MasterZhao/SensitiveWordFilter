package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	
	public  List<File> list=new ArrayList<>();
	
	public  List<File> readFiles(String path){
		
		//获取参数中的目录
		File filepath=new File(path);
		//创建List，储存该目录下的所有文件的完整路径名
		if(filepath.isDirectory()){
			File[] filelist=filepath.listFiles();
			for(File file:filelist){
				
				if(file.isDirectory()){
					//若读取到的文件是一个目录，继续读取该目录下的所有文件
					readFiles(file.getAbsolutePath());
				}else if(!file.isDirectory()){
					//若读取到的不是目录，则记录在pathlist中
					File readfile=new File(file.getAbsolutePath());
					System.out.println(readfile);
					list.add(readfile);
				}
			}
			System.out.println("输入的是目录");
	} 
		return list;
	}
}
