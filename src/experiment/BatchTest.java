package experiment;

import java.io.File;

import utilities.OutputLayerHelper;
import utilities.SolutionReader;
import dataset.Data;
import dataset.DataReader;
import elm.Classifier;
import elm.Result;

public class BatchTest {
	
	private Data trainData;
	private double[] solution = null;
	private Classifier classifier;
	private Result result;
	
	public BatchTest() {
		loadTrainingData();
		for (int i = 0; i < 30; i++) {
			selectClassifier("" + (i + 1));
			classify();
		}
	}
	
	/*private void loadTrainingData(String filename) {
		File file = new File("" + "D:/kamatisan/Data/random/" + filename + "_train.data"); 
		DataReader dl = new DataReader(null, file);
		trainData = dl.read();
	}*/
	
	private void loadTrainingData() {
		File file = new File("" + "D:/kamatisan/Data/fixed/test.data"); 
		DataReader dl = new DataReader(null, file);
		trainData = dl.read();
	}
	
	private void selectClassifier(String filename) {
		solution = SolutionReader.read(new File("D:/kamatisan/Classifiers v2/fixed-420/" + filename + ".tx"));
	}
	
	private void classify() {
		classifier = new Classifier(
						solution, 
						SolutionReader.getHiddenOutputSeparator() - SolutionReader.getInputHiddenSeparator() 
					);
		result = classifier.testBatch(trainData.getInputVector(), OutputLayerHelper.normalize(trainData.getOutputVector()));
		
		float acc = result.getAccuracy(); 
		System.out.println(acc);
	}

	public static void main(String[] args) {
		new BatchTest();
	}
	
}