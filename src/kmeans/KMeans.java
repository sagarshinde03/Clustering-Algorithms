package kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans {
	
	public static double[][] dataMatrix;
	public static Map<Integer, List<Integer>> clusters = new HashMap<Integer, List<Integer>>();
	
	public KMeans() {
		
	}
	
	
	/*public static Map<Integer, List<Integer>> getClusters(){
		
		// get initial centroids
		
		// assign each point to clusters
		
		
		
		//1 calcultae new centroid
		
		//2 assign each point to clusters
		
		// check if points are changed if changed repeat steps 1, 2 unitl no point changes
		
		
		return null;
	}*/
	
	
	public static Map<Integer, List<Integer>> generateClusters(int k, double[][] matrix ){
		dataMatrix = matrix;
		double[][] clusterCentroids = null;
		
		if( clusterCentroids == null ){
			clusterCentroids = genrateInitialCentroids(k, dataMatrix);
		}
		
		double oldSSE = assignPointsToClusters(clusterCentroids);
		double newSSE = 0.0;
		int iterations = 0;
		while( !isConverged(oldSSE, newSSE) ){// IsConverged should be a method
			iterations++;
			clusterCentroids = calculateCentroids();
			oldSSE = newSSE;
			newSSE = assignPointsToClusters(clusterCentroids);
		}
		
		System.out.println("Iterations: "+iterations);
		return clusters;
	}
	
	
	
	/*public static void processMatrix(double[][] matrix){
		dataMatrix = new double[matrix.length][matrix[0].length-2];
		
		for( int counter = 2; counter < matrix[0].length; counter++ ){
			dataMatrix[]
		}
	}*/
	
	public static boolean isConverged(double oldSSE, double newSSE){
		if( Math.abs(oldSSE-newSSE) > 0 )
			return false;
		return true;
	}
	
	
	public static double[][] calculateCentroids(){
		double[][] centroids = new double[clusters.size()][dataMatrix[0].length];
		List<Integer> list = null;
		
		for( Integer cluster : clusters.keySet() ){
			list = clusters.get(cluster);
			
			for( Integer dataPoint : list ){
				for( int counter = 0; counter < centroids.length; counter++ ){
					centroids[cluster-1][counter] += dataMatrix[dataPoint-1][counter];
				}
			}
			
			for( int counter = 0; counter < centroids.length; counter++ ){
				centroids[cluster-1][counter] /= list.size();
			}
		}
		
		return centroids;
	}
	
	
	public static double[][] genrateInitialCentroids(int k , double[][] dataMatrix){
		double[][] initialCentroids = new double[k][dataMatrix[0].length];
		Random random = new Random();
		
		for( int counter = 0; counter < k; counter++){
			int randomNum = random.nextInt(dataMatrix.length);
			initialCentroids[counter] = dataMatrix[randomNum];
		}
		
		return initialCentroids;
	}
	
	
	
	public static double assignPointsToClusters(double[][] clusterCentroids){
		List<Integer> list = null;
		double sse = 0.0;
		
		Map<Integer, List<Integer>> newClusters = new HashMap<Integer, List<Integer>>();
		for( int counter = 0; counter < dataMatrix.length; counter++  ){
			double minDistance = Double.MAX_VALUE;
			int cluster = 0;
			
			for( int innerCounter = 0; innerCounter < clusterCentroids.length; innerCounter++){
				
				double distance = calculateDistance(dataMatrix[counter] , clusterCentroids[innerCounter]);
				sse += distance;
				
				double curDistance = Math.sqrt(distance);
				if( curDistance < minDistance ){
					minDistance = curDistance;
					cluster = innerCounter+1;
				}
			}
			
			
			if( newClusters.containsKey(cluster) ){
				list = newClusters.get(cluster);
			}
			else{
				list = new ArrayList<>();
			}
			
			list.add(counter+1);
			newClusters.put(cluster, list);
		}
		
		clusters = newClusters;
		
		return sse;
	}
	
	
	public static double calculateDistance(double[] dataPoint , double[] centroid){
		
		double distance = 0.0;
		for (int i = 0; i < dataPoint.length; i++) {
			distance += Math.pow(centroid[i] - dataPoint[i], 2);
		}
		
		return distance;
	}
	
}
