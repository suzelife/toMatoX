package imageprocessing;

import java.awt.image.BufferedImage;

import colorspace.RGBChannel;

/**
 * The Class BackgroundRemover.
 * Removes the background of a given image
 */
public class BackgroundRemover {
	
	/** The instance. */
	private static BackgroundRemover instance = null;
	
	/** The blue channel. */
	private BufferedImage blueChannel;
	
	/** The grayscale. */
	private BufferedImage grayscale;
	
	/** The binary mask. */
	private BufferedImage binaryMask;
	
	/** The segmented. */
	private BufferedImage segmented;
	
	/**
	 * Instantiates a new background remover.
	 */
	private BackgroundRemover() { }

	/**
	 * Gets the single instance of BackgroundRemover.
	 *
	 * @return single instance of BackgroundRemover
	 */
	public static BackgroundRemover getInstance() {
		if(instance == null)
			instance = new BackgroundRemover();
		
		return instance;
	}
	
	/**
	 * Removes the background.
	 *
	 * @param image the image
	 * @return the buffered image
	 */
	public BufferedImage removeBackground(BufferedImage image) {
		blueChannel = RGBChannel.toRGBChannel(image, RGBChannel.BLUE);
		grayscale = GrayScale.toGray(blueChannel);
		binaryMask = OtsuThreshold.binarize(grayscale);
		segmented = Masking.extract(image, binaryMask);
		
		return segmented;
	}

	/**
	 * Gets the blue channel.
	 *
	 * @return the blue channel
	 */
	public BufferedImage getBlueChannel() {
		return blueChannel;
	}
	
	/**
	 * Gets the grayscale.
	 *
	 * @return the grayscale
	 */
	public BufferedImage getGrayscale() {
		return grayscale;
	}
	
	/**
	 * Gets the binary mask.
	 *
	 * @return the binary mask
	 */
	public BufferedImage getBinaryMask() {
		return binaryMask;
	}
	
	/**
	 * Gets the segmented.
	 *
	 * @return the segmented
	 */
	public BufferedImage getSegmented() {
		return segmented;
	}

}
