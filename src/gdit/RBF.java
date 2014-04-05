package gdit;


public class RBF {

	public static double distanciaEuclidia(double[] x, double[] y) {
		int len = Math.min(x.length, y.length);
		double sum = 0;
        for (int i=0; i<len ; i++) 
            sum += Math.pow(x[i] - y[i], 2); 
        return Math.sqrt(sum);
	}
	
	public static double[][] distanciaCentroides(double[][] centroides) {

		int i, j, k;
		int lenI = centroides.length;
		int lenJ = centroides[0].length;
		
		double[][] distancias = new double[lenJ][lenJ];
		
		for (i=0; i<lenJ; i++) {
			for (j=0; j<lenJ; j++) {
				distancias[i][j] = RBF.distanciaEuclidia(
						RBF.getColumn(centroides, i),
						RBF.getColumn(centroides, j)
				);
			}
		}
		
		return distancias;
		
	}
	
	public static double[] getSigma(double[][] matrix) {
		int len = matrix.length;
		int i, j;
		double sum;
		double[] sigma = new double[len];
		
		for (i=0; i<len; i++) {
			sum = 0;
			for (j=0; j<len; j++) {
				sum += matrix[i][j];
			}
			sigma[i] = sum/(len-1);
		}
		
		return sigma;
				
	}
	
	public static double[] getColumn(double[][] matrix, int columnIndex) {
		int i;
		double[] column = new double[matrix.length];
		for (i=0; i<matrix.length; i++) {
			column[i] = matrix[i][columnIndex];
		}
		return column;
	}
	
	public static double[][] transposeMatrix(double [][] matrix){
		int lenI = matrix[0].length;
		int lenJ = matrix.length;
        double[][] theResult = new double[lenI][lenJ];
        int i, j;
        for (i=0; i<lenJ; i++)
            for (j=0; j<lenI; j++)
            theResult[j][i] = matrix[i][j];
        return theResult;
    }
	
	public static double[][] functionOwner(double[][] data, double[][] centroids, double[] sigma) {
		
		int i, j;
		int lenI = data.length;
		int lenJ = centroids[0].length;
		
		double[][] theReturn = new double[lenI][lenJ];
		double exponent;
		double r;

		for (i=0; i<lenI; i++) {
			for (j=0; j<lenJ; j++) {
				//r = RBF.distanciaEuclidia(data[i], RBF.getColumn(RBF.transposeMatrix(centroids),j));
				//r = RBF.distanciaEuclidia(data[i], RBF.getColumn(centroids,j));
				//r = RBF.distanciaEuclidia(data[i], centroids[j]);
				r = RBF.distanciaEuclidia(data[i], RBF.transposeMatrix(centroids)[j]);
				
				exponent = -(Math.pow(r,2))/(2*Math.pow(sigma[j],2)); 
				//exponent = -r/(2*Math.pow(sigma[j],2));
				
				theReturn[i][j] = Math.exp(exponent);
			}
		}
		
		return theReturn;
	}
	
	//public static 
		
	public RBF(double[][] data, double[][] test, double[][] centroids) {

		double[][] distance = distanciaCentroides(centroids);
		double[] sigma = getSigma(distance);
		
		//Output.printMatrix(distancia);
		//Output.printVector(getSigma(distancia));
	}
	
}
