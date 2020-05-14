package metricsPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Abstract metric, that offers some useful functions, such as sortClasses() and toString() for formatting the 
 * results presentation, and attributes, that can be commonly shared by different implementations of metrics.
 * @param <T> Generic classification type.
 */
public abstract class MetricAbstract<T> implements Metrics<T>{
	/**
	 * List that stores generic classification values (classes).
	 */
	ArrayList<T> classes = new ArrayList<T>();
	T[] Ctest;
	T[] res;
	/**
	 * List that stores the metric value (score) computed by the metric's evaluate() algorithm.
	 */
	ArrayList<Float> metricValues = new ArrayList<Float>();
	/**
	 * Stores the metric's weighted average.
	 */
	float avg;
	
	/**
	 * 
	 * @param Ctest Array containing the true classification values.
	 * @param res Array containing the predicted classification values (that is, the obtained result).
	 */
	public MetricAbstract(T[] Ctest,T[] res){
		this.Ctest = Ctest;
		this.res = res;
	}
	/**
	 * Method that computes a given metric result, which implementation depends on the metric algorithm.
	 */
	public abstract void evaluate();
	
	/**
	 * Sorting the identified classes from the test set allows the metric's ArrayList <b>metricValues</b> to
	 * store its values numerically and/or alphabetically. Results can be then presented in a sorted way.
	 * 
	 */
	void sortClasses() {
		Collections.sort(classes, new TComparator<T>());
	}

	/**
	 * Prepares the correct output format, to be displayed in the terminal.
	 */
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
