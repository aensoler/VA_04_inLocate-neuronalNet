package gdit;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputHandler {
	
	//public static final int NUM_ATTRIBUTES = 6;
	public static final String REGEX = ",";

	public static double[][] readCSV(String filename) {
        
        File file = new File(filename);
        FileInputStream fis;
        DataInputStream dis;
        BufferedReader br;
        
        int i, j;
        String linea;
        ArrayList<ArrayList<Double>> csv = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> row = null;
        double[][] theReturn = null;

        String[] lineSplited;
        
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            
            while ((linea = br.readLine()) != null) {

            	lineSplited = linea.split(REGEX);
            	row = new ArrayList<Double>(lineSplited.length);
            	
            	for (i=0; i<lineSplited.length; i++)
            		row.add(Double.parseDouble(lineSplited[i]));

            	csv.add(row);
            }
            
            br.close();
            dis.close();
            fis.close();
            
            theReturn = new double[csv.size()][csv.get(0).size()];
            for (i=0; i<csv.size(); i++)
            	for (j=0; j<csv.get(0).size(); j++)
            		theReturn[i][j] = (double) csv.get(i).get(j);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        return theReturn;
    }	
}