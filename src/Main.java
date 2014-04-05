import java.util.ArrayList;

import gdit.Arff;
import gdit.HybridNetwork;
import gdit.InputHandler;
import gdit.Output;
import gdit.RBF;
import gdit.SimpleKMeans;
import gdit.WekaHandler;



public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		HybridNetwork myHybrid = new HybridNetwork();
		myHybrid.startLearning("ENTRE.csv", "ENTRE.csv", 3, true);
		
		

		//RBF mRbf = new RBF();
		/*double[][] data = Input.readCSV("An.csv");
		double[][] test = Input.readCSV("Bn.csv");
		double[][] centroids = Input.readCSV("C.csv");*/
		
		//RBF mRbf = new RBF();
		
		/*double[][] distancias = RBF.distanciaCentroides(centroids);
		double[] sigma = RBF.getSigma(distancias);
		System.out.print("Sigma = ");
		Output.printVector(sigma);
		System.out.println();
		double[][] YA = RBF.functionOwner(data, centroids, sigma);
		double[][] YB = RBF.functionOwner(test, centroids, sigma);*/

		//Output.printMatrix(YA);
		//Output.printMatrix(YB);
		//System.out.println(YA.length+" "+YA[0].length);
		//Output.printMatrix(data);
		//Output.printVector(data[0]);
		//Output.printMatrix(data);
		//Output.printVector(getColumn(data, 0));
		
		//WekaHandler weka = new WekaHandler();
		//WekaHandler.convertCSVtoARFF("C.csv", "aaa");
		//WekaHandler.convertARFFtoCSV("aaa", "bbb");
		//WekaHandler.convertCSVtoARFF("A1.csv", "A1");
		//WekaHandler.doSimpleKMedias("A1.arff");
		
//		Arff training = new Arff();
//		training.readFromCsv("a.csv");
//		training.writeToArff("a.arff");
//		
//		Arff test = new Arff();
//		test.readFromCsv("b.csv");
//		test.writeToArff("b.arff");
//		
//		SimpleKMeans simpleKMeans = new SimpleKMeans("a.arff", "b.arff");
//		ArrayList<String> result = simpleKMeans.start(6);
//		Output.writeFile("simpleKMeansResult.txt", result);
//		simpleKMeans.writeToCsv("centroids.csv");
//		
//		double[][] distancias = RBF.distanciaCentroides(simpleKMeans.getCentroids());
//		double[] sigma = RBF.getSigma(distancias);
//		System.out.print("Sigma = ");
//		Output.printVector(sigma);
//		System.out.println();
//		
//		double[][] YA = RBF.functionOwner(training.getData(), simpleKMeans.getCentroids(), sigma);
//		double[][] YB = RBF.functionOwner(test.getData(), simpleKMeans.getCentroids(), sigma);
		
		
		
		//WekaHandler.doSimpleKMedias(6,"A1.csv.arff");
		
		//WekaHandler.runCommand("help");
		//Output.printMatrix(c);

	}

}
