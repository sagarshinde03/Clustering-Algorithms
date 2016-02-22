/**
 * 
 */
package DistanceMatrix;

/**
 * @author Sagar Shinde
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public class geneData {
	static String[][] getDataFromFile(Scanner x){
		ArrayList<String[]> t=new ArrayList<String[]>();
		int columnSize=0;
		while(x.hasNext()){
			String line=x.nextLine();
			String[] split=line.split("\t");
			columnSize=split.length;
			//if(split[1]!="-1") 
			t.add(split);
		}
		String[][] table=new String[t.size()][columnSize];
		int count=0;
		for(String[] arr:t){
			table[count]=arr;
			count++;
		}
		return table;
	}
}
