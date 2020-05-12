package main;

import java.io.File;

import classifierPackage.*;
import metricsPackage.*;
/**
 * Main class of the application.
 *
 */
public class Main {
	/**
	 * Main function.
	 * @param args Arguments passed by the user in the command line.
	 */
	public static void main(String[] args) {
		if(validUI(args)) {
			String train = args[0];
			String test = args[1];
			String score = args[2];
//			Classifier<Integer> bayes = new Bayes<Integer>("bias-train.csv", "bias-test.csv", "MDL");
			Classifier bayes = new Bayes(train, test, score);
			bayes.train();
			bayes.predict();
			System.out.print(bayes);
			Resume resume = new Resume(test, bayes.results());
			resume.results();
			System.out.print(resume);
			
		}
		else System.exit(1);
	}
	
	/**
	 * Validate user inputs.
	 * @param args Arguments passed by the user in the command line.
	 * @return True if inputs are valid, false otherwise.
	 */
	private static boolean validUI(String[] args) {
		
		if(args.length == 1 && args[0].compareTo("-h") == 0) {
			System.out.println("Usage:\n\t$ java -jar bayes.jar [train] [test] [score]\n\n\t-> [train]\t: path of the train set file\n\t-> [test]\t: path of the test set file\n\t-> [score]\t: \"LL\" or \"MDL\"\n");
			return false;
		}
		else if(args.length != 3) {
			System.out.println("Incorrect usage. Type \"java -jar bayes.jar -h\" for help.");
			return false;
		}
		else {
			File tmpDir = new File(args[0]);
			if(!tmpDir.exists()) {
				System.out.println("Input train file not found.");
				return false;
			}
			tmpDir = new File(args[1]);
			if(!tmpDir.exists()) {
				System.out.println("Input test file not found.");
				return false;
			}
			
			if(args[2].compareTo("MDL")!=0 && args[2].compareTo("LL")!=0) {
				System.out.println("Unrecognized score. Type \"java -jar bayes.jar -h\" for help.");
				return false;
			}
		}
		return true;
	}

}
