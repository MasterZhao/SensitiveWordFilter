package po;

public class TaskDetail {

	private String docpath;
	private String word;
	private Integer num;
	private Integer taskId;
	public String getDocpath() {
		return docpath;
	}
	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	//构造方法
	public TaskDetail() {
		super();
	}
	
	public TaskDetail(String docpath, String word, Integer num, Integer taskId) {
		super();
		this.docpath = docpath;
		this.word = word;
		this.num = num;
		this.taskId = taskId;
	}
	@Override
	public String toString() {
		return "TaskDetail [docpath=" + docpath + ", word=" + word + ", num="
				+ num + ", taskId=" + taskId + "]";
	}
	
}
