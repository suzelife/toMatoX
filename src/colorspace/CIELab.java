package colorspace;

public class CIELab {
	
	public static double[][] M   = {{0.4124, 0.3576,  0.1805},
            {0.2126, 0.7152,  0.0722},
            {0.0193, 0.1192,  0.9505}};
	
	public static double[] D50 = {96.4212, 100.0, 82.5188};
    public static double[] D55 = {95.6797, 100.0, 92.1481};
    public static double[] D65 = {95.0429, 100.0, 108.8900};
    public static double[] D75 = {94.9722, 100.0, 122.6394};
    
    private static double[] whitePoint = D65;
	
	public static double[] RGBtoLAB(int R, int G, int B) {
		return XYZtoLAB(RGBtoXYZ(R, G, B));
	}
	 
	public static double[] RGBtoXYZ(int R, int G, int B) {
		double[] result = new double[3];
	
		// convert 0..255 into 0..1
		double r = R / 255.0;
	    double g = G / 255.0;
	    double b = B / 255.0;
	
	    //assume sRGB
	    if (r <= 0.04045) {
	    	r = r / 12.92;
	    }
	    else {
	    	r = Math.pow(((r + 0.055) / 1.055), 2.4);
	    }
	    if (g <= 0.04045) {
	    	g = g / 12.92;
	    }
	    else {
	    	g = Math.pow(((g + 0.055) / 1.055), 2.4);
	    }
	    if (b <= 0.04045) {
	    	b = b / 12.92;
	    }
	    else {
	    	b = Math.pow(((b + 0.055) / 1.055), 2.4);
	    }
	
	    r *= 100.0;
	    g *= 100.0;
	    b *= 100.0;
	
	    // [X Y Z] = [r g b][M]
	    result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
	    result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
	    result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

	    return result;
	}
	 
	public static double[] XYZtoLAB(double[] XYZ) {
		return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
	}
	 
	public static double[] XYZtoLAB(double X, double Y, double Z) {
		double x = X / whitePoint[0];
	    double y = Y / whitePoint[1];
	    double z = Z / whitePoint[2];

	    if (x > 0.008856) {
	    	x = Math.pow(x, 1.0 / 3.0);
	    }
	    else {
	    	x = (7.787 * x) + (16.0 / 116.0);
	    }
	    if (y > 0.008856) {
	    	y = Math.pow(y, 1.0 / 3.0);
	    }
	    else {
	    	y = (7.787 * y) + (16.0 / 116.0);
	    }
	    if (z > 0.008856) {
	    	z = Math.pow(z, 1.0 / 3.0);
	    }
	    else {
	    	z = (7.787 * z) + (16.0 / 116.0);
	    }

	    double[] result = new double[3];

	    result[0] = (116.0 * y) - 16.0;
	    result[1] = 500.0 * (x - y);
	    result[2] = 200.0 * (y - z);

	    return result;      
	}
	
}
