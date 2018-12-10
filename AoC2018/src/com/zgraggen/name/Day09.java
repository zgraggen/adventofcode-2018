package com.zgraggen.name;
import java.util.ArrayList;

class Player{
    public final int number;
    private long score;
    
    public Player(int number) {
        this.number=number;
        score = 0;
    }
    
    public void addScore(int score){
        this.score+=score;
    }
    
    public long getScore(){
        return score;
    }
}

class Marbel{
    public int value;
    public Marbel forward;
    public Marbel back;
    public Marbel(int value) {
        this.value=value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

//Examples:
//9 players; last marble is worth 25 points: high score is 32
//10 players; last marble is worth 1618 points: high score is 8317
//13 players; last marble is worth 7999 points: high score is 146373
//17 players; last marble is worth 1104 points: high score is 2764
//21 players; last marble is worth 6111 points: high score is 54718
//30 players; last marble is worth 5807 points: high score is 37305

//Input:
//404 players; last marble is worth 71852 points
//PART too 100* more marbels

public class Day09 {
    public static int elves = 404;
    public static int marbels = 7185200;
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        ArrayList<Player> players = new ArrayList<>();
        for(int i=1;i<=elves;i++){
            players.add(new Player(i));
        }
        
        Marbel current = new Marbel(0);
        current.forward = current;
        current.back = current;
        //only needed for printing
//        Marbel zero = current;
        
        int count=0;
        long maxScore=0;
        while(count<marbels){
            count++;
            Player p = players.get((count-1)%elves);
            if(count%23==0){
                p.addScore(count);
                Marbel n = current.back.back.back.back.back.back.back;
                p.addScore(n.value);
                current = n.forward;
                n.back.forward = current;
                current.back = n.back;
                if(p.getScore()>maxScore){
                    maxScore = p.getScore();
                }
            } else {
                Marbel newNode = new Marbel(count);
                
                Marbel one = current.forward;
                Marbel two = one.forward;
                one.forward = newNode;
                newNode.forward = two;
                
                two.back = newNode;
                newNode.back = one;
                
                current = newNode;
            }
            if(count==71852){
                System.out.println("Part1 (" + count + ") = " + maxScore); 
            }
//            print(zero);
        }
        System.out.println("Part2 (" + count + ") = " + maxScore); 
        
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        
    }

    public static void print(Marbel current) {
        Marbel pointer = current;
        do{
            System.out.print(pointer.value + " ");
            pointer = pointer.forward;
        }while(pointer != current);
        System.out.println();
    }

}
