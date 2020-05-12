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
		String[] classification = tr.reverse(tree.returnClassification());
		System.out.print("Classifier:\t\t");
		//tree.printStructure();
		System.out.print(tree);
		System.out.println("Time to build:\t\t"+train_time+" ns");
		System.out.println("Testing the classifier:     ");
		for(int i=0; i<classification.length; i++) System.out.println("-> instance " + i + ":\t\t" + classification[i]);
		System.out.println("Time to test:\t\t"+test_time+" ns");
		return classification;
	}

}
