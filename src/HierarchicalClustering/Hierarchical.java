/**
 * 
 */
package HierarchicalClustering;

/**
 * @author Sagar Shinde
 *
 */
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class Hierarchical {
	public static HashMap<Integer,ArrayList<Integer>> clustersSinglesLink;
	public static HashMap<Integer,ArrayList<Integer>> clustersCompleteLink;
	public void startClustering(String[][] table, double[][] distanceMatrix, int numClusters) throws FileNotFoundException, UnsupportedEncodingException{
		clustersSinglesLink=Clusters.getClustersSingleLink(table, distanceMatrix, numClusters);
		clustersCompleteLink=Clusters.getClustersCompleteLink(table, distanceMatrix, numClusters);
		HashMap<Integer,ArrayList<Integer>> groundTruthClusters=getGTClusters(table);
		double jaccardCoefficientSingleLink=calculateJC(clustersSinglesLink, groundTruthClusters, table.length);
		double jaccardCoefficientCompleteLink=calculateJC(clustersCompleteLink, groundTruthClusters, table.length);
		System.out.println("Jaccard Coefficient Single Link: "+jaccardCoefficientSingleLink);
		System.out.println("Jaccard Coefficient Complete Link: "+jaccardCoefficientCompleteLink);
		
		double[][] matrixForDendrogram=Clusters.genMatrixForDendrogram(table, distanceMatrix);
		PrintWriter writer = new PrintWriter("dendrogram.txt", "UTF-8");
		for(int i=0;i<matrixForDendrogram.length;i++){
			for(int j=0;j<matrixForDendrogram[i].length;j++){
				if(j!=3) writer.print(matrixForDendrogram[i][j]+"\t");
				else writer.println(matrixForDendrogram[i][j]);
			}
		}
		writer.close();
	}
	public HashMap<Integer,ArrayList<Integer>> getGTClusters(String[][] table){
		HashMap<Integer,ArrayList<Integer>> groundTruthClusters=new HashMap<Integer,ArrayList<Integer>>();
		for(int i=0;i<table.length;i++){
			int clusterNum=Integer.parseInt(table[i][1]);
			if(clusterNum!=-1){
				ArrayList<Integer> al=new ArrayList<Integer>();
				if(groundTruthClusters.containsKey(clusterNum)){
					al.addAll(groundTruthClusters.get(clusterNum));
				}
				al.add(Integer.parseInt(table[i][0]));
				groundTruthClusters.put(clusterNum, al);
			}
		}
		return groundTruthClusters;
	}
	double calculateJC(HashMap<Integer,ArrayList<Integer>> clusters, HashMap<Integer,ArrayList<Integer>> groundTruthClusters, int totalGenes){
		int countMatchingGenes=0;
		for(int clusterNum:clusters.keySet()){
			ArrayList<Integer> genes1=clusters.get(clusterNum);
			if(groundTruthClusters.containsKey(clusterNum)){
				ArrayList<Integer> genes2=groundTruthClusters.get(clusterNum);
				for(int i:genes1){
					if(genes2.contains(i)) countMatchingGenes++;
				}
			}
		}
		return (double)countMatchingGenes/totalGenes;
	}
}