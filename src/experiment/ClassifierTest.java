package experiment;

import java.io.File;

import org.ejml.data.DenseMatrix64F;

import utilities.SolutionWriter;
import dataset.Data;
import dataset.DataReader;
import elm.ELM;

public class ClassifierTest {
	
	private ELM elm;
	private Data trainData;
	
	public ClassifierTest() {
		loadTrainingData();
		for (int i = 0; i < 30; i++) {
			System.out.println("Training the network.");
			trainNetwork(trainData, 420);
			System.out.println("Saving the classifier.");
			saveClassifier("" + (i + 1));
		}
	}
	
	/*private void loadTrainingData(String filename) {
		File file = new File("" + "D:/kamatisan/Data/random/" + filename + "_train.data"); 
		DataReader dl = new DataReader(null, file);
		trainData = dl.read();
	}*/
	
	private void loadTrainingData() {
		File file = new File("" + "D:/kamatisan/Data/fixed/train.data"); 
		DataReader dl = new DataReader(null, file);
		trainData = dl.read();
	}
	
	private void trainNetwork(Data trainingData, int hiddenNodes) {
		elm = new ELM();
		elm.setLambda(1);
		
		DenseMatrix64F input = new DenseMatrix64F(trainingData.getInputVector());
		DenseMatrix64F output = new DenseMatrix64F(trainingData.getOutputVector());
		elm.train(input, output, hiddenNodes);
	}
	
	private void saveClassifier(String filename) {
	    File file = new File("D:/kamatisan/Classifiers v2/fixed-420/" + filename + ".tx");
	    SolutionWriter fileSaver = new SolutionWriter(file);
		fileSaver.saveFile(elm.getWeights(), elm.getInputHiddenSeparator(), elm.getHiddenOutputSeparator());
	}
	
	public static void main(String[] args) {
		new ClassifierTest();
	}

}
