package main;

import classifierPackage.*;
import metricsPackage.*;
public class Main {

	public static void main(String[] args) {
		if(args.length == 1 && args[0].compareTo("-h") == 0) {
			System.out.println("Usage:\n\t$ java -jar bayes.jar [train] [test] [score]\n\n\t-> [train]\t: path of the train set file\n\t-> [test]\t: path of the test set file\n\t-> [score]\t: \"LL\" or \"MDL\"\n");
			System.exit(1);
		}
		else if(args.length != 3) {
			System.out.println("Incorrect usage. Type \"java -jar bayes.jar -h\" for help.");
			System.exit(1);
		}
		else {
			if(args[2].compareTo("MDL")!=0 && args[2].compareTo("LL")!=0) {
				System.out.println("Unrecognized score. Type \"java -jar bayes.jar -h\" for help.");
				System.exit(1);
			}
			String train = args[0];
			String test = args[1];
			String score = args[2];
			Classifier bayes = new Bayes(train, test, score);
			bayes.train();
			bayes.predict();
			System.out.print(bayes);
			Resume resume = new Resume(test, bayes.results());
			resume.results();
			System.out.print(resume);
		}
//		Classifier<Integer> bayes = new Bayes<Integer>("bias-train.csv", "bias-test.csv", "MDL");
	}

}
