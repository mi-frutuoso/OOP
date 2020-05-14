package metricsPackage;

//import java.util.ArrayList;

/**
 * Metric class that calculates the sensitivity, given two generic arrays: predicted and expected classes.
 * Sensitivity is computed for each class and then a weighted average is computed.
 *
 * @param <T> Generic type that contains classification values.
 */
public class Sensitivity<T> extends MetricAbstract<T> {

	//private ArrayList<Integer> TP = new ArrayList<Integer>();			// DEBUG purposes
	//private ArrayList<Integer> P = new ArrayList<Integer>();			// DEBUG purposes
	
	/**
	 * Constructor that invokes superclass.
	 * @param Ctest Array containing the true classification values.
	 * @param res Array containing the predicted classification values (that is, the obtained result).
	 */
	public Sensitivity(T[] Ctest, T[] res){
		super(Ctest, res);
	}
	
	/**
	 * Computes sensitivity for each class and weighted average.
	 */
	@Override
	public void evaluate() {
		// array verification
		if(Ctest.length != res.length) {
			System.out.println("Sensitivity error: Predicted and test classes' array not consistent. Exiting...");
			System.exit(1);
		}
		
		// identify each different class and store it
		for (T c : Ctest)
			if(!classes.contains(c)) classes.add(c);
		
		// sort classes numerically or alphabetically
		sortClasses();
		
		// compute Positives (_P) and TruePositives (_TP) for each identified class
		int i; // iterator
		int _P;
		int _TP;
		float _senst;
		for (T _c : classes) {	// for each identified class in test set
			i = 0;
			_P = 0;
			_TP = 0;
			for (T _tc : Ctest) {	// for each instance of test set
				if(_tc.equals(_c)) {
					_P++;
					if(_c.equals(res[i])) _TP++;
				}
				i++;
			}
			//P.add(_P);
			//TP.add(_TP); //debug
			_senst = (float)_TP/(_P);
			metricValues.add(_senst);

			// compute, partially, weighted average sensitivity
			avg += (float) _P*_senst; 
		}
		// conclude weighted average sensitivity computation
		avg = (float) avg/Ctest.length;
	}	

	
//	@Override 						// DEBUG purposes: uncomment "TP.add(_TP);"
//	public String toString() {
//		return "Sensitivity [classes=" + classes + ", TP=" + TP + ", P=" + P + ", senst_c=" + metricValues + ", senst_avg="
//				+ avg + ", Ctest=" + Arrays.toString(Ctest) + "]";
//	}

}
