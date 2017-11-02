package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestTime {

	@Test
	public void test1(){
		Date date=new Date();
		System.out.println(date.getTime());
		
		Date time=new java.sql.Date(date.getTime());
		System.out.println(time);
	}
	@Test
	public void test2(){
		System.out.println(System.currentTimeMillis());
	}
	
	@Test
	public void test3(){
		System.out.println(new Date().getTime());
	}
	
	@Test
	public void test4(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=df.format(new Date());
		System.out.println(date);
		System.out.println(date.getClass());
		System.out.println(date);
	}
}
