package classifierPackage;

public interface Classifier<T> {
	public void train();
	
	public void predict();
	
	public T results();
}
