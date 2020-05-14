package structuresPackage;

/**
 * 
 * Generates a tree (graph) with accessible nodes.
 *
 */
public interface Tree extends Graph{
	/**
	 * Predicts the most probable class of a set in the input data, by using the data stored in the nodes. 
	 * @param var Input data from which we pretend to obtain class predictions.
	 */
	public void predict(int[][] var);
	
	/**
	 * Getter of the classifications predicted.
	 * @return
	 */
	public int[] returnClassification();
}
