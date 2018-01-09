package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static BufferedImage buffImg;

	public static BufferedImage getImage(String arg) {
		try {
			String ref = "images/" + arg + ".png";
			buffImg = ImageIO.read(new File(ref));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffImg;
	}

}
