package po;

public class TaskDetail {

	private String word;
	private String docpath;
	private int taskId;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getDocpath() {
		return docpath;
	}
	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public TaskDetail(){
		
	}
	
	public TaskDetail(String word, String docpath, int taskId) {
		super();
		this.word = word;
		this.docpath = docpath;
		this.taskId = taskId;
	}
	
	@Override
	public String toString() {
		return "TaskDetail [word=" + word + ", docpath=" + docpath
				+ ", taskId=" + taskId + "]";
	}
}
