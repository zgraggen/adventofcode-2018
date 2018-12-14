package com.zgraggen.name;

public class Day11 {
    public static final int GRID_SIZE = 301;
    public static final int PUZZLE_INPUT = 9110;
    
    public static void main(String[] args) {
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        
        for(int y=1; y<GRID_SIZE; y++){
            for(int x=1; x<GRID_SIZE; x++){
                int rackID = x + 10;
                int power = rackID * y;
                int newP = power + PUZZLE_INPUT;
                int newP2 = newP * rackID;
                int hundredDigit = (newP2/100) % 10;
                int level = hundredDigit-5;
                grid[x][y] = level;
            }
        }

        String location = "";
        int maxFuel = Integer.MIN_VALUE;
        //For first part just make z=3;
        for(int z=1; z<GRID_SIZE;z++){
            System.out.println(z);
            for(int y=1; y<(GRID_SIZE-(z-1)); y++){
                for(int x=1; x<(GRID_SIZE-(z-1)); x++){
                    int totalFuel = 0;
                    for(int l=0; l<z;l++){
                        for(int k=0; k<z;k++){
                            totalFuel += grid[x+k][y+l];
                        }
                        
                    }
                    if(totalFuel > maxFuel){
                        maxFuel = totalFuel;
                        location = new String("" + x + "," + y + "," + z);
                    }
                }
            }
        }
                
        System.out.println(location);
        System.out.println(maxFuel);
    }

}
