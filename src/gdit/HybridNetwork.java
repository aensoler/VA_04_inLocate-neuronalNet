package gdit;

import java.util.ArrayList;

public class HybridNetwork {
	
	private Arff training;
	private Arff test;
	private SimpleKMeans simpleKMeans;
	private double[][] centroidDistances;
	private double[] sigma;
	private RBF rbf;

	public Arff getTraining() {
		return training;
	}

	public void setTraining(Arff training) {
		this.training = training;
	}

	public Arff getTest() {
		return test;
	}

	public void setTest(Arff test) {
		this.test = test;
	}

	public SimpleKMeans getSimpleKMeans() {
		return simpleKMeans;
	}

	public void setSimpleKMeans(SimpleKMeans simpleKMeans) {
		this.simpleKMeans = simpleKMeans;
	}

	public double[][] getCentroidDistances() {
		return centroidDistances;
	}

	public void setCentroidDistances(double[][] centroidDistances) {
		this.centroidDistances = centroidDistances;
	}

	public double[] getSigma() {
		return sigma;
	}

	public void setSigma(double[] sigma) {
		this.sigma = sigma;
	}

	public RBF getRbf() {
		return rbf;
	}

	public void setRbf(RBF rbf) {
		this.rbf = rbf;
	}

	public void startLearning(String trainingFilename, String testFilename, int numberAttr, boolean normalize) {
		
		// Lectura
		this.training = new Arff();
		training.readFromCsv(trainingFilename);
		training.writeToArff(trainingFilename + ".arff");
		
		this.test = new Arff();
		test.readFromCsv(testFilename);
		test.writeToArff(testFilename + ".arff");
		
		// Preprocesado
		if (normalize) {
			double[] min = Arff.min(training.getMinData(), test.getMinData());
			double[] max = Arff.max(training.getMaxData(), test.getMaxData());
			training.normalizeData(min, max);
			test.normalizeData(min, max);
		}
		
		// Clustering
		this.simpleKMeans = new SimpleKMeans(trainingFilename + ".arff", testFilename + ".arff");
		ArrayList<String> result = this.simpleKMeans.start(3);
		Output.writeFile("simpleKMeansResult.txt", result);
		this.simpleKMeans.writeToCsv("centroids.csv");
		
		// RBF
		this.centroidDistances = RBF.distanciaCentroides(simpleKMeans.getCentroids());
		this.sigma = RBF.getSigma(this.centroidDistances);
		System.out.print("Sigma = ");
		Output.printVector(this.sigma);
		
		double[][] YA = RBF.functionOwner(this.training.getData(), this.simpleKMeans.getCentroids(), this.sigma);
		
		Output.printMatrix(YA);
		double[][] YB = RBF.functionOwner(this.test.getData(), this.simpleKMeans.getCentroids(), this.sigma);
		Output.printMatrix(YB);
		
		Perceptron perceptron = new Perceptron("a.arff", "b.arff");
		perceptron.training(YA, "4,5");
	}
	
	public double[] retrieveValue(double[] input) {
		double[][] data = {input};
		double[][] grade = RBF.functionOwner(data, this.simpleKMeans.getCentroids(), this.sigma);
		return data[0];
	}
	
}
