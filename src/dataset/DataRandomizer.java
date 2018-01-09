package dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Randomizes the  dataset; 70% for training and 30% for testing.
 */
public class DataRandomizer {
	
	/** The data file. */
	private File dataFile;
	
	/** The percentage. */
	private float percentage = 0.7f;
	
	/** The train_set. */
	private ArrayList<String> train_set = new ArrayList<String>();
	
	/** The test_set. */
	private ArrayList<String> test_set = new ArrayList<String>();
	
	/**
	 * Instantiates a new data randomizer.
	 *
	 * @param dataFile the data file
	 */
	public DataRandomizer(File dataFile) {
		this.dataFile = dataFile;
	}
	
	/**
	 * Gets the train set.
	 *
	 * @return the train set
	 */
	public ArrayList<String> getTrainSet() {
		return train_set;
	}
	
	/**
	 * Gets the test set.
	 *
	 * @return the test set
	 */
	public ArrayList<String> getTestSet() {
		return test_set;
	}
	
	/**
	 * Randomize.
	 *
	 * @return true, if successful
	 */
	public boolean randomize() {
		boolean success = getAbsolutePaths(dataFile);
		return success;
	}

	/**
	 * Gets the absolute paths.
	 *
	 * @param dataFile the data file
	 * @return the absolute paths
	 */
	private boolean getAbsolutePaths(File dataFile) {
		for (File classFile: dataFile.listFiles()) {
	        if (classFile.isDirectory()) { 
	        	File[] data = classFile.listFiles();
	        	int[] trainIndices = randomizeIndices(data.length);
	        	
	        	for( int i = 0; i < data.length; i++ ) {
	        		if(arrayContains(trainIndices, i)) 
	        			train_set.add(data[i].getAbsolutePath()); // add file to train data
	        		else 
	        			test_set.add(data[i].getAbsolutePath()); // add file to test data
	        	}
	        	
	        }
	        else {
				return false;
	        }
		}
		
		return true;
	}
	
	/**
	 * Array contains.
	 *
	 * @param array the array
	 * @param number the number
	 * @return true, if successful
	 */
	private boolean arrayContains(int[] array, int number) {
		for( int i = 0; i < array.length; i++ )
			if( array[i] == number )
				return true;
		
		return false;
	}
	
	/**
	 * Randomize indices.
	 *
	 * @param total the total
	 * @return the int[]
	 */
	private int[] randomizeIndices(int total) {
		int count = (int) (total*percentage);
		int[] indices = new int[count];
		Random rand = new Random();
		int index = 0;
	
		for( int ctr = 0; ctr < count; ctr++ ) {
			do {
				index = rand.nextInt(total);
			} while( hasBeenTaken(indices, index, ctr) );
			indices[ctr] = index;
		}
		
		return indices;
	}
	
	/**
	 * Checks for been taken.
	 *
	 * @param indices the indices
	 * @param index the index
	 * @param ctr the ctr
	 * @return true, if successful
	 */
	private boolean hasBeenTaken(int[] indices, int index, int ctr) {
		for( int i = 0; i < ctr; i++ )
			if( indices[i] == index )
				return true;
		
		return false;
	
	}
	
}