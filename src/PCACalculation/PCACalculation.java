/**
 * 
 */
package PCACalculation;

/**
 * @author Sagar Shinde
 *
 */
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class PCACalculation {
	public static void calculatePCA(String[][] fileData, HashMap<Integer,ArrayList<Integer>> clusters, String algo){
		HashMap<Integer, ArrayList<String>> fd=new HashMap<Integer, ArrayList<String>>();
		fd=generate(fileData);
		
		PrintWriter writer =null;
		try {
			writer = new PrintWriter(algo+".txt", "UTF-8");
		}
		catch (FileNotFoundException e) {System.out.println(e.toString());e.printStackTrace();} 
		catch (UnsupportedEncodingException e) {System.out.println(e.toString());e.printStackTrace();}
		/*for(int i:clusters.keySet()){
			if(i!=clusters.size()) writer.print(clusters.get(i).size()+"\t");
			else writer.println(clusters.get(i).size());
		}*/
		for(int clusterNum:clusters.keySet()){
			ArrayList<Integer> al=clusters.get(clusterNum);
			for(int gene:al){
				writer.print(clusterNum+"\t");
				ArrayList<String> st=fd.get(gene);
				for(int i=0;i<st.size();i++){
					if(i!=st.size()-1) writer.print(st.get(i)+"\t");
					else writer.println(st.get(i));
				}
			}
		}
		writer.close();
	}
	static HashMap<Integer, ArrayList<String>> generate(String[][] fileData){
		HashMap<Integer, ArrayList<String>> result=new HashMap<Integer, ArrayList<String>>();
		for(int i=0;i<fileData.length;i++){
			ArrayList<String> al=new ArrayList<String>();
			for(int j=2;j<fileData[i].length;j++){
				al.add(fileData[i][j]);
			}
			int rowNum=Integer.parseInt(fileData[i][0]);
			result.put(rowNum, al);
		}
		return result;
	}
}
