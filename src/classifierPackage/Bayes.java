package classifierPackage;

import structuresPackage.TreeClass;
import translationPackage.*;
import structuresPackage.Tree;
import structuresPackage.Graph;
import structuresPackage.GraphClass;

import filePackage.*;

/**
 * 
 * Implementation of a tree augmented naive Bayes classifier (TAN).
 *
 */
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
	private StringBuffer features = new StringBuffer();
	private boolean debug;
	
	/**
	 * Constructs a Bayes classifier.
	 * 
	 * @param train_file String with the name of the file containing the training dataset.
	 * @param test_file String with the name of the file containing the test dataset.
	 * @param score String with the type of score to be used for the computation of the nodes parameters.
	 * @param debug Allows to perform debug or not.
	 */
	public Bayes(String train_file, String test_file, String score, boolean debug) {
		this.train_file = train_file;
		this.test_file = test_file;
		this.score = score;
		this.debug = debug;
	}

	/**
	 * Reads the contents of the train file and
	 * trains the classifier by building a supporting graph followed by a directed tree.<br>
	 * It computes the time spent building the classifier.
	 * It is able to handle classes not in numeric form by performing a translation.
	 */
	@Override
	public void train() {
		long start_time = System.nanoTime();
		tr = new IDTranslator();
		file = new FileClass(matrix, train_file, max_values, tr, features);
		file.readFile();
		graph = new GraphClass(matrix[0], max_values[0], score);
		graph.makeStruct();
		tree = new TreeClass(matrix[0], max_values[0], graph.returnNodes(), features.toString(), debug);
		tree.makeStruct();
		train_time = System.nanoTime() - start_time;
	}

	/**
	 * Predicts the outcome with the model built by the <b>predict</b> method for a given test file.<br>
	 * It also keeps track of the time spent predicting the results.
	 */
	@Override
	public void predict() {
		long start_time = System.nanoTime();
		file = new FileClass(matrix, test_file, max_values, null, null);
		file.readFile();
		tree.predict(matrix[0]);
		test_time = System.nanoTime() - start_time;
	}

	/**
	 * Returns the results predicted in <b>predict</b> as an array of Strings.
	 */
	@Override
	public String[] results() {
		return tr.reverse(tree.returnClassification());
	}
	
	/**
	 * Returns a String containing the structure of the originated tree, the time spent building the classifier, 
	 * the predicted results for the test dataset provided and the time spent performing the prediction.
	 */
	@Override
	public String toString() {
		String[] classification = results();
		StringBuffer str = new StringBuffer();	// var where string is appended to
	    str.append("Classifier:\t\t");
	    str.append(tree);
	    str.append("Time to build:\t\t"+train_time+" ns\n");
	    str.append("Testing the classifier:\t\n");
		for(int i=0; i<classification.length; i++) str.append("-> instance " + (i+1) + ":\t\t" + classification[i]+"\n");
		str.append("Time to test:\t\t"+test_time+" ns\n");
		return str.toString();
	}

}
