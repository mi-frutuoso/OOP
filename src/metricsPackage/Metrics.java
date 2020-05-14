package metricsPackage;
/**
 * Service that provides the evaluation of generic classifications.
 *
 * @param <T> Generic type that contains classification values.
 */
public interface Metrics<T> {
	/**
	 * Executes the evaluation of the Metric.
	 */
	public void evaluate();
}
