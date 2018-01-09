package imageprocessing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ui.components.ProgressPane;
import utilities.TomatoXConstants;

/**
 * The Class ImageProcessor.
 */
public class ImageProcessor {
	
	/** The b remover. */
	private BackgroundRemover bRemover = null;
	
	/** The cropped. */
	private BufferedImage cropped;
	
	/**
	 * Instantiates a new image processor.
	 */
	public ImageProcessor() {
		bRemover = BackgroundRemover.getInstance();
	}
	
	/**
	 * Process.
	 *
	 * @param image the image
	 * @param width the width
	 * @param height the height
	 * @return the buffered image
	 */
	public BufferedImage process(BufferedImage image, int width, int height) {
		BufferedImage resized200 = resizeImage(image, width, height);
		BufferedImage cropped = cropImage(removeBackground(resized200));
		BufferedImage resized64 = resizeImage(cropped, 64, 64);
		
		return resized64;
	}
	
	/**
	 * Resize image.
	 *
	 * @param image the image
	 * @param scaledWidth the scaled width
	 * @param scaledHeight the scaled height
	 * @return the buffered image
	 */
	public BufferedImage resizeImage(BufferedImage image, int scaledWidth, int scaledHeight) {
		BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
		g.setComposite(AlphaComposite.Src);
		g.dispose();
		
		return resizedImage;
	}
	
	/**
	 * Removes the background.
	 *
	 * @param image the image
	 * @return the buffered image
	 */
	public BufferedImage removeBackground(BufferedImage image) {
		return bRemover.removeBackground(image);
	}
	
	/**
	 * Crop image.
	 *
	 * @param image the image
	 * @return the buffered image
	 */
	public BufferedImage cropImage(BufferedImage image) {
		cropped = Cropper.crop(image);
		return cropped;
	}
	
	/**
	 * Gets the cropped.
	 *
	 * @return the cropped
	 */
	public BufferedImage getCropped() {
		return cropped;
	}
	
	/**
	 * Gets the blue channel.
	 *
	 * @return the blue channel
	 */
	public BufferedImage getBlueChannel() {
		return bRemover.getBlueChannel();
	}

	/**
	 * Gets the grayscale.
	 *
	 * @return the grayscale
	 */
	public BufferedImage getGrayscale() {
		return bRemover.getGrayscale();
	}
	
	/**
	 * Gets the binary mask.
	 *
	 * @return the binary mask
	 */
	public BufferedImage getBinaryMask() {
		return bRemover.getBinaryMask();
	}

	/**
	 * Gets the segmented.
	 *
	 * @return the segmented
	 */
	public BufferedImage getSegmented() {
		return bRemover.getSegmented();
	}
	
	/**
	 * creates a vector of features for each image.
	 *
	 * @param inputData the input_data
	 * @param progressPane the progress pane
	 * @return the double[][]
	 */
	public double[][] createInputVectorArray(ArrayList<BufferedImage> inputData, ProgressPane progressPane) {
		int numOfSamples = inputData.size();
		double[][] inputArray = new double[numOfSamples][TomatoXConstants.NUM_INPUTS];
		
		for(int i = 0; i < numOfSamples; i++) {
			BufferedImage processed_image = process(inputData.get(i), TomatoXConstants.SCALE_SIZE, TomatoXConstants.SCALE_SIZE);
			inputArray[i] = getFeatures(processed_image);
			progressPane.incrementBar();
		}
	
		return inputArray;
	}
	
	/**
	 * creates a vector and sets the corresponding class index to 1, other to 0.
	 *
	 * @param outputData the output_data
	 * @return the double[][]
	 */
	public double[][] createOutputVector(ArrayList<Integer> outputData) {
		int patternSize = outputData.size();
		double[][] outputArray = new double[patternSize][6];
		
		// set the output node value of the expected class to 1
		for( int i = 0; i < patternSize; i++ ) 
			outputArray[i][outputData.get(i)-1] = 1.0;
		
		return outputArray;
	}
	
	/**
	 * extracts color features of an image.
	 *
	 * @param processedImage the processed image
	 * @return feature vector
	 */
	public double[] getFeatures(BufferedImage processedImage) {
		double[] features = new double[TomatoXConstants.NUM_INPUTS];
		
		//testing purpose only
		/*features[0] = FeatureExtractor.computeMeanRed(processedImage);
		features[1] = FeatureExtractor.computeMeanGreen(processedImage);
		features[2] = FeatureExtractor.computeMeanRG(processedImage);
		features[3] = FeatureExtractor.computeMeanHue(processedImage);
		features[4] = FeatureExtractor.computeMeanA(processedImage);
		
		//1
		features[0] = FeatureExtractor.computeMeanA(processedImage);
		
		//2
		features[0] = FeatureExtractor.computeMeanRG(processedImage);
		features[1] = FeatureExtractor.computeMeanHue(processedImage);
		
		//3
		features[0] = FeatureExtractor.computeMeanRed(processedImage);
		features[1] = FeatureExtractor.computeMeanGreen(processedImage);
		features[2] = FeatureExtractor.computeMeanBlue(processedImage);
		
		//4
		features[1] = FeatureExtractor.computeMeanRG(processedImage);
		features[2] = FeatureExtractor.computeMeanHue(processedImage);
		features[0] = FeatureExtractor.computeMeanA(processedImage);
		
		//5
		features[0] = FeatureExtractor.computeMeanRed(processedImage);
		features[1] = FeatureExtractor.computeMeanGreen(processedImage);
		features[2] = FeatureExtractor.computeMeanA(processedImage);*/
		
		double[] index = FeatureExtractor.computeIndices(processedImage);
		features[0] = index[0];
		features[1] = index[1];
		features[2] = FeatureExtractor.computeMeanHue(processedImage);
		features[3] = FeatureExtractor.computeMeanRG(processedImage);
		
		return features;
	}
	
}
