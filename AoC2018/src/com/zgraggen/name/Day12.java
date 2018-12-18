package com.zgraggen.name;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class Rules{
	HashMap<String, Boolean> grow;
	
	public Rules() {
		grow = new HashMap<>();
	}
	
	public void addRule(String line){
		if(!line.trim().isEmpty()) {
			String pattern = line.substring(0, 5);
			grow.put(pattern, line.charAt(line.length()-1)=='#');
		}
	}
	
	public boolean grow(String pattern) {
		if(grow.containsKey(pattern)) {
			return grow.get(pattern);
		}
		return false;
	}
}

//3605 first part

//5000	405798
//50000000000	4049999595000
//	4050000000798

//TODO Calculate when stable rather then just assuming it

public class Day12 {
	public final static String INPUT_FILE = "Day12_Input.txt";
	//Assumption stable after 5000
	public final static long iterations = 5000; //50000000000L;
	
	public static void main(String[] args) {
		ArrayList<String> lines = FileHelper.readFile(INPUT_FILE);
		Rules rules = new Rules();
		HashMap<Long, Boolean> plants = parseInput(lines, rules);
		HashMap<Long, Boolean> futurePlants = new HashMap<>();
		long start = 0;
		
//		print(0, plants,start);
		
		long previousSum = 0;
		char[] pattern = ".....".toCharArray();
		for(long i=1; i<=iterations; i++) {
			if(i%10000==0) {
				System.out.println(i);
			}
			long current = start;
			while(plants.containsKey(current)) {
				start = calcNextGrow(rules, plants, futurePlants, start, pattern, current);
				
				current++;
			}
			//two more
			calcNextGrow(rules, plants, futurePlants, start, pattern, current);
			calcNextGrow(rules, plants, futurePlants, start, pattern, current+1);
			calcNextGrow(rules, plants, futurePlants, start, pattern, current+2);
			
			//Trim list (optimization to only have to calculate what is actually needed)
			while(futurePlants.containsKey(start)) {
				if(!futurePlants.get(start) && !futurePlants.get(start+1)) {
					futurePlants.remove(start);
					start++;
				} else {
					break;
				}
			}
			
			previousSum = 0;
			for(Entry<Long, Boolean> set: plants.entrySet()) {
				if(set.getValue()) {
					previousSum += set.getKey();
				}
			}
			
			plants = futurePlants;
			futurePlants = new HashMap<>();
			
//			print(i, plants, start);
		}
		
		long sum=0;
		for(Entry<Long, Boolean> set: plants.entrySet()) {
			if(set.getValue()) {
				sum += set.getKey();
			}
		}
		
		long diffSum = sum-previousSum;
		System.out.println(diffSum);
		long missingCount = (50000000000L-iterations)*diffSum;
		
		System.out.println(sum + missingCount);
	}


	private static long calcNextGrow(Rules rules, HashMap<Long, Boolean> plants,
			HashMap<Long, Boolean> futurePlants, long start, char[] pattern, long current) {
		pattern[0]=pattern[1];
		pattern[1]=pattern[2];
		pattern[2]=pattern[3];
		pattern[3]=pattern[4];
		if(plants.containsKey(current)) {
			if(plants.get(current)) {
				pattern[4] = '#';
			} else {
				pattern[4] = '.';				
			}
		} else {
			pattern[4] = '.';
		}
		if(rules.grow(String.valueOf(pattern))) {
			futurePlants.put(current-2, true);
		} else {
			futurePlants.put(current-2, false);
		}
		if(current-2 < start) {
			start = current -2;
		}
		return start;
	}


	private static HashMap<Long, Boolean> parseInput(ArrayList<String> lines, Rules rules) {
		HashMap<Long, Boolean> plants = new HashMap<>();
		String state = lines.get(0).substring(14).trim();
		
//		System.out.println(state);
		
		for(long i =0; i < state.length();i++) {
			if(state.charAt((int)i)=='#'){
				plants.put(i, true);
			} else {
				plants.put(i, false);
			}
		}
		
		//add rules
		for(String line : lines ) {
			rules.addRule(line);
		}
		
		return plants;
	}

	private static void print(long iteration, HashMap<Long, Boolean> plants, long start) {
//		if(iteration<10) {
//			System.out.print(" " + iteration +": ");
//		} else {
//			System.out.print(iteration +": ");
//		}
//		long current = start;
//		while(plants.containsKey(current)) {
//			if(plants.get(current)) {
//				System.out.print("#");
//			} else {
//				System.out.print(".");				
//			}
//			current++;
//		}
//		System.out.println(" | start = "+start);
		
		long sum=0;
		for(Entry<Long, Boolean> set: plants.entrySet()) {
			if(set.getValue()) {
				sum += set.getKey();
			}
		}
		
		System.out.println(start + ", "+sum);
	}
}
