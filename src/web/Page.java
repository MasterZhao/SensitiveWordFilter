package web;

import java.util.List;
//Page类用来封装翻页信息
public class Page<T> {
	//当前是第几页
	private int pageNo;
	//当前页的List
	private List<T> list;
	//每页有多少条记录
	private int pageSize=10;
	//一共有多少条记录
	private long totalItemNumber;
	
	private int totalPageNumber;
	
	//构造器中需要对pageNo进行初始化
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
		
	}
	public int getPageNo() {
		//校验获取的pageNo是否合法
		if(pageNo<0){
			pageNo=1;
		}
		if(pageNo>getTotalPageNumber()){
			pageNo=getTotalPageNumber();
		}
		return pageNo;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public List<T> getList() {
		return list;
	}
	//获取总页数
	public int getTotalPageNumber(){
		int totalPageNumber=(int)totalItemNumber/pageSize;
		if(totalItemNumber%pageSize!=0){
			totalPageNumber++;
		}
		return totalPageNumber;
	}
	public void setTotalItemNumber(long totalItemNumber){
		this.totalItemNumber=totalItemNumber;
	}
	public long getTotalItemNumber(){
		return this.totalItemNumber;
	}
	public boolean hasNext(){
		if(getPageNo()<getTotalPageNumber()){
			return true;
		}
		return false;
	}
	public boolean hasPrev(){
		if(getPageNo()>1){
			return true;
		}
		return false;
	}
	public int getNext(){
		if(hasNext()){
			return getPageNo()+1;
		}
		return getPageNo();
	}
	public int getPrev(){
		if(hasPrev()){
			return getPageNo()-1;
		}
		return getPageNo();
	}
}
