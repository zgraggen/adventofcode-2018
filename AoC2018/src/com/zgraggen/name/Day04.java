package com.zgraggen.name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

class Sleep{
	public final int start;
	public final int end;
	
	public Sleep(int start, int end) {
		this.start = start;
		this.end = end;
	}
}

class Nights{
	public final String date;
	public final ArrayList<Sleep> sleeps;
	
	public Nights(String date, ArrayList<Sleep> sleeps) {
		this.date = date;
		this.sleeps = sleeps;
	}
}

public class Day04 {

	public static void main(String[] args) {
		ArrayList<String> lines = FileHelper.readFile("Day04_Input.txt");
		lines.sort(String::compareToIgnoreCase);
	
		HashMap<Integer, HashSet<Nights>> guardSleep = parseInput(lines);
		
		part1(guardSleep);
		part2(guardSleep);
	}
	
	private static HashMap<Integer, HashSet<Nights>> parseInput(ArrayList<String> lines) {
		HashMap<Integer, HashSet<Nights>> guardSleep = new HashMap<>();
		int currentGuard = -1;
		int sleepStart = Integer.MIN_VALUE;
		String date;
		ArrayList<Sleep> sleeps = new ArrayList<>();
		for(String line : lines){
			String[] parts = line.split(" ");
			
			date = parts[0].substring(1);
			String time = parts[1].substring(0,parts[1].length()-1);
			
			if(line.contains("Guard")){
				if(currentGuard != -1){
					if(guardSleep.containsKey(currentGuard)){
						guardSleep.get(currentGuard).add(new Nights(date, sleeps));

					} else {
						HashSet<Nights> nights = new HashSet<>();
						nights.add(new Nights(date, sleeps));
						guardSleep.put(currentGuard, nights);
					}
					
					sleeps = new ArrayList<>();
				}
				currentGuard = Integer.parseInt(parts[3].substring(1));
				
			} else if(line.contains("falls")) {
				sleepStart = Integer.parseInt(time.split(":")[1]);
			} else {
				int sleepEnd = Integer.parseInt(time.split(":")[1]);
				sleeps.add(new Sleep(sleepStart, sleepEnd-1));
			}
		}
		return guardSleep;
	}	
	
	private static void part1(HashMap<Integer, HashSet<Nights>> guardSleep) {
		int guardId = guardWithMostOverallSleep(guardSleep);
		
		HashSet<Nights> guardNights = guardSleep.get(guardId);
		int[] hour = new int[60];
		for(Nights nights : guardNights){
			for(Sleep sleep : nights.sleeps){
				for(int i = sleep.start; i <= sleep.end ; i++){
					hour[i] = hour[i] + 1;
				}
			}
		}
				
		int time=-1;
		int highest=-1;
		for(int i=0; i<hour.length;i++){
//			System.out.print(i+"=" + hour[i] + " ");
			if(hour[i]>highest){
				time = i;
				highest = hour[i];
			}
		}
		
		System.out.println();
		
		System.out.println(time + " " + highest);
		System.out.println(time * guardId);
	}

	private static int guardWithMostOverallSleep(
			HashMap<Integer, HashSet<Nights>> guardSleep) {
		int guardId = -1;
		int mostSleep = -1;
		for(Entry<Integer, HashSet<Nights>> oneGuard : guardSleep.entrySet()){
			int sleepAmount = 0;
			for(Nights nights : oneGuard.getValue()){
				for(Sleep sleep : nights.sleeps){
					sleepAmount += sleep.end-sleep.start;
				}
			}
			if(sleepAmount>mostSleep){
				guardId = oneGuard.getKey();
				mostSleep=sleepAmount;
			}
		}
		System.out.println(guardId + " " + mostSleep);
		return guardId;
	}
	
	private static void part2(HashMap<Integer, HashSet<Nights>> guardSleep) {
		int guardId=-1;
		int highest=-1;
		int time=-1;
		for(Entry<Integer, HashSet<Nights>> oneGuard : guardSleep.entrySet()){
			int[] hour = new int[60];
			for(Nights nights : oneGuard.getValue()){
				for(Sleep sleep : nights.sleeps){
					for(int i = sleep.start; i <= sleep.end ; i++){
						hour[i] = hour[i] + 1;
					}
				}
			}
			for(int i=0; i<hour.length;i++){
				if(hour[i]>highest){
					guardId = oneGuard.getKey();
					time = i;
					highest = hour[i];
				}
			}
		}
	
		System.out.println();
		
		System.out.println(time + " " + guardId);
		System.out.println(time * guardId);
	}

}
