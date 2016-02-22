
package internal_index;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class Correlation {
	
	
	public static double calculateCorrelation(double[][] distanceMatrix, Map<Integer, List<Integer>> clusters , double[][] groundTruth) {
		int newLen = (distanceMatrix.length * distanceMatrix[0].length) - (distanceMatrix.length + distanceMatrix[0].length) + 1;
		Double[] inputDistance = new Double[newLen];
		Double[] clusterMatrix = new Double[newLen];
		int count = 0;
		
		for( int counter = 1; counter < distanceMatrix.length; counter++ ){
			for( int innerCounter = 1; innerCounter < distanceMatrix[0].length; innerCounter++ ){
				if( counter == innerCounter ){
					inputDistance[count] = 0.0;
				}
				else{
					inputDistance[count] = distanceMatrix[counter][innerCounter];
				}
				count++;
			}
		}
		
		double[][] clusterResult = processClusters(clusters, groundTruth.length);
		//int[][] gTruth = getGroundTruthMatrix(groundTruth);
		double[][] solClusters = getGroundTruthMatrix(clusterResult);
		
		count = 0;
		for( int counter = 0; counter < solClusters.length; counter++ ){
			for( int innerCounter = 0; innerCounter < solClusters[0].length; innerCounter++ ){
				clusterMatrix[count] = solClusters[counter][innerCounter];
				count++;
			}
		}
		
		
		double corr = Utils.calcPCorr(Arrays.asList(inputDistance), Arrays.asList(clusterMatrix));
		return corr;
	}
	
	
	public static double[][] processClusters(Map<Integer, List<Integer>> clusters, int size){
		double[][] clustersResult = new double[size][2];
		
		for( Integer key : clusters.keySet() ){
			List<Integer> list = clusters.get(key);
			for(Integer dataPoint : list){
				clustersResult[dataPoint-1][0] = dataPoint;
				clustersResult[dataPoint-1][1] = key;
			}
		}
		
		return clustersResult;
	}
	
	public static double[][] getGroundTruthMatrix( double[][] groundTruth ){
		int len = groundTruth.length; 
		double[][] matrix = new double[len][len];
		
		for( int counter = 0; counter < len; counter++ ){
			for( int innerCounter = 0; innerCounter < len; innerCounter++ ){
				if( groundTruth[counter][1] == groundTruth[innerCounter][1] ){
					matrix[counter][innerCounter] = 1.0;
					matrix[innerCounter][counter] = 1.0;
				}
				else{
					matrix[counter][innerCounter] = 0.0;
					matrix[innerCounter][counter] = 0.0;
				}
			}
		}
		
		return matrix;
	} 
	
}
