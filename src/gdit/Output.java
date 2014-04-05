package gdit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Output {

	public static void printMatrix(double[][] matrix) {
		int i, j;
		for (i=0; i<matrix.length; i++) {
			for (j=0; j<matrix[i].length; j++)
				System.out.printf("%.4f ", matrix[i][j]);
			System.out.println("");
		}
	}
	
	public static void printVector(double[] vector) {
		int len = vector.length;
		int i;
		
		for (i=0; i<len; i++)
			System.out.printf("%.4f ", vector[i]);
		System.out.println();
	}
	
	public static void writeFile(String filename, ArrayList<String> lines) {
		int i;

        try {
          File file = new File(filename);
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          for (i=0; i<lines.size(); i++) {
        	  output.write(lines.get(i));
        	  output.newLine();
          }
    	  output.flush();
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
	
	public static void writeFormatArff(String filename, ArrayList<String> lines) {
		int i;
        try {
          File file = new File(filename);
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          
          for (i=0; i<lines.size(); i++) {
        	  output.write(lines.get(i));
        	  output.newLine();
          }
    	  output.flush();
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
}
