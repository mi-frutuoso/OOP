package structuresPackage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class TreeClass implements Tree{
	
	Node[] nodes;
	Node C;
	int[][] matrix;
	int[] max_values;
	double[][] alpha_list;
	int[][] adjacency_matrix;
	int[] results;
	
	public TreeClass(int[][] matrix, int[] max_values, Node[] nodes) {
		this.matrix = matrix;
		this.max_values = max_values;
		this.nodes = nodes;
	}

	@Override
	public void makeStruct() {
		alpha_list = new double[max_values.length-1][max_values.length-1];
		adjacency_matrix = new int[max_values.length-1][max_values.length-1];
		
		for(int i = 0; i < max_values.length-1; i++) {
			alpha_list[i] = nodes[i].returnAlpha();
		}
		
		this.buildAdjacencyMatrix();
		System.out.println();
		System.out.println(Arrays.deepToString(adjacency_matrix));
		System.out.println();
		
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
		
		System.out.println(Arrays.toString(results));
	}

	@Override
	public Node[] returnNodes() {
		return nodes;
	}
	
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
			System.out.println("Iteration" + j);
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
			    			alpha_list[temp.intValue()][temp2.intValue()] = Double.NaN;
			    		}else {
			    			if(alpha_list[temp.intValue()][temp2.intValue()] > max_value) {
			    				max_value = alpha_list[temp.intValue()][temp2.intValue()];
				    			max_node = temp2.intValue();
				    			parent = temp.intValue();
				    			alpha_list[temp.intValue()][temp2.intValue()] = Double.NaN;
			    			}
			    		}
			    	}
			    }
			}
			
			System.out.println("Max value: " + max_value + "\tMax node: " + max_node + "\tParent: " + parent);
			
			inTree.add(max_node);
			adjacency_matrix[max_node][parent] = 1;
			notInTree.removeFirstOccurrence(max_node);
		}
	}

	@Override
	public int[] returnClassification() {
		return results;
	}

}
