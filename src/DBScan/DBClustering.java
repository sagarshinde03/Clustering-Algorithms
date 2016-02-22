/**
 * 
 */
package DBScan;

/**
 * @author Sagar Shinde
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.LinkedHashMap;

public class DBClustering {
	public static LinkedHashMap<Integer, Boolean> dataset;
	double[][] distanceMat;
	public static HashMap<Integer,TreeSet<Integer>> clustersDBScan=new HashMap<Integer,TreeSet<Integer>>();
	public HashMap<Integer,ArrayList<Integer>> startClusters(String[][] fileData, double[][] distanceMatrix, double eps, int minPoints){
		ArrayList<Integer> noisePoints=new ArrayList<Integer>();
		distanceMat=distanceMatrix;
		DBClustering dbc=new DBClustering();
		dataset=dbc.generateDataSet(fileData);
		int clusterCount=0;
		for(int i:dataset.keySet()){
			if(dataset.get(i)==false){
				dataset.put(i, true);
				ArrayList<Integer> neighbourPoints=regionQuery(i, eps);
				if(neighbourPoints.size()<minPoints) noisePoints.add(i);
				else{
					clusterCount++;
					expandCluster(i, neighbourPoints, clusterCount, eps, minPoints);
				}
			}
		}
		HashMap<Integer,ArrayList<Integer>> res=new HashMap<Integer,ArrayList<Integer>>();
		for(int i:clustersDBScan.keySet()){
			TreeSet<Integer> ts=clustersDBScan.get(i);
			ArrayList<Integer> al=new ArrayList<Integer>();
			al.addAll(ts);
			res.put(i, al);
		}
		System.out.println("Noise count: "+noisePoints.size());
		return res;
	}
	LinkedHashMap<Integer, Boolean> generateDataSet(String[][] fileData){
		LinkedHashMap<Integer, Boolean> result=new LinkedHashMap<Integer, Boolean>();
		for(int i=0;i<fileData.length;i++){
			result.put(Integer.parseInt(fileData[i][0]), false);
		}
		return result;
	}
	ArrayList<Integer> regionQuery(int p, double eps){
		ArrayList<Integer> result=new ArrayList<Integer>();
		for(int i:dataset.keySet()){
			if(i==p) result.add(i);
			if(distanceMat[p][i]<=eps && distanceMat[p][i]!=-1) {
				result.add(i);
			}
		}
		return result;
	}
	void expandCluster(int p, ArrayList<Integer> neighbourPoints, int clusterCount, double eps, int minPoints){
		TreeSet<Integer> genes=new TreeSet<Integer>();
		if(clustersDBScan.containsKey(clusterCount)){
			genes.addAll(clustersDBScan.get(clusterCount));
		}
		if(!genes.contains(p)) genes.add(p);
		clustersDBScan.put(clusterCount, genes);
		for(int i: neighbourPoints){
			if(dataset.get(i)==false){
				dataset.put(i, true);
				ArrayList<Integer> neighbours=regionQuery(i, eps);
				if(neighbours.size()>=minPoints){
					neighbours.addAll(neighbourPoints);
					expandCluster(i, neighbours, clusterCount, eps, minPoints);
				}else{
					TreeSet<Integer> g=new TreeSet<Integer>();
					g.addAll(clustersDBScan.get(clusterCount));
					if(!g.contains(i)) g.add(i);
					clustersDBScan.put(clusterCount, g);
				}
			}
		}
	}
}
