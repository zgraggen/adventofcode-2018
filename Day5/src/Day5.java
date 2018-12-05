import java.util.ArrayList;
import java.util.HashSet;


public class Day5 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		ArrayList<String> lines = FileHelper.readFile("input");
		System.out.println("Solution for 1= " + part1(lines.get(0).toCharArray(),'\n'));
		
		long middle = System.currentTimeMillis();
		
		System.out.println("Solution for 2= " + part2(lines.get(0).toCharArray()));
		long end = System.currentTimeMillis();
		
		System.out.println("Time to run 1: " + (middle-start) + "ms");
		System.out.println("Time to run 2: " + (end-middle) + "ms");
	}
	
	private static int part2(char[] poly) {
		HashSet<Character> differentCharacters = new HashSet<>();
		for(Character c: poly ){
			differentCharacters.add(Character.toLowerCase(c));			
		}
		int lowest = Integer.MAX_VALUE;
		for(char removeC : differentCharacters){
			int result = part1(poly,removeC);
			if(result<lowest){
				lowest = result;
			}
		}
		return lowest;
	}
	
	private static int part1(char[] poly, char ignore) {
		ArrayList<Character> remain = new ArrayList<>();
		for(Character c: poly ){
			if(Character.toLowerCase(c) == ignore){
				continue;
			}
			if(remain.isEmpty()){
				remain.add(c);
			} else {
				if(isOpposite(c, remain.get(remain.size()-1))){
					remain.remove(remain.size()-1);
				} else {
					remain.add(c);
				}
			}
		}
		return remain.size();
	}
	
	public static boolean isOpposite(char a, char b){
		if(a != b){
			if(a == Character.toLowerCase(b) || Character.toLowerCase(a) == b){
				return true;
			}
		}
		return false;
	}

}
