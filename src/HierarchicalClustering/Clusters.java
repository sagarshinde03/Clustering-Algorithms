/**
 * 
 */
package HierarchicalClustering;

/**
 * @author Sagar Shinde
 *
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Clusters {
	static HashMap<Integer,ArrayList<Integer>> getClustersSingleLink(String[][] table, double[][] distanceMatrix, int numClusters){
		HashMap<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		hm=constructInitialCluster(table);
		while(hm.size()>numClusters){
			double leastDistance=Double.MAX_VALUE;
			int firstCluster=1, secondCluster=2;
			int size=hm.size();
			for(int i=1;i<=size;i++){
				for(int j=i+1;j<=size;j++){
					double distance=getMinDistanceAmongClusters(distanceMatrix, hm, i,j);
					if(leastDistance>distance){
						leastDistance=distance;
						firstCluster=i;
						secondCluster=j;
					}
				}
			}
			ArrayList<Integer> cluster2Genes=hm.get(secondCluster);
			hm.remove(secondCluster);
			ArrayList<Integer> cluster1Genes=hm.get(firstCluster);
			cluster1Genes.addAll(cluster2Genes);
			hm.put(firstCluster, cluster1Genes);
			for(int i=secondCluster+1;i<=size;i++){
				ArrayList<Integer> genes=hm.get(i);
				hm.put(i-1, genes);
			}
			hm.remove(size);
		}
		return hm;
	}
	
	static HashMap<Integer,ArrayList<Integer>> getClustersCompleteLink(String[][] table, double[][] distanceMatrix, int numClusters){
		HashMap<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		hm=constructInitialCluster(table);
		while(hm.size()>numClusters){
			double leastDistance=Double.MAX_VALUE;
			int firstCluster=1, secondCluster=2;
			int size=hm.size();
			for(int i=1;i<=size;i++){
				for(int j=i+1;j<=size;j++){
					double distance=getMaxDistanceAmongClusters(distanceMatrix, hm, i,j);
					if(leastDistance>distance){
						leastDistance=distance;
						firstCluster=i;
						secondCluster=j;
					}
				}
			}
			ArrayList<Integer> cluster2Genes=hm.get(secondCluster);
			hm.remove(secondCluster);
			ArrayList<Integer> cluster1Genes=hm.get(firstCluster);
			cluster1Genes.addAll(cluster2Genes);
			hm.put(firstCluster, cluster1Genes);
			for(int i=secondCluster+1;i<=size;i++){
				ArrayList<Integer> genes=hm.get(i);
				hm.put(i-1, genes);
			}
			hm.remove(size);
		}
		return hm;
	}
	
	static double[][] genMatrixForDendrogram(String[][] table, double[][] distanceMatrix){
		ArrayList<double[]> result=new ArrayList<double[]>();
		HashMap<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		hm=constructInitialClusterDendrogram(table);
		int clusterCount=hm.size()-1;
		while(hm.size()>1){
			double leastDistance=Double.MAX_VALUE;
			int firstCluster=0, secondCluster=0;
			for(int i:hm.keySet()){
				for(int j:hm.keySet()){
					if(j>i){
						double distance=getMinDistanceAmongClusters(distanceMatrix, hm, i,j);
						if(leastDistance>distance){
							leastDistance=distance;
							firstCluster=i;
							secondCluster=j;
						}
					}
				}
			}
			ArrayList<Integer> cluster2Genes=hm.get(secondCluster);
			hm.remove(secondCluster);
			ArrayList<Integer> cluster1Genes=hm.get(firstCluster);
			try{
			cluster1Genes.addAll(cluster2Genes);
			}catch(Exception e){
				System.out.println(cluster1Genes.size());
				System.out.println(cluster2Genes.size());
				System.out.println(firstCluster+" "+secondCluster);}
			hm.remove(firstCluster);
			clusterCount++;
			hm.put(clusterCount, cluster1Genes);
			double[] arr={firstCluster, secondCluster, leastDistance, cluster1Genes.size()};
			result.add(arr);
		}
		double[][] res=new double[result.size()][];
		return result.toArray(res);
	}
	
	static HashMap<Integer,ArrayList<Integer>> constructInitialCluster(String[][] table){
		HashMap<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		int count=1;
		for(int i=0;i<table.length;i++){
			//if(!table[i][1].equals("-1")){
				ArrayList<Integer> a=new ArrayList<Integer>();
				a.add(Integer.parseInt(table[i][0]));
				hm.put(count,a);
				count++;
			//}
		}
		return hm;
	}
	
	static HashMap<Integer,ArrayList<Integer>> constructInitialClusterDendrogram(String[][] table){
		HashMap<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		int count=0;
		for(int i=0;i<table.length;i++){
			//if(!table[i][1].equals("-1")){
				ArrayList<Integer> a=new ArrayList<Integer>();
				a.add(Integer.parseInt(table[i][0]));
				hm.put(count,a);
				count++;
			//}
		}
		return hm;
	}
	
	static double getMinDistanceAmongClusters(double[][] distanceMatrix, HashMap<Integer,ArrayList<Integer>> hm,int firstCluster, int secondCluster){
		ArrayList<Integer> geneSet1=hm.get(firstCluster);
		ArrayList<Integer> geneSet2=hm.get(secondCluster);
		double distance=Double.MAX_VALUE;
		for(int i=0;i<geneSet1.size();i++){
			for(int j=0;j<geneSet2.size();j++){
				int firstGene=geneSet1.get(i);
				int secondGene=geneSet2.get(j);
				if(distance>distanceMatrix[firstGene][secondGene] && distanceMatrix[firstGene][secondGene]>=0) {
					distance=distanceMatrix[firstGene][secondGene];
				}
			}
		}
		return distance;
	}
	
	static double getMaxDistanceAmongClusters(double[][] distanceMatrix, HashMap<Integer,ArrayList<Integer>> hm,int firstCluster, int secondCluster){
		ArrayList<Integer> geneSet1=hm.get(firstCluster);
		ArrayList<Integer> geneSet2=hm.get(secondCluster);
		double distance=Double.MIN_VALUE;
		for(int i=0;i<geneSet1.size();i++){
			for(int j=0;j<geneSet2.size();j++){
				int firstGene=geneSet1.get(i);
				int secondGene=geneSet2.get(j);
				if(distance<distanceMatrix[firstGene][secondGene] && distanceMatrix[firstGene][secondGene]>=0) {
					distance=distanceMatrix[firstGene][secondGene];
				}
			}
		}
		return distance;
	}
}
