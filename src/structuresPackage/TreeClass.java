package structuresPackage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class TreeClass implements Tree{
	
	Node[] nodes;
	int[][] matrix;
	int[] max_values;
	double[][] alpha_list;
	int[][] adjacency_matrix;
	LinkedList<Integer> inTree = new LinkedList<Integer>();
	LinkedList<Integer> notInTree = new LinkedList<Integer>();
	
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
		
	}

	@Override
	public void predict() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node[] returnNodes() {
		return nodes;
	}
	
	private void buildAdjacencyMatrix() {
		double max_value;
		int max_node = 0;
		int parent = 0;
		
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

}
