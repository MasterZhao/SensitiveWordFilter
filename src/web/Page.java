package web;

import java.util.List;
//Page��������װ��ҳ��Ϣ
public class Page<T> {
	//��ǰ�ǵڼ�ҳ
	private int pageNo;
	//��ǰҳ��List
	private List<T> list;
	//ÿҳ�ж�������¼
	private int pageSize=10;
	//һ���ж�������¼
	private long totalItemNumber;
	
	private int totalPageNumber;
	
	//����������Ҫ��pageNo���г�ʼ��
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
		
	}
	public int getPageNo() {
		//У���ȡ��pageNo�Ƿ�Ϸ�
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
	//��ȡ��ҳ��
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
