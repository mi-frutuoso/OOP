package classifierPackage;

import structuresPackage.TreeClass;
import translationPackage.*;
import structuresPackage.Tree;
import structuresPackage.Graph;
import structuresPackage.GraphClass;

import filePackage.*;

public class Bayes implements Classifier{

	private Tree tree;
	private Graph graph;
	private FileInterface file;
	private String train_file;
	private String test_file;
	private String score;
	private int[][][] matrix = new int[1][][];
	private int[][] max_values = new int[1][];
	//private String[] predictions;
	private Translator tr;
	private long train_time;
	private long test_time;
	
	public Bayes(String train_file, String test_file, String score) {
		this.train_file = train_file;
		this.test_file = test_file;
		this.score = score;
	}

	@Override
	public void train() {
		long start_time = System.nanoTime();
		tr = new IDTranslator();
		file = new FileClass(matrix, train_file, max_values, tr);
		file.readFile();
		graph = new GraphClass(matrix[0], max_values[0], score);
		graph.makeStruct();
		tree = new TreeClass(matrix[0], max_values[0], graph.returnNodes());
		tree.makeStruct();
		train_time = System.nanoTime() - start_time;
	}

	@Override
	public void predict() {
		long start_time = System.nanoTime();
		file = new FileClass(matrix, test_file, max_values, null);
		file.readFile();
		tree.predict(matrix[0]);
		test_time = System.nanoTime() - start_time;
	}

	@Override
	public String[] results() {
		return tr.reverse(tree.returnClassification());
	}
	
	@Override
	public String toString() {
		String[] classification = results();
		StringBuffer str = new StringBuffer();	// var where string is appended to
	    str.append("Classifier:\t\t");
	    str.append(tree);
	    str.append("Time to build:\t\t"+train_time+" ns\n");
	    str.append("Testing the classifier:\t\n");
		for(int i=0; i<classification.length; i++) str.append("-> instance " + i + ":\t\t" + classification[i]+"\n");
		str.append("Time to test:\t\t"+test_time+" ns\n");
		return str.toString();
	}

}
