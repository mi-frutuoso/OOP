package structuresPackage;

import java.lang.Math;

public class NodeLL extends NodeAbstract{
	
	public NodeLL(int[][] training, int[] max_values, int node_id) {
		super(training, max_values, node_id);
	}
	
	public NodeLL(int[][] training, int[] max_values, int node_id, int parent) {
		super(training, max_values, node_id, parent);
	}

	@Override
	public void computeAlpha() {
		alpha = new double[training[0].length-1];
		
		for(int i = 0; i < training[0].length-1; i++) {
			if(i == node_id) {
				alpha[i] = Double.NaN;
				continue;
			}
			
			int[][][] Nijkc = new int[max_values[i]+1][max_values[node_id]+1][max_values[max_values.length-1]+1];
			int[][] Nikc = new int[max_values[node_id]+1][max_values[max_values.length-1]+1];
			int[][] Nijc = new int[max_values[i]+1][max_values[max_values.length-1]+1];
			int[] Nc = new int[max_values[max_values.length-1]+1];
			int N = training.length;
			
			for(int n = 0; n < training.length; n++) {
				Nijkc[training[n][i]][training[n][node_id]][training[n][training[n].length-1]]++;
				Nikc[training[n][node_id]][training[n][training[n].length-1]]++;
				Nijc[training[n][i]][training[n][training[n].length-1]]++;
				Nc[training[n][training[n].length-1]]++;
			}
			
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
			
			alpha[i] = total;
		}
		
		//System.out.println(Arrays.toString(alpha));
	}

}
