package classifierPackage;

/**
 * 
 * Service that provides the classifier. It contains 3 methods:<br>
 * - 'train': trains the classifier with the train dataset;<br>
 * - 'predict' : predicts the class values for a specific test dataset;<br>
 * - 'results' : returns the results obtained in 'predict'.
 *
 */
public interface Classifier {
	
	public void train();
	
	public void predict();
	
	/**
	 * 
	 * @return Array of strings with the predicted values.
	 */
	public String[] results();
}
