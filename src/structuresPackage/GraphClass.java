package structuresPackage;

/**
 * Generates a graph which nodes are accessible and implement two score models, MDL and LL.
 *
 */
public class GraphClass implements Graph{
	
	Node[] nodes;
	int[][] matrix;
	int[] max_values;
	String score;
	
	/**
	 * Constructs a graph
	 * 
	 * @param matrix A matrix containing training data, with features and classes.
	 * @param max_values Contains maximum of each set of features.
	 * @param score The score associated with each prediction.
	 */
	public GraphClass(int[][] matrix, int[] max_values, String score) {
		this.matrix = matrix;
		this.max_values = max_values;
		this.score = score;
	}

	/**
	 * Creates nodes and computes their score using the chosen score model to generate the graph.
	 * 
	 */
	@Override
	public void makeStruct() {
		
		if(score.equals("LL")) {
			nodes = new NodeLL[matrix[0].length-1];
			
			for(int i = 0; i < matrix[0].length-1; i++) {
				nodes[i] = new NodeLL(matrix, max_values, i);
				nodes[i].computeAlpha();
			}
		}
		else if(score.equals("MDL")) {
			nodes = new NodeMDL[matrix[0].length-1];
			
			
			for(int i = 0; i < matrix[0].length-1; i++) {
				nodes[i] = new NodeMDL(matrix, max_values, i);
				nodes[i].computeAlpha();
			}
		}
		else {
			System.out.println("No valid score mode selected. Terminating execution.");
			System.exit(1);
		}
	}

	/**
	 * Getter to the array of Node objects.
	 * 
	 */
	@Override
	public Node[] returnNodes() {
		return nodes;
	}

}
