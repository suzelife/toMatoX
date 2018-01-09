package dataset;

import java.io.File;
import java.util.ArrayList;

/**
 * Data is a container of a dataset.
 */
public class Data {
	
	/** The input vector. */
	private double[][] inputVector;
	
	/** The output vector. */
	private double[][] outputVector;
	
	/** The filename. */
	private ArrayList<String> filename;
	
	/**
	 * Instantiates a new data.
	 *
	 * @param filename the filename
	 * @param inputVector the input vector
	 * @param outputVector the output vector
	 */
	public Data(ArrayList<String> filename, double[][] inputVector, double[][] outputVector) {
		this.filename = filename;
		this.inputVector = inputVector;
		this.outputVector = outputVector;
	}
	
	/**
	 * Gets the input vector.
	 *
	 * @return M x N array representing M samples and N features for each sample.
	 */
	public double[][] getInputVector() {
		return inputVector;
	}
	
	/**
	 * sample row = {0, 0, 0, 0, 0, 1} corresponding to red stage.
	 *
	 * @return M x N array representing M samples and N output vector
	 */
	public double[][] getOutputVector() {
		return outputVector;
	}

	/**
	 * Gets the filename.
	 *
	 * @param index the index
	 * @return the filename
	 */
	public String getFilename(int index) {
		return filename.get(index);
	}
	
	/**
	 * Gets the filename.
	 *
	 * @param imageName the name of the image
	 * @return the filename
	 */
	public String getFilename(String imageName) {
		File file = null;
		for (String name : filename) {
			file = null;
			file = new File(name);
			if (file.getName().equals(imageName)) {
				return name;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return filename.size();
	}
	
}
