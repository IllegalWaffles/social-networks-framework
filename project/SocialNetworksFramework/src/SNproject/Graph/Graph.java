/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.Graph;

import SNproject.graph.EdgeMap;
import SNproject.graph.Node;
import SNproject.graph.NodeMap;
import SNproject.SNApp;
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
    
    public Graph(){
    
        nodemap = new NodeMap();
        edgemap = new EdgeMap();
    
    }
    
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
    
    public static Graph generateRandomGraph(GraphProperties parameters, SNApp app){
        
        Random rand = new Random();
        Graph graph = new Graph();
        
        if(parameters == null)
            throw new IllegalArgumentException("Parameters was null");
        
        int numNodes =      parameters.getNumNodes();
        double edgeProb =   parameters.getLinkProb();
        
        app.appendTextAreanl("Populating node map...");
        
        //Populate the node map
        for(int i = 0; i < numNodes; i++){
            
            graph.getNodeMap().put(i, new Node(i));
            
        }
        
        app.appendTextAreanl("Finished populating node map");
        app.appendTextAreanl("Creating random edges...");
        
        for(int i = 0; i < numNodes; i++){
            
            for(int j = 0; j < numNodes-i; j++){
                
                double roll = rand.nextInt(100)/100.0;
                
                if(roll <= edgeProb){
                    
                    //Add the edge
                    graph.getNodeMap().addConnection(i, j);
                    
                }//Otherwise do nothing
                
                int currentNum = i * j;
                int totalNum = numNodes * numNodes;
                
                app.setProgress((double)currentNum/(double)totalNum, "Calculated " + currentNum + " of possible " + totalNum + " edges");
                
            }
            
        }
        
        app.appendTextAreanl("Finished creating random edges");
        app.appendTextAreanl("Building edge map...");
        
        graph.setEdgeMap(EdgeMap.buildEdgeMap(graph.getNodeMap()));
        
        app.appendTextAreanl("Finished building edge map");
        
        return graph;
        
    }
    
}
