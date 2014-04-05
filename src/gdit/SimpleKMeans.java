package gdit;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleKMeans {
	
	public static final String REGEX_DATA = ",";
	
	private double[][] centroids;

	private String trainingArff;
	private String testArff;
	
	public SimpleKMeans(String trainingArff) {
		this.trainingArff = trainingArff;
		this.testArff = null;
	}
	
	public SimpleKMeans(String trainingArff, String testArff) {
		this.trainingArff = trainingArff;
		this.testArff = testArff;
	}
	
	private void retrieveCentroids(ArrayList<String> lines) {
		String line;
		String[] lineSplited;
		int inside = 2;
		int lenI, lenJ;
		ArrayList<ArrayList<Double>> centroids = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> row;
		
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (line != null && !line.equals("")) {
				if (line.charAt(0) == '=') {
					inside--;
					continue;
				}
				if (inside == 0) {
					lineSplited = line.split("\\s+");
					row = new ArrayList<Double>();
					for(int j=2; j<lineSplited.length; j++) {
						row.add(Double.parseDouble(lineSplited[j]));
					}
					centroids.add(row);
				}	
			}
		}
		
		lenI = centroids.size();
		lenJ = centroids.get(0).size();
		
		this.centroids = new double[lenI][lenJ];
		for (int i = 0; i < lenI; i++) {
			for (int j = 0; j < lenJ; j++) {
				this.centroids[i][j] = centroids.get(i).get(j);
			}
		}
	}
	
	public ArrayList<String> start(int numberClusters) {
		ArrayList<String> result = WekaHandler.runCommand(
			"weka.clusterers.FilteredClusterer"
			+ " -F \"weka.filters.unsupervised.attribute.Remove -R 3-5\""
			+ " -t " + this.trainingArff
			+ (this.testArff!=null ? " -T " + this.testArff : "")
			+ " -W weka.clusterers.SimpleKMeans -- -N "
			+ numberClusters
			+ " -A \"weka.core.EuclideanDistance -R first-last\" -I 500 -S 10"
		, true);
				
		retrieveCentroids(result);
		
		return result;
	}

	
	public void writeToCsv(String filename) {
        try {
          File file = new File(filename);
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          
          for (int i = 0; i < this.centroids.length; i++) {
        	  for (int j = 0; j < this.centroids[i].length; j++) {
            	  output.write(this.centroids[i][j] + (j!=this.centroids[i].length-1 ? REGEX_DATA : ""));
        	  }
        	  output.newLine();
          }
    	  output.flush();
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}
	
	public double[][] getCentroids() {
		return centroids;
	}

	public void setCentroids(double[][] centroids) {
		this.centroids = centroids;
	}

	public String getTrainingArff() {
		return trainingArff;
	}

	public void setTrainingArff(String trainingArff) {
		this.trainingArff = trainingArff;
	}

	public String getTestArff() {
		return testArff;
	}

	public void setTestArff(String testArff) {
		this.testArff = testArff;
	}	
		
}
