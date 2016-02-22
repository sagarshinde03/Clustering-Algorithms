package utils;

import java.util.List;

public class Utils {
	
	public static double[] convertToArray(List<Double> data){
		double[] list = new double[data.size()];
		int i = 0;
		for( Double val : data ){
			list[i] = (double)val;
			i++;
		}
		
		return list;
	}
	
	public static double calcPCorr(List<Double> list1 , List<Double> list2){
		double mean1 = calculateMean(list1);
		double mean2 = calculateMean(list2);
		
		double corr = 0.0;
		
		double cov = 0.0;
		for( int counter = 0; counter < list1.size(); counter++ ){
			cov += (list1.get(counter)-mean1) * (list2.get(counter)-mean2);
		}
		
		corr = cov / Math.sqrt(calculateVariance(mean1, list1)*(list1.size()-1)*calculateVariance(mean2, list2)*(list2.size()-1));
		
		return corr;
	}
	
	public static double calculateMean(List<Double> values){
		double sum = 0.0;
		for( Double val : values ){
			sum += val;
		}
		
		return sum/values.size();
	}
	
	
	public static double calculateVariance(double mean , List<Double> list){
		double var = 0.0;
		for( Double val : list ){
			var += Math.pow(val - mean, 2);
		}
		
		return var/(list.size()-1);
	}
}
