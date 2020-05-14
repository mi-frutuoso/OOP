package translationPackage;
/**
 * For generalisation purposes, classes will be converted into arrays of integers. 
 * This will help data handling for any algorithm.
 * This interface offers two services:
 * 	- 'translate': classes translation into integers;
 * 	- 'reverse': returns string array containing original classification values after performing inverse translation. 
 *
 */
public interface Translator {
	/**
	 * 
	 * @param classes Array of the actual and original classes that needs to be translated into integers.
	 * @return Array with auxiliary values for the classes (positive integers plus zero)
	 */
	public int[] translate(String[] classes);
	/**
	 * 
	 * @param predictions Array with auxiliary values for the classes (positive integers plus zero) that needs to be re-translated into its original labels.
	 * @return Array with true values for the classes.
	 */
	public String[] reverse(int[] predictions);
}
