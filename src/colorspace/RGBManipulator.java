package colorspace;

public abstract class RGBManipulator {
	
	protected static int mixColor(int red, int green, int blue) {
		return red<<16|green<<8|blue;
	}
	
}
