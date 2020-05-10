package main;

import classifierPackage.*;
import metricsPackage.*;
public class Main {

	public static void main(String[] args) {
//		Classifier<Integer> bayes = new Bayes<Integer>("bias-train.csv", "bias-test.csv", "MDL");
		Classifier bayes = new Bayes("bias-train_v3.csv", "bias-test_v2.csv", "MDL");
		bayes.train();
		bayes.predict();
		Resume<String> resume = new Resume<String>("bias-test_v3.csv", bayes.results());
		resume.results();
	}

}
