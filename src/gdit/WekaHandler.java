package gdit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WekaHandler {

	public static final String WEKA_JAR_PATH = "C:\\Program Files\\Weka-3-6\\weka.jar";
	public static final String PREFIX = "java -classpath \""+WEKA_JAR_PATH+"\" ";
	public static final String PWD = "C:\\Users\\user\\workspace\\gdit\\";
	
	public static String getEnv(String filename) {
		return "\"" + PWD + filename + "\"";
	}
	
	public static ArrayList<String> runCommand(String command, boolean wait) {
		BufferedReader bufferInput, bufferError;
		String line = "";
		ArrayList<String> theReturn = new ArrayList<String>();
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			System.out.println("RUNNING: " + PREFIX+command);
			process = runtime.exec(PREFIX+command);

			if (wait) {
				process.waitFor();
	
				bufferInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((line = bufferInput.readLine()) != null) {
					theReturn.add(line);
				}
				
				bufferError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				while ((line = bufferError.readLine()) != null) {
					System.err.println(line);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return theReturn;
	}
	
	public static void convertCSVtoARFF(String inputFilename, String outputFilename) {
		ArrayList<String> result = runCommand("weka.core.converters.CSVLoader " + inputFilename, true);
		Output.writeFile(outputFilename, result);
	}
	
	public static void convertARFFtoCSV(String inputFilename, String outputFilename) {
		ArrayList<String> result = runCommand("weka.core.converters.CSVSaver -i " + inputFilename, true);
		Output.writeFile(outputFilename, result);
	}
	
	public static void doSimpleKMedias(int numberClusters, String inputFilename) {
		ArrayList<String> result = runCommand("weka.clusterers.SimpleKMeans -N "
				+ numberClusters
				+ " -A \"weka.core.EuclideanDistance -R first-last\" -I 500 -S 10 -t " + inputFilename, true);
	}
}
