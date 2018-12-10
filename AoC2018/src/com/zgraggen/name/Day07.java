package com.zgraggen.name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;

class Worker{
    public final int name;
    char workName;
    int workAmount=-1;
    HashMap<Character, HashSet<Character>> blockers = new HashMap<>();
    HashMap<Character, HashSet<Character>> dependencies = new HashMap<>();

    public Worker(int name,
            HashMap<Character, HashSet<Character>> blockers,
            HashMap<Character, HashSet<Character>> dependencies) {
        this.name = name;
        this.blockers = blockers;
        this.dependencies = dependencies;
    }
    
    public void startWork(char workName){
        this.workName = workName;
        this.workAmount = workName - '@' + 60;
    }
    
    public void work(){
        this.workAmount--;
        if(workAmount==0){
            blockers.remove(workName);
            HashSet<Character> deps = dependencies.get(workName);
            for(char c : deps){
                blockers.get(c).remove(workName);
            }
            workName = '0';
        }
    }
    
    public boolean isBusy(){
        return workAmount>0;
    }
    
    public Character getWork() {
        return workName;
    }
    
    @Override
    public String toString() {
        if(workAmount>0){
            return "Worker " + name + ": " + workName + "(" + workAmount + ")";             
        } else {
            return "Worker " + name + ": waiting"; 
        }
    }
}

public class Day07 {

//    public final static int WORKER_COUNT = 2;
//    public final static String INPUT_FILE = "Day07_Example.txt";
    public final static int WORKER_COUNT = 5;
    public final static String INPUT_FILE = "Day07_Input.txt";
    
    public static void main(String[] args) {
        
        ArrayList<String> lines = FileHelper.readFile(INPUT_FILE);

        part1(lines);
        part2(lines);
        
    }

    private static void part2(ArrayList<String> lines ) {
        
      HashMap<Character, HashSet<Character>> blockers = new HashMap<>();
      HashMap<Character, HashSet<Character>> dependencies = new HashMap<>();
      for(String line: lines ){
          char before = line.charAt(5);
          char after = line.charAt(36);
          addEntries(blockers, before, after);
          addEntries(dependencies, after, before);
      }
       
        ArrayList<Worker> workers = new ArrayList<>();
        for(int i=0; i<WORKER_COUNT;i++){
            workers.add(new Worker(i, blockers, dependencies));
        }
        
        int timeCount=0;
        while(!blockers.isEmpty()){
            if(!workerBusy(workers,timeCount)){
                PriorityQueue<Character> heap = new PriorityQueue<>();
                for(Entry<Character, HashSet<Character>> entry : blockers.entrySet()){
                    if(entry.getValue().isEmpty()){
                        if(!beingWorkOn(workers, entry.getKey())){
                            heap.add(entry.getKey());
                        }  
                    }
                }
                if(heap.isEmpty()){
                    timeCount++;
                    workerWork(workers);
                } else {
                    while(!heap.isEmpty()){
                        if(workerBusy(workers,timeCount)){
                            break;
                        } else {                            
                            for(Worker w : workers){
                                if(!w.isBusy()){
                                    w.startWork(heap.remove());
                                    System.out.println(w);
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                timeCount++;
                workerWork(workers);
            }
        }
        System.out.println(timeCount);
    
    }
    
    private static boolean beingWorkOn(ArrayList<Worker> workers, Character key) {
        for(Worker w : workers){
            if(w.getWork() == key){
                return true;
            }
        }
        return false;
    }

    private static void workerWork(ArrayList<Worker> workers) {
        for(Worker w : workers){
            w.work();
        }
    }

    private static boolean workerBusy(ArrayList<Worker> workers, int timeCount) {
        for(Worker w : workers){
            if(!w.isBusy()){
                return false;
            }
        }
        return true;
    }

    private static void part1(ArrayList<String> lines) {
      HashMap<Character, HashSet<Character>> blockers = new HashMap<>();
      HashMap<Character, HashSet<Character>> dependencies = new HashMap<>();
      for(String line: lines ){
          char before = line.charAt(5);
          char after = line.charAt(36);
          addEntries(blockers, before, after);
          addEntries(dependencies, after, before);
      }
        
        while(!blockers.isEmpty()){
            PriorityQueue<Character> heap = new PriorityQueue<>();
            for(Entry<Character, HashSet<Character>> entry : blockers.entrySet()){
                if(entry.getValue().isEmpty()){
                    heap.add(entry.getKey());
                }
            }
            System.out.print(heap.peek());
            blockers.remove(heap.peek());
            HashSet<Character> deps = dependencies.get(heap.peek());
            for(char c:deps){
                blockers.get(c).remove(heap.peek());
            }
        }
        System.out.println();
    }

    private static void addEntries(
            HashMap<Character, HashSet<Character>> blockers, char before,
            char after) {
        if(blockers.containsKey(after)){
            blockers.get(after).add(before);
        } else {
            HashSet<Character> newSet = new HashSet<>();
            newSet.add(before);
            blockers.put(after, newSet);
        }
        
        if(!blockers.containsKey(before)){
            HashSet<Character> newSet = new HashSet<>();
            blockers.put(before, newSet);
        }
    }

}
