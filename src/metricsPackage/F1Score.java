package metricsPackage;

import java.util.ArrayList;

public class F1Score<T> implements Metrics<T> {

	private ArrayList<T> classes = new ArrayList<T>();
	private ArrayList<Float> f1_c = new ArrayList<Float>();
	private T[] Ctest;
	float f1_avg;
	
	public F1Score(T[] Ctest){
		this.Ctest = Ctest;
	}

	@Override
	public void evaluate(T[] res) {
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
			f1_c.add(_f1);

			// compute, partially, weighted average F1score
			f1_avg += (float) fC*_f1; 
		}
		// conclude weighted average F1score computation
		f1_avg = (float) f1_avg/Ctest.length;
	}
	
	private void insert(T elem){					// insertion with lexicographical order
	    // iterate over classes[]
	    for (int i = 0; i < classes.size(); i++) {
	        if (classes.get(i).toString().compareTo(elem.toString()) <= -1) {
	        	continue; 							//keep searching
	        }
	        classes.add(i, elem);					// location to insert has been found
	        return;
	    }
	    classes.add(elem);							// insert in the end
	}

	@Override
	public String toString() {
		return "[" + f1_c + ", " + f1_avg +"]";
	}
	
//	@Override									// DEBUG purposes
//	public String toString() {
//		return "F1Score [classes=" + classes + ", f1_c=" + f1_c + ", Ctest=" + Arrays.toString(Ctest) + ", f1_avg="
//				+ f1_avg + "]";
//	}

}
