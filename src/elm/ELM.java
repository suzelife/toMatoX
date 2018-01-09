package elm;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

import ui.PanelTrain;
import ui.components.ProgressPane;

/**
 * The Class ELM.
 * The implementation of Extreme Learning Machines
 */
public class ELM {
	
	/** The matrices used in ELM. */
	private DenseMatrix64F input, tOutput, inputWght, outputWght;
	
	/** The bias. */
	private double[] bias;
	
	/** The elapsed time. */
	private double startTime, elapsedTime;
	
	/** The num of training samples. */
	private int numOfHiddenNodes;
	
	/** The num of input nodes. */
	private int numOfInputNodes = 4;
	
	/** The num of output nodes. */
	private int numOfOutputNodes = 6;
	
	/** The num of training samples. */
	private int numOfTrainingSamples;
	
	/** The separators for saving the classifier. */
	private int inputHiddenSeparator, hiddenOutputSeparator;
	
	/** The is basic. */
	private boolean isBasic = true;
	
	/** The lambda. */
	private float lambda;

	/** The progress pane. */
	private ProgressPane progressPane;
	
	/** The parent. */
	private Object parent;

	/**
	 * Instantiates a new elm.
	 */
	public ELM() {}
	
	/**
	 * Sets the progress pane.
	 *
	 * @param progressPane the new progress pane
	 */
	public void setProgressPane(ProgressPane progressPane) {
		this.progressPane = progressPane;
	}
	
	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(Object parent) {
		this.parent = parent;
	}
	
	/**
	 * Sets the lambda.
	 *
	 * @param lambda the new lambda
	 */
	public void setLambda(float lambda) {
		this.lambda = lambda;
		isBasic = false;
	}
	
	/**
	 * Train.
	 *
	 * @param input the input
	 * @param tOutput the t output
	 * @param numOfHiddenNodes the num of hidden nodes
	 */
	public void train(DenseMatrix64F input, DenseMatrix64F tOutput, int numOfHiddenNodes) {
		progressPane.reset(3);
		startTime = System.currentTimeMillis();
		
		this.input = input;
		this.tOutput = tOutput;
		this.numOfHiddenNodes = numOfHiddenNodes;
		
		numOfInputNodes = this.input.numCols;
		numOfOutputNodes = this.tOutput.numCols;
		numOfTrainingSamples = this.input.numRows;
		
		generateHiddenLayerParam();
		calculateOutputWeight();
		
		elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
		if (parent instanceof PanelTrain) {
			((PanelTrain) parent).displayTrainingTime(elapsedTime);
		}
	}
	
	/**
	 * Generate hidden layer param.
	 */
	private void generateHiddenLayerParam() {
		inputWght = new DenseMatrix64F(numOfHiddenNodes, numOfInputNodes);
		bias = new double[numOfHiddenNodes];
		
		//randomize values
		int row, col;
		for (row = 0; row < inputWght.numRows; row++) {
			for (col = 0; col < inputWght.numCols; col++) {
				inputWght.set(row, col, random());
			}
			bias[row] = random();
		}
		
		//test purpose only
		/*System.out.println("---a---");
		inputWght.print();
		System.out.println("---bias---");
		for (row = 0; row < inputWght.numRows; row++) {
			System.out.println("bias: " + bias[row]);
		}*/
		
		progressPane.incrementBar();
	}
	
	/**
	 * Random.
	 *
	 * @return the double
	 */
	private double random() {
		int maxWeight = 1;
		int minWeight = -1;

		return (minWeight + (Math.random() * (maxWeight - minWeight)));
	}
	
	/**
	 * Calculate output weight.
	 */
	private void calculateOutputWeight() {
		outputWght = new DenseMatrix64F(numOfHiddenNodes, numOfOutputNodes);
		
		if (isBasic) {
			DenseMatrix64F inverseH = new DenseMatrix64F(numOfHiddenNodes, numOfTrainingSamples);
			//Moore-Penrose pseudoinverse
			CommonOps.pinv(generateHiddenLayerOutputMatrix(), inverseH);
			/*System.out.println("---Inverse H---");
			inverseH.print();*/
			//calculate output weight
			CommonOps.mult(inverseH, tOutput, outputWght);
			
			//test purpose only
			/*System.out.println("---Target Output---");
			tOutput.print();
			System.out.println("---B---");
			outputWght.print();*/
		}
		else {
			DenseMatrix64F h_trans = null;
			DenseMatrix64F h = generateHiddenLayerOutputMatrix();
			h_trans = CommonOps.transpose(h, null);
			DenseMatrix64F I = CommonOps.identity(h.getNumRows());
			CommonOps.divide(lambda, I);
			DenseMatrix64F h_hTrans = new DenseMatrix64F(h.getNumRows(), h_trans.getNumCols());
			CommonOps.multOuter(h, h_hTrans);
			CommonOps.addEquals(h_hTrans, I);
			CommonOps.invert(h_hTrans);
			DenseMatrix64F tmp = new DenseMatrix64F(h_trans.getNumRows(), h_hTrans.getNumCols());
			CommonOps.mult(h_trans, h_hTrans, tmp);
			CommonOps.mult(tmp, tOutput, outputWght);
		}
		
		progressPane.incrementBar();
	}
	
	/**
	 * Generate hidden layer output matrix.
	 *
	 * @return the dense matrix64 f
	 */
	private DenseMatrix64F generateHiddenLayerOutputMatrix() {
		DenseMatrix64F H = new DenseMatrix64F(numOfTrainingSamples, numOfHiddenNodes);
		
		int row, col, i;
		double ax;
		
		for (row = 0; row < numOfTrainingSamples; row++) {
			for (col = 0; col < numOfHiddenNodes; col++) {
				ax = 0;
				for (i = 0; i < numOfInputNodes; i++) {
					ax += input.get(row, i) * inputWght.get(col, i);
				}
				
				H.set(row, col, ActivationFunction.getValue(ax, bias[col]));
			}
		}
		
		//test purpose only
		/*System.out.println("---H---");
		H.print();*/
		
		progressPane.incrementBar();
		
		return H;
	}
	
	/**
	 * Calculate output function value.
	 *
	 * @param input the input
	 * @return the double[]
	 */
	public double[] calculateOutputFunctionValue(double[] input) {
		DenseMatrix64F hiddenOutput = new DenseMatrix64F(1, numOfHiddenNodes);
		DenseMatrix64F output = new DenseMatrix64F(1, numOfOutputNodes);
		
		int row, col;
		double sum;
		
		for (row = 0; row < numOfHiddenNodes; row++) {
			sum = 0;
			for (col = 0; col < input.length; col++) {
				sum += inputWght.get(row, col) * input[col];
			}
			
			hiddenOutput.set(0, row, ActivationFunction.getValue(sum, bias[row]));
		}
		
		//test purpose only
		/*System.out.println("---Hidden Output---");
		hiddenOutput.print();*/
		
		CommonOps.mult(hiddenOutput, outputWght, output);
		
		//test purpose only
		/*System.out.println("---Output---");
		output.print();*/
		
		double[] outputArray = new double[output.numCols];
		for (int i = 0; i < outputArray.length; i++) {
			outputArray[i] = output.get(0, i);
		}
			
		//test purpose only
		/*System.out.println("---Normalized Output---");
		System.out.println(normalizeOutput(outputArray));*/
		
		return outputArray;
	}
	
	//test purpose only
	/*private int normalizeOutput(double[] output) {
		int maxIndex = 0, i;
		
		for(i = 1; i < output.length; i++)
			if(output[i] > output[maxIndex])
				maxIndex = i;
		
		return maxIndex;
	}*/
	
	/**
	 * Gets the weights.
	 *
	 * @return the weights
	 */
	public double[] getWeights() {
		double[] weights = new double[
			                              (numOfHiddenNodes * numOfInputNodes)+ 
			                              numOfHiddenNodes + 
			                              (numOfHiddenNodes * numOfOutputNodes)
		                              ];
		int ctr = 0, row, col;
		
		for (row = 0; row < inputWght.numRows; row++) {
			for (col = 0; col < inputWght.numCols; col++) {
				weights[ctr] = inputWght.get(row, col);
				ctr++;
			}
		}
		
		inputHiddenSeparator = ctr;
		
		for (row = 0; row < bias.length; row++, ctr++) {
			weights[ctr] = bias[row];
		}
			
		hiddenOutputSeparator = ctr;
		
		for (row = 0; row < outputWght.numRows; row++) {
			for (col = 0; col < outputWght.numCols; col++) {
				weights[ctr] = outputWght.get(row, col);
				ctr++;
			}
		}
		
		return weights;
	}
	
	/**
	 * Gets the input hidden separator.
	 *
	 * @return the input hidden separator
	 */
	public int getInputHiddenSeparator() {
		return inputHiddenSeparator;
	}
	
	/**
	 * Gets the hidden output separator.
	 *
	 * @return the hidden output separator
	 */
	public int getHiddenOutputSeparator() {
		return hiddenOutputSeparator;
	}
	
	/**
	 * Sets the input weights.
	 *
	 * @param inputWeight the new input weights
	 */
	public void setInputWeights(DenseMatrix64F inputWeight) {
		inputWght = inputWeight;
	}
	
	/**
	 * Sets the output weights.
	 *
	 * @param outputWeight the new output weights
	 */
	public void setOutputWeights(DenseMatrix64F outputWeight) {
		outputWght = outputWeight;
	}
	
	/**
	 * Sets the bias.
	 *
	 * @param bias the new bias
	 */
	public void setBias(double[] bias) {
		this.bias = bias;
	}
	
	/**
	 * Sets the number of hidden nodes.
	 *
	 * @param numOfHiddenNodes the new number of hidden nodes
	 */
	public void setNumberOfHiddenNodes(int numOfHiddenNodes) {
		this.numOfHiddenNodes = numOfHiddenNodes;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		double[][] data = { { 40.52, 46.48, 6.16, 118.97 } };
		DenseMatrix64F input = new DenseMatrix64F(data);
		
		double[][] target = { { 0, 0, 0, 0, 0, 1 } };
		DenseMatrix64F output = new DenseMatrix64F(target);
		
		ELM elm = new ELM();
		elm.train(input, output, 5);
		
		elm.calculateOutputFunctionValue( data[0] );
		
		
		/*ELM_macaraig elm_macaraig = new ELM_macaraig();
		elm_macaraig.train(input, output, 4);
		double[] test = { 1, 0 };
		elm_macaraig.test(test);*/
	}
	
}
