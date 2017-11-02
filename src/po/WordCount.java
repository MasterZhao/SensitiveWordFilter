package po;

public class WordCount {

	private String word;
	private long count;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public WordCount(String word,long count) {
		super();
		this.word = word;
		this.count = count;
	}
	public WordCount() {
		super();
	}
	
	@Override
	public String toString() {
		return "WordCount [word=" + word + ", count=" + count + "]";
	}
	
	
}
