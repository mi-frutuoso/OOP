package main;


import classifierPackage.*;
import metricsPackage.*;

public class Main {

	public static void main(String[] args) {
		Classifier<Integer> bayes = new Bayes<Integer>("bias-train.csv", "bias-test.csv", "MDL");
		bayes.train();
		bayes.predict();
		Integer C_true[] = new Integer[4];
		C_true[0] = 1;
		C_true[1] = 11;
		C_true[2] = 0;
		C_true[3] = 2;
		
		Integer C_pred[] = new Integer[4];
		C_pred[0] = 0;
		C_pred[1] = 0;
		C_pred[2] = 1;
		C_pred[3] = 2;
		
		Metrics<Integer> acc = new Accuracy<Integer>(C_true);
		acc.evaluate(C_pred);
		System.out.println(acc);
		
		Character C_true2[] = new Character[4];
		C_true2[0] = 'C';
		C_true2[1] = 'B';
		C_true2[2] = 'A';
		C_true2[3] = 'C';
		
		Character C_pred2[] = new Character[4];
		C_pred2[0] = 'C';
		C_pred2[1] = 'B';
		C_pred2[2] = 'B';
		C_pred2[3] = 'C';
		
		Metrics<Character> acc2 = new Accuracy<Character>(C_true2);
		acc2.evaluate(C_pred2);
		System.out.println(acc2);
		
		Metrics<Integer> acc3 = new Sensitivity<Integer>(C_true);
		acc3.evaluate(C_pred);
		System.out.println(acc3);
		
		Metrics<Character> acc4 = new F1Score<Character>(C_true2);
		acc4.evaluate(C_pred2);
		System.out.println(acc4);
		
		Metrics<Integer> acc5 = new Specificity<Integer>(C_true);
		acc5.evaluate(C_pred);
		System.out.println(acc5);
		
		Metrics<Integer> acc6 = new F1Score<Integer>(C_true);
		acc6.evaluate(C_pred);
		System.out.println(acc6);
	}

}
