/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.Graph;

/**
 *
 * @author Kuba
 */
public class GraphProperties{
    
    private double link_prob;
    private int num_nodes;
    
    public void setLinkProb(double d){
        link_prob = d;
    }
    
    public void setNumNodes(int i){
        num_nodes = i;
    }
    
    public double getLinkProb(){
        return link_prob;
    }
    
    public int getNumNodes(){
        return num_nodes;
    }
    
}
