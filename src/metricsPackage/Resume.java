package metricsPackage;

import filePackage.FileClass;
import filePackage.FileInterface;
/**
 * Encapsulates all available metrics into a single object, acting like a "remote control" of the metrics. 
 * <br> Responsible also for presenting the results computed by those metrics (the resume).
 */
public class Resume {
	private Metrics<?>[] resume;
	private String filename;
	private String[] results;
	
	/**
	 * 
	 * @param filename Name of the file containing the true classification values (test set).
	 * @param results Array containing the computed classification values (predicted classes) by a classifier.
	 */
	public Resume(String filename, String[] results){
		this.filename = filename;
		this.results = results;
	}
	
	/**
	 * Gathers all implemented metrics and calls their evaluation method, to compute their score(s).
	 */
	public void results() {
		resume = new Metrics[4];
		// translate classes into real values
		FileInterface fp = new FileClass(filename);
		String[] true_classes = fp.readClasses();
		// gather all implemented metrics
		resume[0] = new Accuracy<String>(true_classes, results);
		resume[1] = new Specificity<String>(true_classes, results);
		resume[2] = new Sensitivity<String>(true_classes, results);
		resume[3] = new F1Score<String>(true_classes, results);
		// each metric evaluates (computes the score(s))
		for(int i=0; i<resume.length; i++) {
			Metrics<?> r = resume[i];
			if(r!=null) r.evaluate();
		}
	}
	
	/**
	 * Prepares the correct output format, to be displayed on the terminal.
	 */
	@Override
	public String toString() {
		// print resume
		StringBuffer str = new StringBuffer();	// var where string is appended to
	    str.append("Resume:\t\t\t");
		for(int i=0; i<resume.length; i++) {
			Metrics<?> r = resume[i];
			if(r==null) continue;
			str.append(r);
			if(i != resume.length-1) str.append(", ");
		}
		return str.toString();
	}
}
