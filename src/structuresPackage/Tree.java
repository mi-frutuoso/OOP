package structuresPackage;

/**
 * 
 * Generates a tree (graph) with accessible nodes.
 *
 */
public interface Tree extends Graph{
	/**
	 * Predicts the most probable class of a sets in the input data, by using the data stored in the nodes. 
	 * @param var Input data from which we pretend to obtain class predictions.
	 */
	public void predict(int[][] var);
	
	/**
	 * 
	 * @return
	 */
	public int[] returnClassification();
}
