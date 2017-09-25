package po;

public class Doc {

	private Integer id;
	private String title;
	private String docPath;
	private String content;
	private Integer docSymbol;
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getDocSymbol() {
		return docSymbol;
	}
	public void setDocSymbol(Integer docSymbol) {
		this.docSymbol = docSymbol;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	//�޲ι��췽��
	public Doc() {
	}
	
	@Override
	public String toString() {
		return "Doc [id=" + id + ", title=" + title + ", docPath=" + docPath
				+ ", content=" + content + ", docSymbol=" + docSymbol
				+ ", description=" + description + "]";
	}
	
	public Doc(Integer id,String title, String docPath,
			Integer docSymbol, String description) {
		//Doc����content���������ǲ��ڹ��췽����ʹ�ã���Ϊ���ݿ���û����������ֶ�
		super();
		this.id=id;
		this.title = title;
		this.docPath = docPath;
		this.docSymbol = docSymbol;
		this.description = description;
	}
	
}

