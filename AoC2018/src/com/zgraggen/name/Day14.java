package com.zgraggen.name;

import java.util.ArrayList;

public class Day14 {
    static int elf1=0;
    static int elf2=1;
    static ArrayList<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        int score = 681901;
        initialState();
        
        System.out.println(part1(score));
        System.out.println(part2(score));

    }

    private static void initialState() {
        list.add(3);
        list.add(7);
        list.add(1);
        list.add(0);
    }

    private static void newRecipies() {
        int recipe = list.get(elf1) + list.get(elf2);
        if(recipe>9){
            list.add(1);
        }
        list.add(recipe%10);
        
        elf1=(elf1 + 1 + list.get(elf1)) % list.size();
        elf2=(elf2 + 1 + list.get(elf2)) % list.size();
        
// for debugging        
//      print(list, elf1, elf2);
    }
    
    private static String part1(int score) {
        while(list.size()<score+11){
            newRecipies();
        }
        
        StringBuffer sb = new StringBuffer();
        for(int i=score;i<score+10;i++){
            sb.append(list.get(i));
        }
        
        return sb.toString();
    }
    
    private static int part2(int score) {
        int scoreLength = String.valueOf(score).length();   
        int search=0;
        
        while(true){
            newRecipies();
            
            while(list.size() > search+scoreLength){
                int value=0;
                for(int s=0;s<scoreLength;s++){
                    value*=10;
                    value+=list.get(search+s);
                }
                if(value==score){
                    return search;
                }
                search++;
            }
            
        }
    }

    static void print(ArrayList<Integer> list, int elf1, int elf2) {
        for(int i=0; i<list.size(); i++){
            if(i==elf1){
                System.out.print("("+ list.get(i) + ")");
            } else if(i==elf2){
                System.out.print("[" + list.get(i) + "]");
            } else {
                System.out.print(" " + list.get(i) + " ");
            }
        }
        System.out.println();
    }

}
