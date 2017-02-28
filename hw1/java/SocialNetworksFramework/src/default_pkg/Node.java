/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_pkg;

import java.util.ArrayList;

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
