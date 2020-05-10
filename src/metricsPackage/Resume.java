package metricsPackage;
import filePackage.FileClass;
import filePackage.FileInterface;

public class Resume<T> {
	private Metrics<?>[] resume = new Metrics[4];
	//FileInterface fp;
	String filename;
	String[] results;
	
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
		
		// print resume
		System.out.print("Resume:                   ");
		for(int i=0; i<resume.length; i++) {
			Metrics<?> r = resume[i];
			r.evaluate();
			if(i == resume.length-1) System.out.print(r);
			else System.out.print(r+", ");
		}
	}
}
