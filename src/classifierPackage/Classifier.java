package classifierPackage;

public interface Classifier {
	public void train();
	
	public void predict();
	
	public String[] results();
}
