package elm;

/**
 * The Class Result.
 */
public class Result {
	
	/** The expected. */
	private int[] expected;
	
	/** The actual. */
	private int[] actual;
	
	/**
	 * Instantiates a new result.
	 *
	 * @param expected the expected
	 * @param actual the actual
	 */
	public Result(int[] expected, int[] actual) {
		this.expected = expected;
		this.actual = actual;
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return expected.length;
	}
	
	/**
	 * Gets the accuracy.
	 *
	 * @return the accuracy
	 */
	public float getAccuracy() {
		return 100 * (float) getScore() / (float) size();
	}
	
	/**
	 * Gets the expected.
	 *
	 * @param index the index
	 * @return the expected
	 */
	public int getExpected(int index) {
		return expected[index];
	}
	
	/**
	 * Gets the actual.
	 *
	 * @param index the index
	 * @return the actual
	 */
	public int getActual(int index) {
		return actual[index];
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		int score = 0;
		for (int i = 0; i < size(); i++)
			if (expected[i] == actual[i])
				score++;
		
		return score;
	}

}
