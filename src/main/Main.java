package main;

import classifierPackage.*;

public class Main {

	public static void main(String[] args) {
		Classifier<Integer> bayes = new Bayes<Integer>("bias-train.csv", "", "MDL");
		bayes.train();
	}

}
