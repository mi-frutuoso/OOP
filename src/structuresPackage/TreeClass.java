package structuresPackage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Generates a tree (graph) implemented as an adjacency matrix with accessible nodes.
 * 
 */
public class TreeClass implements Tree{
	
	Node[] nodes;
	Node C;
	int[][] matrix;
	int[] max_values;
	double[][] alpha_list;
	int[][] adjacency_matrix;
	int[] results;
	String feature_labels;
	boolean debug;
	
	/**
	 * Constructs a tree.
	 * 
	 * @param matrix The testing data from which predictions will be made.
	 * @param max_values Contains maximum of each set of features. 
	 * @param nodes Array containing previously generated nodes with computed alphas.
	 * @param feature_labels Contains a string with the features labels
	 * @param debug To display debug information or not
	 */
	public TreeClass(int[][] matrix, int[] max_values, Node[] nodes, String feature_labels, boolean debug) {
		this.matrix = matrix;
		this.max_values = max_values;
		this.nodes = nodes;
		this.feature_labels = feature_labels;
		this.debug = debug;
	}

	/**
	 * Generates the tree.
	 * The previously generated nodes are used to get an array of alpha's, necessary to build the adjacency matrix that represents the tree.
	 * New nodes of features are created with parameters theta and their parent's index; it's also created a new node C (class).
	 */
	@Override
	public void makeStruct() {
		//Allocates memory to store all the alpha values from all the nodes and the adjacency matrix
		alpha_list = new double[max_values.length-1][max_values.length-1];
		adjacency_matrix = new int[max_values.length-1][max_values.length-1];
		
		//Gets the values of the alphas from all the nodes
		for(int i = 0; i < max_values.length-1; i++) {
			alpha_list[i] = nodes[i].returnAlpha();
		}
		
		//Builds the adjacency matrix
		this.buildAdjacencyMatrix();
		
		//Creates new nodes with parents and computes the thetas for each one
		nodes = new NodeLL[max_values.length-1];
		for(int i = 0; i < max_values.length-1; i++) {
			
			int p = 0;
			for(int j = 0; j < max_values.length-1; j++) {
				if(adjacency_matrix[i][j] == 1) p = j;
			}
			
			nodes[i] = new NodeLL(matrix, max_values, i, p);
			nodes[i].computeTheta();
		}
		
		//Create class node and compute the thetas
		C = new NodeLL(matrix, max_values, -1, -1);
		C.computeTheta();
		
	}

	/**
	 * Predicts classes for the sets of features in test data.
	 * 
	 * 
	 * @param test_matrix Test data in a two-dimensional matrix format.
	 */
	@Override
	public void predict(int[][] test_matrix) {
		
		//Allocate memory to store all the results
		results = new int[test_matrix.length];
		
		//For each entry in the test dataset
		for(int i = 0; i < test_matrix.length; i++) {
			int classification = -1;
			double prob = 0;
			//Test all the possible values for the class to check which one yields a higher probability
			for(int j = 0; j < max_values[max_values.length-1]+1; j++) {
				double temp_prob = 0;
				//Gets the probability associated with each node and sums the logs of the probabilities
				for(int n = 0; n < max_values.length-1; n++) {
					int p = 0;
					for(int k = 0; k < adjacency_matrix.length; k++) {
						if(adjacency_matrix[n][k] == 1) p=k;
					}
					
					temp_prob = temp_prob + Math.log(nodes[n].returnProbability(test_matrix[i][p], test_matrix[i][n], j));
				}
				
				temp_prob = temp_prob + Math.log(C.returnProbability(0, 0, j));
				
				//If no classification as been attributed yet
				if(classification == -1) {
					prob = temp_prob;
					classification = j;
				}
				//If the probability of this class is higher then the previous maximum, update
				else {
					if(temp_prob > prob) {
						prob = temp_prob;
						classification = j;
					}
				}
			}
			
			//Store the classification values
			results[i] = classification;
		}
	}

	/**
	 * Getter to the array of Node objects.
	 */
	@Override
	public Node[] returnNodes() {
		return nodes;
	}
	
	/**
	 * Defines the adjacency between nodes.
	 * 
	 */
	private void buildAdjacencyMatrix() {
		double max_value;
		int max_node = 0;
		int parent = 0;
		//Creates two lists, one for the nodes in the tree and other for the nodes not yet on the tree
		LinkedList<Integer> notInTree = new LinkedList<Integer>();
		LinkedList<Integer> inTree = new LinkedList<Integer>();
		
		//Makes the node 0 the root node
		for(int i = 0; i < max_values.length-1; i++) {
			adjacency_matrix[0][i] = 0;
			notInTree.add(i);
		}
		
		//Adds node 0 to the tree list and removes from the non-tree list
		inTree.add(0);
		notInTree.removeFirstOccurrence(0);
		
		//For loop equal to the number of nodes we need to insert in the tree
		for(int j = 0; j < max_values.length-2; j++) {
			//System.out.println("Iteration" + j);
			max_value = Double.NaN;
			//Iterate over all the nodes on the tree
			Iterator<Integer> iterator = inTree.iterator();
			while (iterator.hasNext()) {
				Integer temp = iterator.next();
				//Iterate over all the nodes not on the tree
				Iterator<Integer> not_iterator = notInTree.iterator();
			    while(not_iterator.hasNext()) {
			    	Integer temp2 = not_iterator.next();
			    	//Check id this given position is not NaN
			    	if(Double.isNaN(alpha_list[temp.intValue()][temp2.intValue()]) == false) {
			    		//If no maximum has been defined yet, then this is attributed as the maximum
			    		if(Double.isNaN(max_value)) {
			    			max_value = alpha_list[temp.intValue()][temp2.intValue()];
			    			max_node = temp2.intValue();
			    			parent = temp.intValue();
			    		}
			    		//Compare with the actual maximum and update the value if higher
			    		else {
			    			if(alpha_list[temp.intValue()][temp2.intValue()] > max_value) {
			    				max_value = alpha_list[temp.intValue()][temp2.intValue()];
				    			max_node = temp2.intValue();
				    			parent = temp.intValue();
			    			}
			    		}
			    	}
			    }
			}
			
			//Update the tree list, the not-in-tree list and the adjacency matrix with the new element
			inTree.add(max_node);
			adjacency_matrix[max_node][parent] = 1;
			notInTree.removeFirstOccurrence(max_node);
		}
	}

	/**
	 * Getter for the classification results.
	 */
	@Override
	public int[] returnClassification() {
		return results;
	}
	
	/**
	 * Override of toString method, allows to print the object tree structure and debug information.
	 */
	@Override
	public String toString() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int p;
		StringBuffer ret = new StringBuffer();
		String[] features = new String[max_values.length];	// convert features from int to real names
		for(int i=0; i<features.length; i++) features[i] = feature_labels.split(",")[i];
		
		//ret.append("X1 : C\n");
		ret.append(String.format("%s : %s\n", features[0], features[features.length-1]));
		
		list.add(0);
		
		for(int i = 0; i < max_values.length-2; i++) {
			if(list == null) return null;
			else p = list.remove();
			for(int j = 0; j < max_values.length-1; j++) {
				if(adjacency_matrix[j][p] == 1) {
//					ret.append("\t\t\tX" + (j+1) + " : X" + (p+1) + " C\n");
					ret.append("\t\t\t" + features[j] + " : " + features[features.length-1] + " " + features[p]+"\n");
					list.add(j);
				}
			}
		}
		
		if(debug) {
			ret.append("====DEBUG====\n");
			for(int i = 0; i < max_values.length-1; i++) {
				ret.append(nodes[i].toString());
				ret.append("Alphas:\n" + Arrays.toString(alpha_list[i]) + "\n");
			}
			ret.append("====DEBUG====\n");
		}
		
		return ret.toString();
	}

}
