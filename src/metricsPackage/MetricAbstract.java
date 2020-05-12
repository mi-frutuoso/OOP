package metricsPackage;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class MetricAbstract<T> implements Metrics<T>{
	/**
	 * List that stores generic classification values.
	 */
	ArrayList<T> classes = new ArrayList<T>();
	T[] Ctest;
	T[] res;
	ArrayList<Float> metricValues = new ArrayList<Float>();
	float avg;
	
	public MetricAbstract(T[] Ctest,T[] res){
		this.Ctest = Ctest;
		this.res = res;
	}
	/**
	 * Method that computes a given metric result, which implementation depends on the metric.
	 */
	public abstract void evaluate();
	
	/**
	 * This method allows to add an identified class from the test set to a list that stores all the classes.
	 * Instead of appending, it inserts each element maintaining the lexicographical order of the set of classes.
	 * This allows the ArrayList 'f1_c' to follow the same order and to present the results in a sorted way.
	 * 
	 * @param elem Generic class of the test set
	 */
	void insert(T elem){													// lexicographical order insertion
	    // iterate over classes[]
		int i = 0;
		Iterator<T> it = classes.iterator();
		while (it.hasNext()) {
			T temp = it.next();
			if (temp.toString().compareTo(elem.toString()) <= -1) {
				i++;
				continue; 														// keep searching
			}
	        classes.add(i, elem);												// insertion location found!
	        i++;
	        return;
		}
	    classes.add(elem);														// insert in the end
	}
	
	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();	// var where string is appended to
	    str.append("[");

	    // append scores for all classes
	    Iterator<T> it_c = classes.iterator();
	    Iterator<Float> it_s = metricValues.iterator();
		while (it_c.hasNext()) {
			T temp_c = it_c.next();
			Float temp_s = it_s.next();  
			str.append(""+ temp_c + ": "+ String.format("%.2f", temp_s)+", ");
		}
	    
	    // append last element (avg)
	    str.append(""+ String.format("%.2f", avg)+"]");
	    return str.toString();
	}
}
