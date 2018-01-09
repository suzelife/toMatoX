package colorspace;

import java.awt.image.BufferedImage;

public class RGBChannel {
	
	public static final int RED = 1, GREEN = 2, BLUE = 3;
	
	/**
	 * Extract R/G/B Channel
	 * @param image BufferedImage
	 * @param channel int
	 * @return BufferedImage
	 */
	public static BufferedImage toRGBChannel(BufferedImage image, int channel) {
		int rgb, width = image.getWidth(), height = image.getHeight();
		BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int mask = getChannelMask(channel);
		
		for(int h = 0; h < height; h++) 
			for(int w = 0; w < width; w++) {
				rgb = image.getRGB(w, h);
				
				rgb &= mask;
				
				convertedImage.setRGB(w, h, rgb);
			}
		
		return convertedImage;
	}
	    
	private static int getChannelMask(int channel) {
		if( channel == RED )
			return 0x00FF0000;
		else if( channel == BLUE )
			return 0x000000FF;
		return 0x0000FF00;  //green
	}

}
