package structuresPackage;

public interface Tree extends Graph{
	public void predict(int[][] var);
	public int[] returnClassification();
	public void printStructure();
}
