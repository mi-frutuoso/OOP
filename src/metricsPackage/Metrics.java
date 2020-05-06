package metricsPackage;

public interface Metrics<T> {
	void evaluate(T[] res);
}
