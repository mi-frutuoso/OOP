package main;

import classifierPackage.*;
import metricsPackage.*;
public class Main {

	public static void main(String[] args) {
//		Classifier<Integer> bayes = new Bayes<Integer>("bias-train.csv", "bias-test.csv", "MDL");
		Classifier bayes = new Bayes("bias-train.csv", "bias-test.csv", "LL");
		bayes.train();
		bayes.predict();
		System.out.print(bayes);
		Resume<String> resume = new Resume<String>("bias-test_v3.csv", bayes.results());
		resume.results();
		System.out.print(resume);
	}

}
