package com.zgraggen.name;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;


public class Day01 {

	public static void main(String[] args) throws Exception {
		part1();
	    part2();
	}

    private static void part1() {
        String fileName = "Day01_Input.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			System.out.println(
					stream
						.filter(i -> (i.length() > 1))
						//.peek(System.out::println)
						.mapToInt(x -> Integer.parseInt(x))
						.sum());

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static void part2() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(new File("Day01_Input.txt"));
        BufferedReader br = new BufferedReader(fr);
        
        String line;
        ArrayList<Integer> list = new ArrayList<>();
        while((line = br.readLine()) != null){
            list.add(Integer.parseInt(line));
        }
        
        int sum = 0;
        HashSet<Integer> set = new HashSet<Integer>();
        boolean done =false;
        while(!done){
            for(int i: list){
                sum+= i;
                if(set.contains(sum)){
                    System.out.println(sum);
                    done = true;
                    break;
                } else {
                    set.add(sum);
                }
            }
            
        }
        br.close();
    }
}
