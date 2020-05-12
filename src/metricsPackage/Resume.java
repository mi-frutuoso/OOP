package metricsPackage;

import filePackage.FileClass;
import filePackage.FileInterface;

public class Resume<T> {
	private Metrics<?>[] resume = new Metrics[4];
	private String filename;
	private String[] results;
	
	public Resume(String filename, String[] results){
		this.filename = filename;
		this.results = results;
	}
	
	public void results() {
		FileInterface fp = new FileClass(filename);
		String[] true_classes = fp.readClasses();
		resume[0] = new Accuracy<String>(true_classes, results);
		resume[1] = new Specificity<String>(true_classes, results);
		resume[2] = new Sensitivity<String>(true_classes, results);
		resume[3] = new F1Score<String>(true_classes, results);
	}

	@Override
	public String toString() {
		// print resume
		StringBuffer str = new StringBuffer();	// var where string is appended to
	    str.append("Resume:\t\t\t");
		for(int i=0; i<resume.length; i++) {
			Metrics<?> r = resume[i];
			r.evaluate();
			str.append(r);
			if(i != resume.length-1) str.append(", ");
		}
		return str.toString();
	}
}
