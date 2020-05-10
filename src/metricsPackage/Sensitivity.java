package metricsPackage;

import java.util.ArrayList;

public class Sensitivity<T> implements Metrics<T> {

	private ArrayList<T> classes = new ArrayList<T>();
	//private ArrayList<Integer> TP = new ArrayList<Integer>();			// DEBUG purposes
	private ArrayList<Integer> P = new ArrayList<Integer>();
	private ArrayList<Float> senst_c = new ArrayList<Float>();
	float senst_avg;
	private T[] Ctest;
	private T[] res;
	
	public Sensitivity(T[] Ctest, T[] res){
		this.Ctest = Ctest;
		this.res = res;
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
			P.add(_P);
			//TP.add(_TP); //debug
			_senst = (float)_TP/(_P);
			senst_c.add(_senst);

			// compute, partially, weighted average sensitivity
			senst_avg += (float) _P*_senst; 
		}
		// conclude weighted average sensitivity computation
		senst_avg = (float) senst_avg/Ctest.length;
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
		    str.append(""+ classes.get(i) + ": "+ String.format("%.2f", senst_c.get(i))+", "); 
	    
	    // append last element (avg)
	    str.append(""+ String.format("%.2f", senst_avg)+"]");
	    return str.toString();
	}

	
//	@Override 						// DEBUG purposes: uncomment "TP.add(_TP);"
//	public String toString() {
//		return "Sensitivity [classes=" + classes + ", TP=" + TP + ", P=" + P + ", senst_c=" + senst_c + ", senst_avg="
//				+ senst_avg + ", Ctest=" + Arrays.toString(Ctest) + "]";
//	}

}
