/**
 * 
 */

/**
 * @author Sagar Shinde
 *
 */
import internal_index.Correlation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import DBScan.DBClustering;
import DistanceMatrix.DistanceMatrix;
import HierarchicalClustering.Hierarchical;
import external_index.ExternalCofficient;
import kmeans.KMeans;

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the name of the file you want to process and hit enter key.");
		String fileName=br.readLine();
		fileName=getCorrectFileName(fileName);
		System.out.println("How many clusters do you want to make ?");
		int numClusters=Integer.parseInt(br.readLine());
		String[][] fileData=DistanceMatrix.scanFile(fileName);
		double[][] distanceMatrix=DistanceMatrix.getDistanceMatrix(fileData);
		Hierarchical h=new Hierarchical();
		h.startClustering(fileData, distanceMatrix,numClusters);
		HashMap<Integer,ArrayList<Integer>> clustersSinglesLinkHierarchical=Hierarchical.clustersSinglesLink;
		
		double[][] matrix = DistanceMatrix.parseDoubleMatrix(fileData);
		Map<Integer, List<Integer>> clustersKMeans = KMeans.generateClusters(numClusters , matrix);
		double[] extIndex = ExternalCofficient.calculateJaccordCoff(DistanceMatrix.getGroundTruth(), clustersKMeans);
		System.out.println("JCoff K-Means:"+extIndex[0]+" , Rand Index  K-Means:"+extIndex[1]);
		System.out.println("Corr K-Means:"+Correlation.calculateCorrelation(distanceMatrix, clustersKMeans, DistanceMatrix.getGroundTruth()));
		
		System.out.println("Enter the value of Epsilon");
		double eps=Double.parseDouble(br.readLine());
		System.out.println("Enter the value of Minimum Points");
		int minPoints=Integer.parseInt(br.readLine());
		DBScan.DBClustering db=new DBScan.DBClustering();
		HashMap<Integer,ArrayList<Integer>> clustersDBScan=db.startClusters(fileData, distanceMatrix, eps, minPoints);
		for(int i:clustersDBScan.keySet()){
			ArrayList<Integer> al=clustersDBScan.get(i);
			System.out.println(al.size());
			
		}
		
		PCACalculation.PCACalculation.calculatePCA(fileData, clustersSinglesLinkHierarchical, "Hierarchical");
		HashMap<Integer, ArrayList<Integer>> newclustersKMeans=getList(clustersKMeans);
		PCACalculation.PCACalculation.calculatePCA(fileData, newclustersKMeans, "KMeans");
		
	}
	static String getCorrectFileName(String fileName){
		if(fileName.contains(".")){
			int index=fileName.indexOf(".");
			return fileName.substring(0,index);
		}else return fileName;
	}
	static HashMap<Integer, ArrayList<Integer>> getList(Map<Integer, List<Integer>> clustersKMeans){
		HashMap<Integer, ArrayList<Integer>> h=new HashMap<Integer, ArrayList<Integer>>();
		for(int i:clustersKMeans.keySet()){
			ArrayList<Integer> al=(ArrayList<Integer>) clustersKMeans.get(i);
			h.put(i, al);
		}
		return h;
	}
}