package translationPackage;

import java.util.ArrayList;
/**
 * Translator of classes into IDs (positive integers including zero).
 * Keeps a <i>seed</i> that allows to perform inverse translation of integers into the original classes' values.
 *
 */
public class IDTranslator implements Translator {
	private ArrayList<String> original;								// may contain duplicates
	private ArrayList<String> seed;									// 'original' without duplicates
	private int Nclasses;											// Number of classes identified
	/**
	 * 
	 * @param classes List of the (original) identified classes stored as strings.
	 */
	public IDTranslator(ArrayList<String> classes) {
		original = classes;
	}
	
	/**
	 * Translates list <b>classes</b> (given in constructor) into array of integers.
	 * Generates <b>seed</b> for further inverse translation.
	 */
	public int[] translate() {
		if(original.size() < 1) return null;
		int[] translation = new int[original.size()];
		int i = 0;
		seed = new ArrayList<String>(original.size());
		for(String c : original) {
			if(seed.contains(c)) translation[i]=seed.indexOf(c);
			else {
				seed.add(c);
				translation[i]=i;
				i++;
			}
		}
		Nclasses = i+1;
		return translation;
	}
	
	public String[] reverse(int[] predictions) {
		if (predictions.length < 1) return null;
		String[] rev_predictions = new String[predictions.length];
		for (int i = 0; i<predictions.length; i++)
			rev_predictions[i] = ((predictions[i] < Nclasses) ? seed.get(predictions[i]) : null);
		return rev_predictions;
	}
}
