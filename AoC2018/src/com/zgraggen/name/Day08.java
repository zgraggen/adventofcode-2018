package com.zgraggen.name;
import java.util.ArrayList;


class Node{
    public final int childCount;
    public final int metadataCount;
    public final Node[] children;
    public final ArrayList<Integer> meta = new ArrayList<>();
    
    private int childsAdded=0;
    
    public Node(int childCount, int metadataCount) {
        this.childCount = childCount;
        this.metadataCount = metadataCount;
        this.children = new Node[childCount];
    }
    
    public void addChild(Node n){
        children[childsAdded++] = n;
    }
    
    public void addMetadata(int n){
        meta.add(n);
    }
    public int getSum(){
        int sum = 0;
        for(int i : meta){
            sum+=i;
        }
        for(Node child:children){
            sum += child.getSum();
        }
        return sum;
    }
    
    public int getValue(){
        int sum = 0;
        if(childCount==0){
            for(int i : meta){
                sum += i;
            }
        } else {
            for(int i : meta){
                if(i <= childCount){
                    sum += children[i-1].getValue();
                }
            }
        }
        return sum;
    }
}

public class Day08 {
    public final static String INPUT_FILE = "Day08_Input.txt";
    private static int pos=0;

    public static void main(String[] args) {
        ArrayList<String> lines = FileHelper.readFile(INPUT_FILE);

        String[] entries = lines.get(0).split(" ");
        Node node = process(entries);
        
        System.out.println(node.getSum());
        System.out.println(node.getValue());
    }

    private static Node process(String[] entries) {
        int childCount = Integer.parseInt(entries[pos++]);
        int metaCount = Integer.parseInt(entries[pos++]);
        Node node = new Node(childCount, metaCount);        
        for(int i=0; i<childCount;i++){
            node.addChild(process(entries));            
        }
        
        for(int i=0; i<metaCount;i++){
            node.addMetadata(Integer.parseInt(entries[pos++]));
        }
                
        return node;
    }

}
