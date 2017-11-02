package po;

public class LevelCount {
	//LevelCount类用来记录任务中敏感词各级别的数量
	private int wordLevel;
	private int levelCount;
	
	public int getWordLevel() {
		return wordLevel;
	}

	public void setWordLevel(int wordLevel) {
		this.wordLevel = wordLevel;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}

	public LevelCount() {
		super();
	}

	@Override
	public String toString() {
		return "LevelCount [wordLevel=" + wordLevel + ", levelCount="
				+ levelCount + "]";
	}
	
}
