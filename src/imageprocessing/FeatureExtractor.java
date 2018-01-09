package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import colorspace.CIELab;
import colorspace.HSI;

/**
 * Class that extracts color features from the processed image.
 */
public class FeatureExtractor {
	
	/**
	 * Extracts the average RED-GREEN DIFFERENCE of the RGB image.
	 *
	 * @param image the image
	 * @return Average red-green
	 */
	public static double computeMeanRG(BufferedImage image) {
		double sum = 0, ctr = 0;
		int width = image.getWidth(),
			height = image.getHeight();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				Color color = new Color(image.getRGB(j, i));
				if( color.getRed() != 0 ) {
					sum += ( color.getRed() - color.getGreen() );
					ctr++;
				}
			}

		return ctr != 0 ? sum / ctr : 0;
	}
	
	/**
	 * Compute indices.
	 *
	 * @param image the image
	 * @return the double[]
	 */
	public static double[] computeIndices(BufferedImage image) {
		double[] index = new double[2];
		double a = 0, L = 0, ctr = 0;
		int rgb, red, green, blue;
		double[] lab = new double[3];
		int width = image.getWidth(),
			height = image.getHeight();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					rgb = image.getRGB(j, i);
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					lab = CIELab.RGBtoLAB(red, green, blue);
					L += lab[0];
					a += lab[1];
					ctr++;
				}
			}

		if (ctr != 0) {
			index[0] = L / ctr;
			index[1] = a / ctr;
		}
		else {
			index[0] = 0;
			index[1] = 0;
		}
		
		return index;
	}

	/**
	 * Extracts the average HUE (from HSI) component of the image.
	 *
	 * @param image the image
	 * @return Average hue
	 */	
	public static double computeMeanHue(BufferedImage image) {
		double sum = 0;
		int rgb, red, green, blue, ctr = 0;
		int width = image.getWidth(),
			height = image.getHeight();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					rgb = image.getRGB(j, i);
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					sum += HSI.getHue(red, green, blue);
					ctr++;
				}
			}
		return ctr != 0 ? sum / ctr : 0;
	}
	
}
