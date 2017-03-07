/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_pkg;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Kuba
 */
public class Node{
    
    private int ID;
    private ArrayList<Integer> connections;
    
    public Node(){
        this(-1);
    }
    
    public Node(int _ID){
        ID = _ID;
        connections = new ArrayList<Integer>();
    }
    
    public ArrayList<Integer> connections(){
        return connections;
    }
    
    public static boolean connect(Node node1, Node node2){
        
        if(node1.connections().contains(node2.ID) || node2.connections().contains(node2.ID))
            return false;
            
        node1.connections().add(node2.ID);
        node2.connections().add(node1.ID);
        
        return true;
        
    }
    
    public static int intersection(Node n1, Node n2){
    
        HashSet<Integer> set = new HashSet<>();
        
        for(int i : n1.connections())
            if(n2.connections().contains(i))
                set.add(i);
                
        return set.size();
        
    }
    
    public static int union(Node n1, Node n2){
    
        HashSet<Integer> set = new HashSet<>(); 
    
        set.addAll(n1.connections());
        set.addAll(n2.connections());
        
        return set.size();
    
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
    
    public boolean equals(Node other){
        return this.ID == other.ID;
    }
    
}
