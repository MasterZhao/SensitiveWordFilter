package po;

public class SenWord {

	private Integer id;
	private String word;
	private Integer wordSymbol;
	private Integer wordLevel;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getWordSymbol() {
		return wordSymbol;
	}
	public void setWordSymbol(Integer wordSymbol) {
		this.wordSymbol = wordSymbol;
	}
	public Integer getWordLevel() {
		return wordLevel;
	}
	public void setWordLevel(Integer wordLever) {
		this.wordLevel = wordLever;
	}
	//构造方法
	public SenWord() {
		super();
	}
	
	public SenWord(Integer id, String word, Integer wordSymbol,
			Integer wordLevel) {
		this.id = id;
		this.word = word;
		this.wordSymbol = wordSymbol;
		this.wordLevel = wordLevel;
	}
	@Override
	public String toString() {
		return "SenWord [id=" + id + ", word=" + word + ", wordSymbol="
				+ wordSymbol + ", wordLever=" + wordLevel + "]";
	}
}
