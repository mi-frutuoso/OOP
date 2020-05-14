package structuresPackage;

import java.lang.Math;

/**
 * Implementation of the node using the minimum description length (MDL) score to compute the value of the Alphas.
 *
 */
public class NodeMDL extends NodeAbstract{
	
	/**
	 * Constructor that receives the training dataset, the maximum values of each attribute/class and the node identification.
	 * Delegates to the constructor of the super class.
	 * @param training Double array containing the training dataset.
	 * @param max_values Array that contains the maximum values for each attribute and class.
	 * @param node_id Node identification.
	 */
	public NodeMDL(int[][] training, int[] max_values, int node_id) {
		super(training, max_values, node_id);
	}

	/**
	 * Computes the value of the Alpha parameters accordingly to the MDL score.
	 */
	@Override
	public void computeAlpha() {
		//Allocate memory for the alphas array
		alpha = new double[training[0].length-1];
		
		//Checks every feature
		for(int i = 0; i < training[0].length-1; i++) {
			//Sets the position of the array corresponding to itself to NaN and continues the loop
			if(i == node_id) {
				alpha[i] = Double.NaN;
				continue;
			}
			
			//Allocate memory to keep track of the counts
			int[][][] Nijkc = new int[max_values[i]+1][max_values[node_id]+1][max_values[max_values.length-1]+1];
			int[][] Nikc = new int[max_values[node_id]+1][max_values[max_values.length-1]+1];
			int[][] Nijc = new int[max_values[i]+1][max_values[max_values.length-1]+1];
			int[] Nc = new int[max_values[max_values.length-1]+1];
			int N = training.length;
			
			//Counts the patterns found on the training dataset
			for(int n = 0; n < training.length; n++) {
				Nijkc[training[n][i]][training[n][node_id]][training[n][training[n].length-1]]++;
				Nikc[training[n][node_id]][training[n][training[n].length-1]]++;
				Nijc[training[n][i]][training[n][training[n].length-1]]++;
				Nc[training[n][training[n].length-1]]++;
			}
			
			//Computes the values of the alpha for a given node(self)->node(target) using the MDL score
			double total = 0;
			for(int j = 0; j < max_values[i] + 1; j++) {
				for(int k = 0; k < max_values[node_id] + 1; k++) {
					for(int l = 0; l < max_values[max_values.length-1] + 1; l++) {
						if(Nijkc[j][k][l] == 0 || Nikc[k][l] == 0 || Nijc[j][l] == 0) {
							continue;
						}
				
						total += ((double)Nijkc[j][k][l] / (double)N) * (Math.log(((double)Nijkc[j][k][l]*(double)Nc[l])/((double)Nikc[k][l]*(double)Nijc[j][l])) / Math.log(2));
					}
				}
			}
			
			total = total - ((((max_values[max_values.length-1]+1)*max_values[node_id]*max_values[i])/(2))*Math.log(N));
			
			//Attribution of the value to the corresponding position on the array
			alpha[i] = total;
		}
		
		//System.out.println(Arrays.toString(alpha));
	}

}
