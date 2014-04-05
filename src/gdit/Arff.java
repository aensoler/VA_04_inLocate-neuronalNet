package gdit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arff {
	
	public static final String REGEX_ARFF = " ";
	public static final String REGEX_DATA = ",";
	
	private String relation;
	private String[] attributes;
	private double[][] data;
	private double[] minData;
	private double[] maxData;
	
	public Arff() {
		this.minData = null;
		this.maxData = null;
	}
	
	public static double[] min(double[] x, double[] y) {
		int len = Math.min(x.length, y.length);
		double[] result = new double[len];
		for (int i = 0; i < len; i++)
			result[i] = Math.min(x[i], y[i]);
		return result;
	}

	public static double[] max(double[] x, double[] y) {
		int len = Math.min(x.length, y.length);
		double[] result = new double[len];
		for (int i = 0; i < len; i++)
			result[i] = Math.max(x[i], y[i]);
		return result;
	}
	
	public double[] getMinData() {
		if (this.minData == null) {
			int i, j;
		
			this.minData = new double[this.data[0].length];
			for (i=0; i<this.minData.length; i++)
				this.minData[i] = Double.MAX_VALUE;
			if (this.data != null) {
				for (i=0; i<this.data.length; i++) {
					for (j=0; j<this.data[i].length; j++) {
						if (this.data[i][j] < this.minData[j]) this.minData[j] = this.data[i][j];
					}
				}
			} else {
				System.err.println("[Arff:getMinData] Data is null");
			}
		}
		return this.minData;
	}
	
	public double[] getMaxData() {
		if (this.maxData == null) {
			int i, j;
		
			this.maxData = new double[this.data[0].length];
			for (i=0; i<this.maxData.length; i++)
				this.maxData[i] = Double.MIN_VALUE;
			if (this.data != null) {
				for (i=0; i<this.data.length; i++) {
					for (j=0; j<this.data[i].length; j++) {
						if (this.data[i][j] > this.maxData[j]) this.maxData[j] = this.data[i][j];
					}
				}
			} else {
				System.err.println("[Arff:getMinData] Data is null");
			}
		}
		return this.maxData;
	}
	
	public void normalizeData(double[] min, double[] max) {
		
		int i, j;
		
		if (this.data != null) {
			for (i=0; i<this.data.length; i++) {
				for (j=0; j<this.data[i].length; j++) {
					this.data[i][j] = (this.data[i][j] - min[j])/(max[j] - min[j]);
				}
			}
		} else {
			System.err.println("[Arff:normalizeData] Data is null");
		}
	}
	
	public void readFromCsv(String filename) {
        
        File file = new File(filename);
        FileInputStream fis;
        DataInputStream dis;
        BufferedReader br;
        
        int i, j;
        String linea;
        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> row = null;

        String[] lineSplited;
        
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            
            if ((linea = br.readLine()) != null) {
            	this.attributes = linea.split(REGEX_DATA);
            }
            
            while ((linea = br.readLine()) != null) {

            	lineSplited = linea.split(REGEX_DATA);
            	row = new ArrayList<Double>(lineSplited.length);
            	
            	for (i=0; i<lineSplited.length; i++)
            		row.add(Double.parseDouble(lineSplited[i]));

            	data.add(row);
            }
            
            br.close();
            dis.close();
            fis.close();
            
            this.data = new double[data.size()][data.get(0).size()];
            for (i=0; i<data.size(); i++)
            	for (j=0; j<data.get(0).size(); j++)
            		this.data[i][j] = (double) data.get(i).get(j);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
	
	public void readFromArff(String filename) {
        
        File file = new File(filename);
        FileInputStream fis;
        DataInputStream dis;
        BufferedReader br;
        
        int i, j;
        String linea;
        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        ArrayList<String> attributes = new ArrayList<String>();
        ArrayList<Double> row = null;

        String[] lineSplited;
        
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            
            while ((linea = br.readLine()) != null) {

            	lineSplited = linea.split(REGEX_ARFF);
            	
            	if (lineSplited[0].equals("@relation")) this.relation = lineSplited[1];
            	else if (lineSplited[0].equals("@attribute")) attributes.add(lineSplited[1]);
            	else if (lineSplited[0].equals("@data")) {
            		
            		this.attributes = new String[attributes.size()];
            		for (i=0; i<attributes.size(); i++)
            			this.attributes[i] = attributes.get(i);
            		
            		while ((linea = br.readLine()) != null) {
            			if(!linea.equals("")) {
		        			lineSplited = linea.split(REGEX_DATA);
		        			
		        			row = new ArrayList<Double>(lineSplited.length);
		                	for (i=0; i<lineSplited.length; i++)
		                		row.add(Double.parseDouble(lineSplited[i]));
		
		                	data.add(row);
            			}            			
            		}
            	}

            }
            
            br.close();
            dis.close();
            fis.close();
            
            this.data = new double[data.size()][data.get(0).size()];
            for (i=0; i<data.size(); i++)
            	for (j=0; j<data.get(0).size(); j++)
            		this.data[i][j] = (double) data.get(i).get(j);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

	
	public void writeToCsv(String filename) {
        try {
          File file = new File(filename);
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          
          for (int i = 0; i < this.attributes.length; i++)
        	  output.write(this.attributes[i] + (i!=this.attributes.length-1 ? REGEX_DATA : ""));
          output.newLine();
          
          for (int i = 0; i < this.data.length; i++) {
        	  for (int j = 0; j < this.data[i].length; j++) {
            	  output.write(this.data[i][j] + (j!=this.data[i].length-1 ? REGEX_DATA : ""));
        	  }
        	  output.newLine();
          }
    	  output.flush();
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
	
	public void writeToArff(String filename) {
		try {
          File file = new File(filename);
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          
          output.write("@relation " + this.relation);
          output.newLine();
          output.newLine();
          
          for (int i = 0; i < this.attributes.length; i++) {
        	  output.write("@attribute " + this.attributes[i] + " numeric");
        	  output.newLine();
          }
          output.newLine();
          
          output.write("@data");
          output.newLine();
          for (int i = 0; i < this.data.length; i++) {
        	  for (int j = 0; j < this.data[i].length; j++) {
            	  output.write(this.data[i][j] + (j!=this.data[i].length-1 ? REGEX_DATA : ""));
        	  }
        	  output.newLine();
          }
    	  output.flush();
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
	
	public void printRelation() {
		System.out.println(this.relation);
	}
	
	public void printAttributes() {
		for (int i = 0; i < this.attributes.length; i++)
			System.out.print(this.attributes[i] + " ");
		System.out.println();
	}
	
	public void printData() {
		for (int i = 0; i < this.data.length; i++) {
			for (int j=0; j < this.data[i].length; j++)
				System.out.printf("%.4f ", this.data[i][j]);
			System.out.println();
		}
	}
	
	public String getRelation() {
		return relation;
	}

	
	public void setRelation(String relation) {
		this.relation = relation;
	}

	
	public String[] getAttributes() {
		return attributes;
	}

	
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	
	public double[][] getData() {
		return data;
	}

	
	public void setData(double[][] data) {
		this.data = data;
	}
}
