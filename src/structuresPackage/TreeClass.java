package structuresPackage;

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
	
	/**
	 * Constructs a tree.
	 * 
	 * @param matrix The testing data from which predictions will be made.
	 * @param max_values Contains maximum of each set of features. 
	 * @param nodes Array containing previously generated nodes with computed alphas.
	 * @param feature_labels 
	 */
	public TreeClass(int[][] matrix, int[] max_values, Node[] nodes, String feature_labels) {
		this.matrix = matrix;
		this.max_values = max_values;
		this.nodes = nodes;
		this.feature_labels = feature_labels;
	}

	/**
	 * Generates the tree.
	 * The previously generated nodes are used to get an array of alpha's, necessary to build the adjacency matrix that represents the tree.
	 * New nodes of features are created with parameters theta and their parent's index; it's also created a new node C (class).
	 */
	@Override
	public void makeStruct() {
		alpha_list = new double[max_values.length-1][max_values.length-1];
		adjacency_matrix = new int[max_values.length-1][max_values.length-1];
		
		for(int i = 0; i < max_values.length-1; i++) {
			alpha_list[i] = nodes[i].returnAlpha();
		}
		
		this.buildAdjacencyMatrix();
//		System.out.println();
//		System.out.println(Arrays.deepToString(adjacency_matrix));
//		System.out.println();
		
		nodes = new NodeLL[max_values.length-1];
		for(int i = 0; i < max_values.length-1; i++) {
			
			int p = 0;
			for(int j = 0; j < max_values.length-1; j++) {
				if(adjacency_matrix[i][j] == 1) p = j;
			}
			
			nodes[i] = new NodeLL(matrix, max_values, i, p);
			nodes[i].computeTheta();
		}
		
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
		
		results = new int[test_matrix.length];
		
		for(int i = 0; i < test_matrix.length; i++) {
			int classification = -1;
			double prob = 0;
			for(int j = 0; j < max_values[max_values.length-1]+1; j++) {
				double temp_prob = 0;
				for(int n = 0; n < max_values.length-1; n++) {
					int p = 0;
					for(int k = 0; k < adjacency_matrix.length; k++) {
						if(adjacency_matrix[n][k] == 1) p=k;
					}
					
					temp_prob = temp_prob + Math.log(nodes[n].returnProbability(test_matrix[i][p], test_matrix[i][n], j));
				}
				
				temp_prob = temp_prob + Math.log(C.returnProbability(0, 0, j));
				
				if(classification == -1) {
					prob = temp_prob;
					classification = j;
				}else {
					if(temp_prob > prob) {
						prob = temp_prob;
						classification = j;
					}
				}
			}
			
			results[i] = classification;
		}
		
		//System.out.println(Arrays.toString(results));
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
		LinkedList<Integer> notInTree = new LinkedList<Integer>();
		LinkedList<Integer> inTree = new LinkedList<Integer>();
		
		for(int i = 0; i < max_values.length-1; i++) {
			adjacency_matrix[0][i] = 0;
			notInTree.add(i);
		}
		
		inTree.add(0);
		notInTree.removeFirstOccurrence(0);
		
		for(int j = 0; j < max_values.length-2; j++) {
			//System.out.println("Iteration" + j);
			max_value = Double.NaN;
			Iterator<Integer> iterator = inTree.iterator();
			while (iterator.hasNext()) {
				Integer temp = iterator.next();
				Iterator<Integer> not_iterator = notInTree.iterator();
			    while(not_iterator.hasNext()) {
			    	Integer temp2 = not_iterator.next();
			    	if(Double.isNaN(alpha_list[temp.intValue()][temp2.intValue()]) == false) {
			    		if(Double.isNaN(max_value)) {
			    			max_value = alpha_list[temp.intValue()][temp2.intValue()];
			    			max_node = temp2.intValue();
			    			parent = temp.intValue();
			    		}else {
			    			if(alpha_list[temp.intValue()][temp2.intValue()] > max_value) {
			    				max_value = alpha_list[temp.intValue()][temp2.intValue()];
				    			max_node = temp2.intValue();
				    			parent = temp.intValue();
			    			}
			    		}
			    	}
			    }
			}
			
			//System.out.println("Max value: " + max_value + "\tMax node: " + max_node + "\tParent: " + parent);
			
			alpha_list[parent][max_node] = Double.NaN;
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
	 * Override of toString method, allows to print the object tree structure.
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
		
		return ret.toString();
	}

}
