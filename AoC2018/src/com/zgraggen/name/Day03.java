package com.zgraggen.name;
import java.util.ArrayList;


public class Day03 {

	static int size = 1000;
	
	public static void main(String[] args) {
		ArrayList<String> lines = FileHelper.readFile("Day03_Input.txt");
		int[][] fabric = new int[size][size];
			
		for(String line:lines){
			//#1 @ 1,3: 4x4
			String[] parts = line.split(" ");
			String[] startPos = parts[2].split(",");
			String[] lengths = parts[3].split("x");
			addsquare(
					fabric,
					Integer.parseInt(parts[0].substring(1)),
					Integer.parseInt(startPos[0]),
					Integer.parseInt(startPos[1].substring(0,startPos[1].length()-1)),
					Integer.parseInt(lengths[0]),
					Integer.parseInt(lengths[1]));
		}
		
		print(fabric);

		for(String line:lines){
			//#1 @ 1,3: 4x4
			String[] parts = line.split(" ");
			String[] startPos = parts[2].split(",");
			String[] lengths = parts[3].split("x");
			overlap(
					fabric,
					Integer.parseInt(parts[0].substring(1)),
					Integer.parseInt(startPos[0]),
					Integer.parseInt(startPos[1].substring(0,startPos[1].length()-1)),
					Integer.parseInt(lengths[0]),
					Integer.parseInt(lengths[1]));
		}
	}

	private static void addsquare(int[][] fabric, int id, int x, int y, int sizex, int sizey){
		for(int i=0; i<sizex; i++){
			for(int j=0; j<sizey; j++){
				if(fabric[x+i][y+j] == 0){
					fabric[x+i][y+j] = id;
				} else {
					fabric[x+i][y+j] = -1;
				}
			}
		}
	}
	
	private static void overlap(int[][] fabric, int id, int x, int y, int sizex, int sizey){
		boolean overlap=false;
		for(int i=0; i<sizex; i++){
			for(int j=0; j<sizey; j++){
				if(fabric[x+i][y+j] == -1){
					overlap=true;
				}
			}
		}	
		if(!overlap){
			System.out.println(id);
		}
	}
	
	private static void print(int[][] fabric){
	    boolean debug = false;
		int overlapCount=0;
		for(int[] row : fabric){
			for(int inch: row){
				if(inch==0){
				    if(debug){
				        System.out.print(".");
				    }
				}else{
					if(inch==-1){
						overlapCount++;
					}
					if(debug){
					    System.out.print(inch);
					}
				}
			}
			if(debug){
			    System.out.println();
			}
		}
		System.out.println("\nOverlap count= " + overlapCount);
	}

}
