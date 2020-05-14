package filePackage;

/**
 * 
 * Service dedicated to handle files to be processed.
 *
 */
public interface FileInterface {
	/**
	 * Operation that allows to read an input file to a data structure.
	 */
	public void readFile();
	/**
	 * Operation that allows to read an input file and select a specific fraction into a string array.
	 * @return Array of strings containing a specific fraction of the file.
	 */
	public String[] readClasses();
}
