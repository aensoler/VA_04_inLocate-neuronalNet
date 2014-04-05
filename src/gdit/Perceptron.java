package gdit;

import java.util.ArrayList;

public class Perceptron {
	
	private Arff training;
	private Arff test;
	private String trainingFilename;
	private String testFilename;
	
	public Perceptron(String trainingFilename, String testFilename) {
		this.trainingFilename = trainingFilename;
		this.testFilename = testFilename;
	}

	public void training(double[][] gradeFunction, String remove) {
		ArrayList<String> result = WekaHandler.runCommand(
			"weka.classifiers.functions.MultilayerPerceptron -L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a -R"
			+ " -t " + this.trainingFilename
			+ (this.testFilename!=null ? " -T " + this.testFilename : "")
			+ " > andsk", false
		);
		
		for(int i = 0; i<result.size(); i++) {
			System.out.println(result.get(i));
		}
	}
}
