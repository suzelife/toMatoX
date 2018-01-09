package dataset;

import imageprocessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import ui.components.ProgressPane;
import utilities.Debugger;
import utilities.FileHelper;

/**
 * DataReader reads data from a file.
 * The file is specified in the parameter of the constructor.
 */
public class DataReader extends FileHelper {
	
	/** The input vector. */
	private double[][] inputVector;
	
	/** The output vector. */
	private double[][] outputVector;
	
	/** The file_list. */
	private ArrayList<String> file_list = new ArrayList<String>();
	
	/** The image_list. */
	private ArrayList<BufferedImage> image_list = new ArrayList<BufferedImage>();
	
	/** The class_list. */
	private ArrayList<Integer> class_list = new ArrayList<Integer>();
	
	/** The im processor. */
	private ImageProcessor imProcessor;
	
	/** The data file. */
	private File dataFile;
	
	/** The progress pane. */
	private ProgressPane progressPane;
	
	/** The buffered reader. */
	private BufferedReader bufferedReader;
	
	/**
	 * Instantiates a new data reader.
	 *
	 * @param progressPane the progress pane
	 * @param dataFile the data file
	 */
	public DataReader(ProgressPane progressPane, File dataFile) {
		this.dataFile = dataFile;
		this.progressPane = progressPane;
		imProcessor = new ImageProcessor();
	}

	/**
	 * Reads .data file containing absolute paths of images and extracts features from each image
	 * 
	 * @return Data 
	 */
	public Data read() {
		int count = FileHelper.countFiles(dataFile);
		progressPane.reset(count * 2);
		String fname = "";
		int classNumber;
		File file = null;
    	BufferedImage image = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(dataFile));
        	
			while((fname = bufferedReader.readLine()) != null) {
				file = new File(fname);
        		image = ImageIO.read(file);
        		//image = iProcessor.resizeImage(image, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
        		classNumber =  Integer.parseInt(file.getParentFile().getName());
        		
        		image_list.add(image);
	        	class_list.add(classNumber);
        		file_list.add(fname);
        		progressPane.incrementBar();
			}			
		} catch (IIOException e) {
			Debugger.printError("Can't read file: " + fname);
		} catch (FileNotFoundException e) {
			Debugger.printError("File not found: " + fname);
		} catch (IOException e) {			
			//
		} catch (NumberFormatException e) {
			Debugger.printError("Can't convert to integer: " + file.getParentFile().getName());	
		}
		
		inputVector = imProcessor.createInputVectorArray(image_list, progressPane);
    	outputVector = imProcessor.createOutputVector(class_list);

		return new Data(file_list, inputVector, outputVector);
	}
	
}
