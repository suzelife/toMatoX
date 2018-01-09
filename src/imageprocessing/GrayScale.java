package imageprocessing;

import java.awt.image.BufferedImage;

import colorspace.RGBManipulator;

/**
 * The Class GrayScale.
 */
public class GrayScale extends RGBManipulator {

	/**
	 * To gray.
	 *
	 * @param image the image
	 * @return the buffered image
	 */
	public static BufferedImage toGray(BufferedImage image) {
	    int width = image.getWidth(), height = image.getHeight();
	        
        BufferedImage grayImage = new BufferedImage(width, height, image.getType());
        
        int h, w, rgb, gray;
		for (h = 0; h < height; h++) {
			for (w = 0; w < width; w++) {
				rgb = image.getRGB(w, h);
				gray = getGrayValue((rgb & 0xFF0000) >> 16, (rgb & 0xFF00) >> 8, (rgb & 0xFF));
				grayImage.setRGB(w, h, mixColor(gray, gray, gray));
			}
		}
        
        return grayImage;
	}
	
	/**
	 * Gets the gray value.
	 *
	 * @param r the r
	 * @param g the g
	 * @param b the b
	 * @return the gray value
	 */
	private static int getGrayValue(int r, int g, int b) {
		return (int) Math.round((r * 0.3) + (g * 0.59) + (b * 0.11));
	}
	
}
