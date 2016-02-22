/**
 * 
 */
package DistanceMatrix;

/**
 * @author Sagar Shinde
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import DBScan.DBClustering;

public class DistanceMatrix {
	public static double[][] groundTruth;
	
	public static String[][] scanFile(String fileName){
		Scanner scan=null;
		try {
			scan=new Scanner(new File("src\\"+fileName+".txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file");
			e.printStackTrace();
		}
		String[][] table=geneData.getDataFromFile(scan);
		return table;
	}
	
	
	public static double[][] getDistanceMatrix(String[][] table){
		double[][] distanceMatrix=new double[table.length+1][table.length+1];
		for(int i=0;i<distanceMatrix[0].length;i++) distanceMatrix[0][i]=-1;
		for(int i=0;i<distanceMatrix.length;i++) distanceMatrix[i][0]=-1;
		for(int i=0;i<distanceMatrix.length;i++) distanceMatrix[i][i]=-1;
		for(int i=0;i<table.length;i++){
			for(int j=i+1;j<table.length;j++){
				distanceMatrix[i+1][j+1]=getDistance(table, i, j);
				distanceMatrix[j+1][i+1]=distanceMatrix[i+1][j+1];
			}
		}
		return distanceMatrix;
	}
	
	public static double[][] parseDoubleMatrix(String[][] table){
		double[][] matrix = new double[table.length][table[0].length-2];
		groundTruth = new double[table.length][2];
		for(int i = 0; i < table.length;i++){
			for(int j = 2; j < table[0].length;j++){
				matrix[i][j-2] = Double.parseDouble(table[i][j]);
			}
			
			groundTruth[i][0] = matrix[i][0];
			groundTruth[i][1] = matrix[i][1];
		}
		
		return matrix;
	}
	
	public static double[][] getGroundTruth(){
		return groundTruth;
	}
	
	static double getDistance(String[][] table, int first, int second){
		String[] fir=table[first];
		String[] sec=table[second];
		//if(fir[1].equals("-1") || sec[1].equals("-1")) return -1;
		double[] f=new double[fir.length-2];
		double[] s=new double[sec.length-2];
		for(int i=2;i<fir.length;i++) f[i-2]=Double.parseDouble(fir[i]);
		for(int i=2;i<sec.length;i++) s[i-2]=Double.parseDouble(sec[i]);
		return calculateDistance(f, s);
	}
	
	static double calculateDistance(double[] f, double[] s){
		double result=0.0;
		for(int i=0;i<f.length;i++){
			double x=f[i]-s[i];
			result+=x*x;
		}
		return Math.sqrt(result);
	}
}
