package structuresPackage;

public abstract class NodeAbstract implements Node{
	double[] alpha;
	double[] theta;
	int[][] training;
	int[] max_values;
	int node_id;
	
	public NodeAbstract() {
		System.out.println("No data supplied.");
		System.exit(1);
	}
	
	public NodeAbstract(int[][] training, int[] max_values, int node_id) {
		this.training = training;
		this.max_values = max_values;
		this.node_id = node_id;
	}
	
	public abstract void computeAlpha();
	
	public void computeTheta() {
		
	}
	
	public double[] returnAlpha() {
		return alpha;
	}
}
