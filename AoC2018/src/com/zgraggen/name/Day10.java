package com.zgraggen.name;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

class InitialPoint{
    public final int x;
    public final int y;
    public final int velocityx;
    public final int velocityy;
    
    public InitialPoint(int x, int y, int velocityx, int velocityy) {
        this.x=x;
        this.y=y;
        this.velocityx=velocityx;
        this.velocityy=velocityy;
    }
    
    public Point getPointAt(int time){
        return new Point(x+(velocityx*time),y+(velocityy*time));
    }
}

public class Day10 {
    public final static String INPUT_FILE = "Day10_Input.txt";
 
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        
        ArrayList<String> lines = FileHelper.readFile(INPUT_FILE);

        ArrayList<InitialPoint> initialPoints = new ArrayList<>();
        for(String line: lines){
            String start = line.substring(10);
            int coma = start.indexOf(",");
            int endCoordinate = start.indexOf(">");
            int x = Integer.parseInt(start.substring(0, coma).trim());
            int y = Integer.parseInt(start.substring(coma+1, endCoordinate).trim());
            
            String part2 = start.substring(endCoordinate+12);
            coma = part2.indexOf(",");
            endCoordinate = part2.indexOf(">");
            int velocityx = Integer.parseInt(part2.substring(0, coma).trim());
            int velocityy = Integer.parseInt(part2.substring(coma+1, endCoordinate).trim());
            
            initialPoints.add(new InitialPoint(x, y, velocityx, velocityy));
        }
        

        int time = calcTimeOfMin(initialPoints);
        print(initialPoints, time);        
        
        long endTime = System.currentTimeMillis();
        System.out.println("process time= " + (endTime-startTime) + "ms");
    }

    private static int calcTimeOfMin(ArrayList<InitialPoint> initialPoints) {
        int time=0;
        int distanceX = Integer.MAX_VALUE;
        
        for(;time<100000; time++){
            int xmin=Integer.MAX_VALUE;
            int xmax=Integer.MIN_VALUE;
            for(InitialPoint iPoint: initialPoints){
                Point p = iPoint.getPointAt(time);
                if(p.x<xmin)xmin=p.x;
                if(p.x>xmax)xmax=p.x;           
            }
            
            int distance = xmax-xmin;
            if(distanceX > distance){
                distanceX=distance;
            } else {
                break;
            }
        }
        return time-1;
    }
    
    private static void print(ArrayList<InitialPoint> initialPoints, int time){
        HashSet<Point> points = new HashSet<>();
        
        int xmin=Integer.MAX_VALUE;
        int ymin=Integer.MAX_VALUE;
        int xmax=Integer.MIN_VALUE;
        int ymax=Integer.MIN_VALUE;
        for(InitialPoint iPoint: initialPoints){
            Point p = iPoint.getPointAt(time);
            if(p.x<xmin)xmin=p.x;
            if(p.x>xmax)xmax=p.x;
            if(p.y<ymin)ymin=p.y;
            if(p.y>ymax)ymax=p.y;
            points.add(p);            
        }
        
        System.out.println("TIME: " + time);
        for(int j=ymin;j<=ymax;j++){
            for(int i=xmin;i<=xmax;i++){
                if(points.contains(new Point(i,j))){
                    System.out.print("#");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

}
