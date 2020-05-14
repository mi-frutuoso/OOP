package metricsPackage;

/**
 * Metric class that calculates the classification accuracy, given two generic arrays: predicted and expected classes.
 * Accuracy is computed by comparing the values of each array's instance.
 *
 * @param <T> Generic type that contains classification values.
 */
public class Accuracy<T> implements Metrics<T> {
	
	private T[] trueC;
	private T[] res;
	private float acc;
	
	/**
	 * 
	 * @param trueC Array containing the true classification values.
	 * @param res Array containing the predicted classification values (that is, the obtained result).
	 */
	public Accuracy(T[] trueC, T[] res){
		this.trueC = trueC;
		this.res = res;
	}
	
	/**
	 * Computes global accuracy.
	 */
	@Override
	public void evaluate() {
		// TODO Auto-generated method stub
		if(trueC.length != res.length) {
			System.out.println("Accuracy error: Predicted and test classes' array not consistent. Exiting...");
			System.exit(1);
		}
		
		int Ncorrect = 0;
		for(int i = 0; i < trueC.length; i++)
			if(trueC[i].equals(res[i])) Ncorrect++;
		
		acc = (float)Ncorrect/(trueC.length);
	}
	
	/**
	 * Prepares the correct output format, to be displayed on the terminal.
	 */
	@Override
	public String toString() {
		return String.format("%.2f", acc);
	}
	
	

}
