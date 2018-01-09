package elm;

import org.ejml.data.DenseMatrix64F;

import utilities.TomatoXConstants;

/**
 * The Class Classifier.
 */
public class Classifier {	
	
	/** The bias. */
	private double[] bias;
	
	/** The output weight. */
	private double[][] inputWeight, outputWeight;
	
	/** The elm. */
	private ELM elm;
	
	/**
	 * Instantiates a new classifier.
	 *
	 * @param solution the solution
	 * @param numOfHiddenNodes the num of hidden nodes
	 */
	public Classifier(double[] solution, int numOfHiddenNodes) {		
		inputWeight = new double[numOfHiddenNodes][TomatoXConstants.NUM_INPUTS]; 
		outputWeight = new double[numOfHiddenNodes][TomatoXConstants.NUM_OUTPUTS];
		bias = new double[numOfHiddenNodes];
		
		int ctr = 0, row, col;
		for (row = 0; row < numOfHiddenNodes; row++)
			for (col = 0; col < inputWeight[0].length; col++) {
				inputWeight[row][col] = solution[ctr];
				ctr++;
			}
		
		for (row = 0; row < numOfHiddenNodes; row++, ctr++)
			bias[row] = solution[ctr];
		
		for (row = 0; row < numOfHiddenNodes; row++)
			for (col = 0; col < 6; col++) {
				outputWeight[row][col] = solution[ctr];
				ctr++;
			}
		
		elm = new ELM();
		elm.setNumberOfHiddenNodes(numOfHiddenNodes);
		elm.setInputWeights(new DenseMatrix64F(inputWeight));
		elm.setBias(bias);
		elm.setOutputWeights(new DenseMatrix64F(outputWeight));
	}
	
	/**
	 * Test_batch.
	 *
	 * @param test_input the test_input
	 * @param test_expected the test_expected
	 * @return the result
	 */
	public Result testBatch(double[][] test_input, int[] test_expected) {
		int[] actual = new int[test_expected.length];

		for( int i = 0; i < test_input.length; i++ ) 
			actual[i] = classify( test_input[i] );
		
		return new Result(test_expected, actual);
	}
	
	/**
	 * Classify.
	 *
	 * @param input the input
	 * @return the int
	 */
	public int classify(double[] input) {
		double[] output = elm.calculateOutputFunctionValue(input);
		
		return normalizeOutput(output);
	}
	
	/**
	 * Normalize output.
	 *
	 * @param output the output
	 * @return the int
	 */
	private int normalizeOutput(double[] output) {
		int maxIndex = 0, i;
		
		for(i = 1; i < output.length; i++)
			if(output[i] > output[maxIndex])
				maxIndex = i;
		
		return maxIndex;
	}
}
