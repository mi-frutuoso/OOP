package filePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.Scanner;
import translationPackage.*;

/**
 * Reads a training or test file into an Integer Matrix, also calculates the maximum of each set of features.
 * 
 */
public class FileClass implements FileInterface{
	
	private int[][][] matrix;
	private int[][] max_values;
	private String filename;
	private String[] classes;
	private Translator tr;
	private StringBuffer Xlabels;

	/**
	 * Constructs a training/test file reader.
	 * <br>
	 * Files must have (n - 1) comma separated columns of values (X) and a last column with the <i>class</i> (C).
	 * Arrays must have a one element first dimension, so that changing the remaining dimensions inside the object will affect the remaining dimensions outside the object. 
	 * 
 	 * @param arg_matrix Three-dimensional matrix in the format [1][N][N], contains file contents. 
	 * @param filename Canonical or relative path to the training/test file.
	 * @param max_values Two-dimensional matrix in the format [1][N], contains maximum of each set of features.
	 * @param classes Array containing the classes column, converted to strings. 
	 */
	public FileClass(int[][][] arg_matrix, String filename, int[][] max_values, Translator tr, StringBuffer Xlabels) {
		this.matrix = arg_matrix;
		this.filename = filename;
		this.max_values = max_values;
		this.tr = tr;
		this.Xlabels = Xlabels;
	}
	
	/**
	 * Constructs a training/test file reader.
	 * <br>
	 * Files must have (n - 1) comma separated columns of values (X) and a last column with the <i>class</i> (C).
	 * 
	 * @param filename Canonical or relative path to the training/test file.
	 */
	public FileClass(String filename) {
		this.filename = filename;
	}

	/**
	 * Call to fill <b>matrix</b> (given in constructor) with file (<b>filename</b>) contents.
	 */
	@Override
	public void readFile() {
		try {
			File myObj = new File(filename);
		    Scanner myReader = new Scanner(myObj);
		      
		    if (myReader.hasNextLine()) {
		    	String[] data_array = (myReader.nextLine()).split(",");
		    	int num_vars = data_array.length;
		    	int lines = 0;
		    	  
		    	while(myReader.hasNextLine()) {
		    		myReader.nextLine();
		    		lines++;
		    	}
		    	
		    	myReader.close();
		    	matrix[0] = new int[lines][num_vars];
		    	max_values[0] = new int[num_vars];
		    	classes = new String[lines];
		    	 
		    	myReader = new Scanner(myObj);
		    	int j = 0;
		    	if(myReader.hasNextLine()) {
		    		String vars = myReader.nextLine();
		    		if(Xlabels != null) Xlabels.append(vars);			// copy feature labels to stringbuffer
		    	}
		        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          for(int i = 0; i < num_vars; i++) {
		        	  if(i == (num_vars-1))
		        		  classes[j] = data.split(",")[i];
		        	  else {
		        		  try {
		        			  matrix[0][j][i] = Integer.parseInt(data.split(",")[i]);		        			  
		        		  }
		        		  catch (NumberFormatException e) {
		        			  System.out.println("Invalid data value. Terminating execution.");
		        			  System.exit(1);
		        		  }
		        	  }
		        	  
		        	  if(j == 0) {
		        		  max_values[0][i] = matrix[0][j][i];
		        	  }
		        	  else {
		        		  if(matrix[0][j][i] > max_values[0][i]) {
		        			  max_values[0][i] = matrix[0][j][i];
		        		  }
		        	  }
		          }
		          j++;
		        }
		        myReader.close();
		        
		        // if no Translator provided, our job is done here (that is, we're only reading test set)
		        if(tr == null) return;
		        // else, we proceed
		        // translate classes into generic values
		        int[] intClasses = tr.translate(classes);

		        // check translation array length
		        if(intClasses.length != lines) {
		        	System.out.println("Incorrect translation output. Terminating execution.");
		        	System.exit(1);
		        }
		        
		        // update matrix values
		        int max_value_C = intClasses[0];	// stores max_value for the class (max_values[0][num_vars-1])
		        for(int i=0;i<lines;i++) {
		        	matrix[0][i][num_vars-1] = intClasses[i];
		        	if(intClasses[i] > max_value_C) max_value_C = intClasses[i];
		        }
		        
		        // finally, update max_values value for the class
		        max_values[0][num_vars-1] = max_value_C;
		      }
	    } catch (FileNotFoundException e) {
		      System.out.println("File not found. Terminating execution.");
		      System.exit(1);
		      //e.printStackTrace();
	    }
	}

	/**
	 * Returns the column correspondent to the classes of an input dataset file.
	 */
	@Override
	public String[] readClasses() {
		try {
			File myObj = new File(filename);
		    Scanner myReader = new Scanner(myObj);
		      
		    if (myReader.hasNextLine()) {
		    	String[] data_array = (myReader.nextLine()).split(",");
		    	int num_vars = data_array.length;
		    	int lines = 0;
		    	  
		    	while(myReader.hasNextLine()) {
		    		myReader.nextLine();
		    		lines++;
		    	}
		    	
		    	myReader.close();
		    	classes = new String[lines];
		    	  
		    	myReader = new Scanner(myObj);
		    	int j = 0;
		    	if(myReader.hasNextLine()) {
		    		myReader.nextLine();
		    	}
		        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          classes[j] = data.split(",")[num_vars-1];
		          j++;
		        }
		        myReader.close();
		      }
	    } catch (FileNotFoundException e) {
		      System.out.println("File not found. Terminating execution.");
		      System.exit(1);
		      //e.printStackTrace();
	    }
		return classes;
	}
	
	
}
