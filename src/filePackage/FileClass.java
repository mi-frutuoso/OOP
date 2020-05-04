package filePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileClass implements FileInterface{
	
	private int[][][] matrix;
	private int[][] max_values;
	private String filename;
	
	public FileClass(int[][][] arg_matrix, String filename, int[][] max_values) {
		this.matrix = arg_matrix;
		this.filename = filename;
		this.max_values = max_values;
	}

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
		    	  
		    	myReader = new Scanner(myObj);
		    	int j = 0;
		    	if(myReader.hasNextLine()) {
		    		myReader.nextLine();
		    	}
		        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          for(int i = 0; i < num_vars; i++) {
		        	  matrix[0][j][i] = Integer.parseInt(data.split(",")[i]);
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
		      }
		    } catch (FileNotFoundException e) {
		      System.out.println("File not found. Terminating execution.");
		      System.exit(1);
		      //e.printStackTrace();
		    }
	}

}
