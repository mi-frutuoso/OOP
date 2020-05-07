package structuresPackage;

public interface Node {
	public void computeAlpha();
	public void computeTheta();
	public double[] returnAlpha();
	public double returnProbability(int parent_value, int node_value, int class_value);
}
