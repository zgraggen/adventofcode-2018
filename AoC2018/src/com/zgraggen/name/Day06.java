package com.zgraggen.name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

class Point {
    public final int x;
    public final int y;
    public final int hash;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        hash = new String(x + "-" + y).hashCode();
    }
    
    @Override
    public int hashCode() {
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point){
            return ((Point)obj).hash == hash;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return x + "-" + y;
    }
}

class ClosestPoint{
    public final char name;
    public final int distance;
    
    public ClosestPoint(char name, int distance) {
        this.name = name;
        this.distance = distance;
    }
}

public class Day06 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        ArrayList<String> lines = FileHelper.readFile("Day06_Input.txt");

        int xmax = -1;
        int ymax = -1;
        ArrayList<Point> points = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0].trim());
            if(x > xmax) xmax = x;
            int y = Integer.parseInt(parts[1].trim());
            if(y > ymax) ymax = y;
            
            points.add(new Point(x,y));
        }
        
        part1(xmax, ymax, points);
        
        long middle = System.currentTimeMillis();
        
        part2(xmax, ymax, points);

        long end = System.currentTimeMillis();
        System.out.println("Time to run 1: " + (middle - start) + "ms");
        System.out.println("Time to run 2: " + (end - middle) + "ms");
    }

    private static void part2(int xmax, int ymax, ArrayList<Point> points) {
        int count=0;
        for(int y=0; y<=ymax;y++){
            for(int x=0; x<=xmax;x++){
                int sum=0;
                for(Point p : points){
                    int xdist = Math.abs(x-p.x);
                    int ydist = Math.abs(y-p.y);
                    sum += xdist + ydist; 
                }
                if(sum<10000){
                    count++;
                }
            }
        }
        System.out.println("Part 2 = " + count);
        
    }
    
    private static void part1(int xmax, int ymax, ArrayList<Point> points) {
        char dot = 46;
        char startName = 65;
        HashMap<Point, ClosestPoint> grid = new HashMap<>();
        
        char name = startName;
        for(Point p : points){
            for(int y=0; y<=ymax;y++){
                for(int x=0; x<=xmax;x++){  
                    int distance = Math.abs(p.x-x) + Math.abs(p.y-y);
                    Point newP = new Point(x,y);
                    if(grid.containsKey(newP)){
                        if(grid.get(newP).distance==distance){
                            grid.put(newP, new ClosestPoint(dot, distance));
                        } else if(grid.get(newP).distance>distance){
                            grid.put(newP, new ClosestPoint(name, distance));    
                        }
                    } else {
                        grid.put(newP, new ClosestPoint(name, distance));
                    }
                }
            }
            name++;
        }
        
        HashSet<Character> infinitePoints = new HashSet<>();
        for(int i=0; i<xmax; i++){
            infinitePoints.add(grid.get(new Point(i,0)).name);
            infinitePoints.add(grid.get(new Point(i,ymax)).name);
        }
        for(int i=0; i<ymax; i++){
            infinitePoints.add(grid.get(new Point(0,i)).name);
            infinitePoints.add(grid.get(new Point(xmax,i)).name);
        }
        
        HashMap<Character, Integer> counts = new HashMap<>();
        for(int y=0; y<=ymax;y++){
            for(int x=0; x<=xmax;x++){ 
                ClosestPoint candidate = grid.get(new Point(x,y));
                if(!infinitePoints.contains(candidate.name)){
                    if(counts.containsKey(candidate.name)){
                        counts.put(candidate.name, counts.get(candidate.name)+1);
                    } else { 
                        counts.put(candidate.name, 1);
                    }
                }
            }
        }
        System.out.println(counts);
        int maxCount=0;
        for(Entry<Character, Integer> entry: counts.entrySet()){
            if(entry.getValue()>maxCount){
                maxCount = entry.getValue();
            }
        }
        System.out.println("Part 1 = " + maxCount);
    }

    public static void printGrid(HashMap<Point, ClosestPoint> grid, int xmax, int ymax){
        for(int y=0; y<=ymax;y++){
            for(int x=0; x<=xmax;x++){
                Point tmp = new Point(x,y);
                if(grid.containsKey(tmp)){
                    System.out.print(grid.get(tmp).name);
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
