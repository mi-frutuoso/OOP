package structuresPackage;

public abstract class NodeAbstract implements Node{
	double[] alpha;
	double[][][] theta;
	int[][] training;
	int[] max_values;
	int node_id;
	int parent;
	static final double N_ = 0.5;
	
	public NodeAbstract() {
		System.out.println("No data supplied.");
		System.exit(1);
	}
	
	public NodeAbstract(int[][] training, int[] max_values, int node_id) {
		this.training = training;
		this.max_values = max_values;
		this.node_id = node_id;
	}
	
	public NodeAbstract(int[][] training, int[] max_values, int node_id, int parent) {
		this.training = training;
		this.max_values = max_values;
		this.node_id = node_id;
		this.parent = parent;
	}
	
	public abstract void computeAlpha();
	
	public void computeTheta() {
		
		if(node_id == -1) {
			theta = new double[1][1][max_values[max_values.length-1]+1];
			
			int[] Nc = new int[max_values[max_values.length-1]+1];
			int N = training.length;
			
			
			for(int n = 0; n < training.length; n++) {
				Nc[training[n][training[n].length-1]]++;
			}
			
			for(int i = 0; i < max_values[max_values.length-1]+1; i++) {
				theta[0][0][i] = ((double)Nc[i] + N_) / (((double)N) + (N_ * (double)(max_values[max_values.length-1]+1)));
			}
		}
		else {
			if(node_id != parent) {
				theta = new double[max_values[parent]+1][max_values[node_id]+1][max_values[max_values.length-1]+1];
				
				int[][][] Nijkc = new int[max_values[parent]+1][max_values[node_id]+1][max_values[max_values.length-1]+1];
				int[][] Nijc = new int[max_values[parent]+1][max_values[max_values.length-1]+1];
				
				for(int n = 0; n < training.length; n++) {
					Nijkc[training[n][parent]][training[n][node_id]][training[n][training[n].length-1]]++;
					Nijc[training[n][parent]][training[n][training[n].length-1]]++;
				}
				
				for(int i = 0; i < max_values[parent]+1; i++) {
					for(int j = 0; j < max_values[node_id]+1; j++) {
						for(int k = 0; k < max_values[max_values.length-1]+1; k++) {
							theta[i][j][k] = ((double)Nijkc[i][j][k] + N_) / ((double)(Nijc[i][k]) + ((double)(max_values[node_id]+1)*N_));
						}
					}
				}
			} else {
				theta = new double[1][max_values[node_id]+1][max_values[max_values.length-1]+1];
				
				int[][] Nijkc = new int[max_values[node_id]+1][max_values[max_values.length-1]+1];
				int[] Nijc = new int[max_values[max_values.length-1]+1];
				
				for(int n = 0; n < training.length; n++) {
					Nijkc[training[n][node_id]][training[n][training[n].length-1]]++;
					Nijc[training[n][training[n].length-1]]++;
				}

				for(int j = 0; j < max_values[node_id]+1; j++) {
					for(int k = 0; k < max_values[max_values.length-1]+1; k++) {
						theta[0][j][k] = ((double)Nijkc[j][k] + N_) / ((double)(Nijc[k]) + ((double)(max_values[node_id]+1)*N_));
					}
				}
			}
		}
		
		//System.out.println("Theta for node " + node_id + ":");
		//System.out.println(Arrays.deepToString(theta));
	}
	
	public double[] returnAlpha() {
		return alpha;
	}
	
	public double returnProbability(int parent_value, int node_value, int class_value) {
		if(node_id == -1) {
			return theta[0][0][class_value];
		}else {
			if(node_id != parent) {
				return theta[parent_value][node_value][class_value];
			}else {
				return theta[0][node_value][class_value];
			}
		}
	}
}
