package structuresPackage;

public class GraphClass implements Graph{
	
	Node[] nodes;
	int[][] matrix;
	int[] max_values;
	String score;
	
	public GraphClass(int[][] matrix, int[] max_values, String score) {
		this.matrix = matrix;
		this.max_values = max_values;
		this.score = score;
	}

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

}
