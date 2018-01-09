package elm;


/**
 * The Class ActivationFunction.
 */
public class ActivationFunction {
	
	/**
	 * Gets the value.
	 * The Log-Sigmoid function is used.
	 *
	 * @param ax the ax
	 * @param b the b
	 * @return the value
	 */
	public static double getValue(double ax, double b) {
		return (double) (1 / (1 + Math.exp(-1 * (ax + b))));
	}

}
