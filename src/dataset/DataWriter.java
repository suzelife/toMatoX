package dataset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * DataWriter writes data to a file.
 * The filename of the file is specified in the parameter of the constructor.
 */
public class DataWriter {
	
	/** The data file. */
	private File dataFile;
	
	/**
	 * Instantiates a new data writer.
	 *
	 * @param filename the filename
	 */
	public DataWriter(String filename) {
		dataFile = new File(filename);
	}
	
	/**
	 * Writes data to a file.
	 * @param files ArrayList containing the data to be written.
	 * @return true if successful, else false
	 */
	public boolean write(ArrayList<String> files) {
		FileWriter fileWriter;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = new FileWriter(dataFile);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(String filename: files) {
				bufferedWriter.write(filename);
				bufferedWriter.newLine();
			}	
		} catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	        
	        return false;
	    } catch (IOException e) {
			e.printStackTrace();
			
			return false;
		} finally {
	        try {
	            if (bufferedWriter != null) {
	                bufferedWriter.flush();
	                bufferedWriter.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            
	            return false;
	        }
	    }
		
		return true;
	}
	
}
