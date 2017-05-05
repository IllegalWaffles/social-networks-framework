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
import java.util.Random;

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
    
    public int getNumNodes(){return nodemap.getNumNodes();}
    
    public static Graph generateRandomGraph(GraphProperties parameters){
        
        Random rand = new Random();
        Graph graph = new Graph();
        
        if(parameters == null)
            throw new IllegalArgumentException("Parameters was null");
        
        int numNodes =      parameters.getNumNodes();
        double edgeProb =   parameters.getLinkProb();
        
        //Populate the node map
        for(int i = 0; i < numNodes; i++){
            
            graph.getNodeMap().put(i, new Node(i));
            
        }
        
        for(int i = 0; i < numNodes; i++)
            for(int j = 0; j < numNodes-i; j++){
                
                double roll = rand.nextInt(100)/100.0;
                
                if(roll <= edgeProb){
                    
                    //Add the edge
                    graph.getNodeMap().addConnection(i, j);
                    
                }else{}
                //Otherwise do nothing
                
            }
        
        graph.setEdgeMap(EdgeMap.buildEdgeMap(graph.getNodeMap()));
        
        return graph;
        
    }
    
}
