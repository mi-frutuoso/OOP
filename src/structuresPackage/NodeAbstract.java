package structuresPackage;

/**
 * Abstract implementation of the <b>Node</b> interface.<br>
 * It defines some attributes and constructors and gives the implementation of certain methods that are the same for every type of node.
 *
 */
public abstract class NodeAbstract implements Node{
	
	/**
	 * Array that contains the values of the Alpha parameters.
	 */
	double[] alpha;
	/**
	 * Double array that contains the values of the Theta parameters.
	 */
	double[][][] theta;
	/**
	 * Double array that holds the values of the training data.
	 */
	int[][] training;
	/**
	 * Array that holds the maximum value of each attribute and the class.
	 */
	int[] max_values;
	/**
	 * Node identification.
	 */
	int node_id;
	/**
	 * Parent identification
	 */
	int parent;
	static final double N_ = 0.5;
	
	/**
	 * No-arg constructor that aborts the execution.
	 */
	public NodeAbstract() {
		System.out.println("No data supplied.");
		System.exit(1);
	}
	
	/**
	 * Constructor that receives the training values, the maximum values and the node identification
	 * 
	 * @param training Double array containing the values of the training dataset.
	 * @param max_values Array containing the maximum value for each feature and the class.
	 * @param node_id Node identification.
	 */
	public NodeAbstract(int[][] training, int[] max_values, int node_id) {
		this.training = training;
		this.max_values = max_values;
		this.node_id = node_id;
	}
	
	/**
	 * Constructor that receives in addition the parent node identification.
	 * @param training Double array containing the values of the training dataset.
	 * @param max_values Array containing the maximum value for each feature and the class.
	 * @param node_id Node identification.
	 * @param parent Parent node identification.
	 */
	public NodeAbstract(int[][] training, int[] max_values, int node_id, int parent) {
		this(training, max_values, node_id);
		this.parent = parent;
	}
	
	/**
	 * Abstract method to be implemented depending on the type of node.
	 */
	public abstract void computeAlpha();
	
	
	/**
	 * Computes the Theta parameters from the training dataset.
	 */
	public void computeTheta() {
		
		//If node C
		if(node_id == -1) {
			//Allocate the memory to save the parameters
			theta = new double[1][1][max_values[max_values.length-1]+1];
			
			//Variables that will contain the counting
			int[] Nc = new int[max_values[max_values.length-1]+1];
			int N = training.length;
			
			//Counts the number of patterns that occur in the dataset
			for(int n = 0; n < training.length; n++) {
				Nc[training[n][training[n].length-1]]++;
			}
			
			//Computes the Theta parameters from the counts
			for(int i = 0; i < max_values[max_values.length-1]+1; i++) {
				theta[0][0][i] = ((double)Nc[i] + N_) / (((double)N) + (N_ * (double)(max_values[max_values.length-1]+1)));
			}
		}
		//If not node C
		else {
			//If it is not the root node
			if(node_id != parent) {
				//Allocate the memory to save the parameters
				theta = new double[max_values[parent]+1][max_values[node_id]+1][max_values[max_values.length-1]+1];
				
				//Variables that will contain the counting
				int[][][] Nijkc = new int[max_values[parent]+1][max_values[node_id]+1][max_values[max_values.length-1]+1];
				int[][] Nijc = new int[max_values[parent]+1][max_values[max_values.length-1]+1];
				
				//Counts the number of patterns that occur in the dataset
				for(int n = 0; n < training.length; n++) {
					Nijkc[training[n][parent]][training[n][node_id]][training[n][training[n].length-1]]++;
					Nijc[training[n][parent]][training[n][training[n].length-1]]++;
				}
				
				//Computes the Theta parameters from the counts
				for(int i = 0; i < max_values[parent]+1; i++) {
					for(int j = 0; j < max_values[node_id]+1; j++) {
						for(int k = 0; k < max_values[max_values.length-1]+1; k++) {
							theta[i][j][k] = ((double)Nijkc[i][j][k] + N_) / ((double)(Nijc[i][k]) + ((double)(max_values[node_id]+1)*N_));
						}
					}
				}
			} 
			//If it is the root node
			else {
				//Allocate the memory to save the parameters
				theta = new double[1][max_values[node_id]+1][max_values[max_values.length-1]+1];
				
				//Variables that will contain the counting
				int[][] Nijkc = new int[max_values[node_id]+1][max_values[max_values.length-1]+1];
				int[] Nijc = new int[max_values[max_values.length-1]+1];
				
				//Counts the number of patterns that occur in the dataset
				for(int n = 0; n < training.length; n++) {
					Nijkc[training[n][node_id]][training[n][training[n].length-1]]++;
					Nijc[training[n][training[n].length-1]]++;
				}

				//Computes the Theta parameters from the counts
				for(int j = 0; j < max_values[node_id]+1; j++) {
					for(int k = 0; k < max_values[max_values.length-1]+1; k++) {
						theta[0][j][k] = ((double)Nijkc[j][k] + N_) / ((double)(Nijc[k]) + ((double)(max_values[node_id]+1)*N_));
					}
				}
			}
		}
	}
	
	/**
	 * Returns the Alpha parameters as an array.
	 */
	public double[] returnAlpha() {
		return alpha;
	}
	
	/**
	 * Return the probability of the node having a certain value given the value of the parent and the class.<br>
	 * It searches in the Theta structure built previously.
	 */
	public double returnProbability(int parent_value, int node_value, int class_value) {
		//If node C
		if(node_id == -1) {
			return theta[0][0][class_value];
		}
		//If not node C
		else {
			//If not root node
			if(node_id != parent) {
				return theta[parent_value][node_value][class_value];
			}
			//If root node
			else {
				return theta[0][node_value][class_value];
			}
		}
	}
}
