/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.Graph;

import SNproject.graph.EdgeMap;
import SNproject.graph.NodeMap;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Kuba
 */
public class Graph {
    
    private NodeMap nodemap;
    private EdgeMap edgemap;
    
    public Graph(){}
    
    public Graph(File f){
        
        if(f == null)
            return;
        
        try{
            
            nodemap = NodeMap.buildFromFile(f.getAbsolutePath());
            edgemap = EdgeMap.buildEdgeMap(nodemap);
            
        }catch(IOException e){
            
            System.out.println("Could not open file " + f.getAbsolutePath());
            System.out.println(e.getMessage());
            
        }
        
    }
    
    public NodeMap getNodeMap(){return nodemap;}
    public EdgeMap getEdgeMap(){return edgemap;}
    
    public void setNodeMap(NodeMap nm){nodemap = nm;}
    public void setEdgeMap(EdgeMap em){edgemap = em;}
    
    public static Graph generateRandomGraph(GraphProperties parameters){
        
        Graph toReturn = new Graph();
        
        return toReturn;
        
    }
    
}
