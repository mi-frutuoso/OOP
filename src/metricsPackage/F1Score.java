package metricsPackage;

public class F1Score<T> extends MetricAbstract<T> {
	
	public F1Score(T[] Ctest,T[] res){
		super(Ctest, res);
	}
	
	@Override
	public void evaluate() {
		// array verification
		if(Ctest.length != res.length) {
			System.out.println("F1Score error: Predicted and test classes' array not consistent. Exiting...");
			System.exit(1);
		}
		
		// identify each different class
		for (T c : Ctest)
			if(!classes.contains(c)) insert(c);
		
		// compute TruePositives (_TP), FalsePositives (_FP) and FalseNegatives (_FN) for each identified class
		int i; 										// iterator
		int _TP;
		int _FP;
		int _FN;
		int fC; 									// stores frequency of each class in predicted set
		float _f1;
		for (T _c : classes) {						// for each identified class in test set
			i = 0;
			_TP = 0;
			_FP = 0;
			_FN = 0;
			fC = 0;
			for (T _tc : Ctest) {					// for each instance of test set
				if(_tc.equals(_c)) {				// class should be accepted
					if(_c.equals(res[i])) _TP++;	// correctly accepted
					else _FN++;						// not accepted
					fC++;
				}
				else { 								// class should be rejected
					if(_c.equals(res[i])) _FP++; 	// not rejected
					//else _TN++;					// correctly rejected
				}
				i++;
			}
			_f1 = (float) 2*_TP/(2*_TP+_FP+_FN);
			metricValues.add(_f1);

			// compute, partially, weighted average F1score
			avg += (float) fC*_f1; 
		}
		// conclude weighted average F1score computation
		avg = (float) avg/Ctest.length;
	}
	
//	@Override									// DEBUG purposes
//	public String toString() {
//		return "F1Score [classes=" + classes + ", f1_c=" + metricValues + ", Ctest=" + Arrays.toString(Ctest) + ", f1_avg="
//				+ avg + "]";
//	}

}
