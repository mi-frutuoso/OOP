package structuresPackage;

/**
 * 
 * Service that provides a representation of a node.
 *
 */
public interface Node {
	
	/**
	 * Computes the Alpha parameters that represent affinity between nodes.
	 */
	public void computeAlpha();
	
	/**
	 * Computes the Theta parameters that represent probabilities.
	 */
	public void computeTheta();
	
	/**
	 * Returns the value of the Alpha parameters.
	 * 
	 * @return Array of doubles containing the Alpha parameters.
	 */
	public double[] returnAlpha();
	
	/**
	 * 
	 * Returns the probability of the given node having a certain value, given the value of the class and the parent node.
	 * 
	 * @param parent_value Value for the parent node.
	 * @param node_value Value for the self node.
	 * @param class_value Value for the class.
	 * @return Probability of occurrence.
	 */
	public double returnProbability(int parent_value, int node_value, int class_value);
}
