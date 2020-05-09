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
	 * @return Array with auxiliary values for the classes (positive integers plus zero)
	 */
	public int[] translate();
	public String[] reverse(int[] predictions);
}
