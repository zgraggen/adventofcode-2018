package com.zgraggen.name;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day02 {
	public static void main(String[] args) {
		part1();
		part2();
	}

    private static void part1() {
        ArrayList<String> lines = FileHelper.readFile("Day02_Input.txt");
		
		int twos=0,threes=0;
		for(String line: lines){
			HashMap<Character, Integer> counts = new HashMap<>(); 
			for(char c : line.toCharArray()){
				if(counts.containsKey(c)){
					counts.put(c, counts.get(c) + 1);
				} else {
					counts.put(c,1);					
				}		
			}
			boolean counted2 = false;
			boolean counted3 = false;
			for(Entry<Character, Integer> set: counts.entrySet()){
				if(set.getValue() == 2 && !counted2){
					twos++;
					counted2=true;
				}
				else if (set.getValue() == 3 && !counted3){
					threes++;
					counted3=true;
				}
			}
		}
		System.out.println(twos*threes);
    }
    

    private static void part2() {
        ArrayList<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("Day02_Input.txt"))) {
            lines = stream
                .collect(Collectors.toCollection(ArrayList::new));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line: lines){
            for(String line2: lines){
                int wrongCharsCount=0;
                for(int i=0; i<line.length();i++){
                    if(line.charAt(i)!=line2.charAt(i)){
                        wrongCharsCount++;
                        if(wrongCharsCount>1){
                            break;
                        }
                    }
                }
                if(wrongCharsCount==1){
                    for(int i=0; i<line.length();i++){
                        if(line.charAt(i)==line2.charAt(i)){
                            System.out.print(line.charAt(i));
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

}
