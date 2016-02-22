
package internal_index;

import java.util.List;
import java.util.Map;

public class Silhouette {
	public static void calculateCoff(Map<Integer, List<Integer>> clusters, double[][] matrix){
		double b = 0.0;
		List<Integer> list = null;
		for( int counter = 0; counter < clusters.size(); counter++){
			list = clusters.get(counter);
			
			for( Integer dataPoint : list ){
				double a = 0.0;
				for( Integer otherPoint : list ){
					a += calculateDistance(matrix[dataPoint-1] , matrix[otherPoint-1]);
				}
				
				a = a/list.size();
				
				for( int i = 0; i < clusters.size(); i++){
					if( i != counter ){
						
					}
				}
				
			}
		}
	}
	
	public static double calculateDistance(double[] dataPoint , double[] centroid){
		
		double distance = 0.0;
		for (int i = 0; i < dataPoint.length; i++) {
			distance += Math.pow(centroid[i] - dataPoint[i], 2);
		}
		
		return distance;
	}
}
