package metricsPackage;

//import java.util.ArrayList;

public class Specificity<T> extends MetricAbstract<T> {
	
	//private ArrayList<Integer> TN = new ArrayList<Integer>();			// DEBUG purposes
	//private ArrayList<Integer> N = new ArrayList<Integer>();			// DEBUG purposes
	
	/**
	 * Constructor that invokes superclass.
	 * @param Ctest Array containing the true classification values.
	 * @param res Array containing the predicted classification values (that is, the obtained result).
	 */
	public Specificity(T[] Ctest, T[] res){
		super(Ctest, res);
	}

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
		
		// compute Negatives (_N) and TrueNegatives (_TN) for each identified class
		int i; 							// iterator
		int _N;
		int _TN;
		int fC; 						// stores frequency of each class in predicted set
		float _specf;
		for (T _c : classes) {			// for each identified class in test set
			i = 0;
			_N = 0;
			_TN = 0;
			fC = 0;
			for (T _tc : Ctest) {		// for each instance of test set
				if(!_tc.equals(_c)) {
					_N++;
					if(!_c.equals(res[i])) _TN++;
				} else fC++;
				i++;
			}
			//N.add(_N);
			//TN.add(_TN); //debug
			_specf = (float)_TN/(_N);
			metricValues.add(_specf);

			// compute, partially, weighted average specificity
			avg += (float) fC*_specf; 
		}
		// conclude weighted average specificity computation
		avg = (float) avg/Ctest.length;
	}
	
//	@Override 						// DEBUG purposes: uncomment "TN.add(_TN);"
//	public String toString() {
//		return "Specificity [classes=" + classes + ", TN=" + TN + ", N=" + N + ", specf_c=" + metricValues + ", Ctest="
//				+ Arrays.toString(Ctest) + ", specf_avg=" + avg + "]";
//	}
	
	
	

}
