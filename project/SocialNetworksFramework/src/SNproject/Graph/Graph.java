package SNproject.Graph;

import SNproject.graph.EdgeMap;
import SNproject.graph.Node;
import SNproject.graph.NodeMap;
import SNproject.SNApp;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;

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
    
    public int getNumEdges(){return edgemap.size();}
    public int getNumNodes(){return nodemap.size();}
    
    public double getClusteringCoefficient(){return nodemap.clusteringCoefficient();}
    
    /**
     * Returns a ratio of the number of edges to the number of possible edges (<=1)
     * @return 
     */
    public double getEdgeDensity(){return (double)getNumEdges()/getNumPossibleEdges();}
    
    public int getNumPossibleEdges(){
    
        int n = getNumNodes();
        
        return (n * (n - 1)) / 2;
    
    }
    
    public static Graph generateRandomGraph(GraphProperties parameters, SNApp app){
        
        final Graph graph = new Graph();
        
        Task task = new Task<Void>() {
        
            @Override 
            public Void call(){
            
                final Random rand = new Random();
                
                if(parameters == null)
                    throw new IllegalArgumentException("Parameters was null");

                int numNodes =      parameters.getNumNodes();
                double edgeProb =   parameters.getLinkProb();
                double numPossibleEdges;
                int numEdgesCreated = 0;
                boolean finishedFlag = false;
                
                if(app != null){
                    app.appendTextAreanl("Generating graph with " + numNodes + " and with probability " + edgeProb);
                    app.appendTextAreanl("Graph will have a maximum of " + parameters.getMaxNumEdges() + " edges");
                }

                if(app != null)
                    app.appendTextAreanl("Populating node map...");

                //Populate the node map
                for(int i = 0; i < numNodes; i++)
                    graph.getNodeMap().put(i, new Node(i));

                //Calculate the total number of possible edges
                numPossibleEdges = graph.getNumPossibleEdges();
                
                if(app != null){
                
                    app.appendTextAreanl("Finished populating node map");
                    app.appendTextAreanl("Creating random edges...");

                }
                
                for(int i = 0; i < numNodes; i++){

                    for(int j = 0; j < numNodes-i; j++){

                        double roll = rand.nextDouble();

                        if(roll <= edgeProb){

                            //Add the edge
                            graph.getNodeMap().addConnection(i, j);
                            numEdgesCreated++;
                            
                            if(numEdgesCreated >= parameters.getMaxNumEdges())
                                finishedFlag = true;
                            
                        }//Otherwise do nothing

                        int currentNum = i * j;

                        updateProgress(currentNum, numPossibleEdges);
                        updateMessage(String.format("Generated %9d edges from a possible %d", currentNum, (int)numPossibleEdges));
                        
                        if(finishedFlag)
                            break;
                        
                    }

                    if(finishedFlag)
                        break;
                    
                }

                if(app != null){
                
                    app.appendTextAreanl("Finished creating random edges");
                    app.appendTextAreanl("Building edge map...");
                
                }
                
                graph.setEdgeMap(EdgeMap.buildEdgeMap(graph.getNodeMap()));

                if(app != null)
                    app.appendTextAreanl("Finished building edge map");

                return null;

            }
        
        };
        
        if(app != null){
        
            Platform.runLater(() -> {

                app.getProgressBar().progressProperty().bind(task.progressProperty());
                app.getProgressLabel().textProperty().bind(task.messageProperty());

            });
        
        }
            
        Thread thread = new Thread(task);
        
        //Start the thread
        thread.start();
        
        try{
        
            //Wait for it to finish
            thread.join();
        
        }catch(InterruptedException e){
            
            System.out.println("Something was interrupted: " + e.getMessage());
            
        }
        
        if(app != null){
        
            Platform.runLater(() -> {

                app.getProgressBar().progressProperty().unbind();
                app.getProgressLabel().textProperty().unbind();

            });
        
        }
        
        if(app != null){
        
            app.appendTextAreanl("Generated graph has " + graph.getNumNodes() + " nodes and " + graph.getNumEdges() + " edges");
        
        }
        
        return graph;
        
    }
    
}
