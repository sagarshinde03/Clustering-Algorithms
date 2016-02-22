/**
 * 
 */
package external_index;

/**
 * @author Sagar Shinde
 *
 */
import java.util.List;
import java.util.Map;

public class ExternalCofficient {
	
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
	
	public static double[] calculateJaccordCoff(double[][] groundTruth , Map<Integer, List<Integer>> clusters){
		int M11 = 0;
		int M1001 = 0;
		int M00 = 0;
		
		double[][] clusterResult = processClusters(clusters, groundTruth.length);
		
		int[][] gTruth = getGroundTruthMatrix(groundTruth);
		int[][] solClusters = getGroundTruthMatrix(clusterResult);
		
		for( int counter = 0; counter < gTruth.length; counter++ ){
			for( int innerCounter = 0; innerCounter < gTruth.length; innerCounter++ ){
				if( gTruth[counter][innerCounter] == 1 && solClusters[counter][innerCounter] == 1 ){
					M11++;
				}
				else if( gTruth[counter][innerCounter] == 1 || solClusters[counter][innerCounter] == 1 ){
					M1001++;
				}
				else{
					M00++;
				}
			}
		}
		
		double[] coff = {(double)M11/(double)(M11+M1001) , (double)(M11+M00)/(double)(M11+M1001+M00)};
		
		return coff;
	}
	
	
	public static int[][] getGroundTruthMatrix( double[][] groundTruth ){
		int len = groundTruth.length; 
		int[][] matrix = new int[len][len];
		
		for( int counter = 0; counter < len; counter++ ){
			for( int innerCounter = 0; innerCounter < len; innerCounter++ ){
				if( groundTruth[counter][1] == groundTruth[innerCounter][1] ){
					matrix[counter][innerCounter] = 1;
					matrix[innerCounter][counter] = 1;
				}
				else{
					matrix[counter][innerCounter] = 0;
					matrix[innerCounter][counter] = 0;
				}
			}
		}
		
		return matrix;
	} 
}