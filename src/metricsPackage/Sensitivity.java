package metricsPackage;

//import java.util.ArrayList;

public class Sensitivity<T> extends MetricAbstract<T> {

	//private ArrayList<Integer> TP = new ArrayList<Integer>();			// DEBUG purposes
	//private ArrayList<Integer> P = new ArrayList<Integer>();			// DEBUG purposes
	
	public Sensitivity(T[] Ctest, T[] res){
		super(Ctest, res);
	}
	
	@Override
	public void evaluate() {
		// identify each different class
		for (T c : Ctest)
			if(!classes.contains(c)) insert(c);
		
		// TODO: sort classes
		// ...
		
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
