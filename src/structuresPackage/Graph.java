package structuresPackage;

/**
 * 
 * Generates a graph with accessible nodes.
 * 
 * */
public interface Graph {
	/**
	 *  Shapes and generates the graph. Any structure might be used graph, a tree or others.
	 */
	public void makeStruct();
	
	/**
	 * Getter to the nodes created within the class.
	 * @return An array of objects Node.
	 */
	public Node[] returnNodes();
}
