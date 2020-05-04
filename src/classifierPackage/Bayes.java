package classifierPackage;

import structuresPackage.TreeClass;
import structuresPackage.Tree;
import structuresPackage.Graph;
import structuresPackage.GraphClass;
import filePackage.*;

public class Bayes<T> implements Classifier<T>{

	private Tree tree;
	private Graph graph;
	private FileInterface file;
	private String train_file;
	private String test_file;
	private String score;
	private int[][][] matrix = new int[1][][];
	private int[][] max_values = new int[1][];
	
	public Bayes(String train_file, String test_file, String score) {
		this.train_file = train_file;
		this.test_file = test_file;
		this.score = score;
	}

	@Override
	public void train() {
		file = new FileClass(matrix, train_file, max_values);
		file.readFile();
		graph = new GraphClass(matrix[0], max_values[0], score);
		graph.makeStruct();
	}

	@Override
	public void predict() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T results() {
		// TODO Auto-generated method stub
		return null;
	}

}
