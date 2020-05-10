package metricsPackage;

import java.util.ArrayList;

public class Specificity<T> implements Metrics<T> {
	
	private ArrayList<T> classes = new ArrayList<T>();
	//private ArrayList<Integer> TN = new ArrayList<Integer>();			// DEBUG purposes
	private ArrayList<Integer> N = new ArrayList<Integer>();
	private ArrayList<Float> specf_c = new ArrayList<Float>();
	private T[] Ctest;
	float specf_avg;
	private T[] res;
	
	public Specificity(T[] Ctest, T[] res){
		this.Ctest = Ctest;
		this.res = res;
	}

	@Override
	public void evaluate() {
		// identify each different class
		for (T c : Ctest)
			if(!classes.contains(c)) insert(c);
		
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
			N.add(_N);
			//TN.add(_TN); //debug
			_specf = (float)_TN/(_N);
			specf_c.add(_specf);

			// compute, partially, weighted average specificity
			specf_avg += (float) fC*_specf; 
		}
		// conclude weighted average specificity computation
		specf_avg = (float) specf_avg/Ctest.length;
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
	public String toString()
	{
	    StringBuilder str = new StringBuilder();	// var where string is appended to
	    str.append("[");

	    // append scores for all classes
	    for(int i = 0; i < classes.size(); i++)
		    str.append(""+ classes.get(i) + ": "+ String.format("%.2f", specf_c.get(i))+", "); 
	    
	    // append last element (avg)
	    str.append(""+ String.format("%.2f", specf_avg)+"]");
	    return str.toString();
	}
	
//	@Override 						// DEBUG purposes: uncomment "TN.add(_TN);"
//	public String toString() {
//		return "Specificity [classes=" + classes + ", TN=" + TN + ", N=" + N + ", specf_c=" + specf_c + ", Ctest="
//				+ Arrays.toString(Ctest) + ", specf_avg=" + specf_avg + "]";
//	}
	
	
	

}
