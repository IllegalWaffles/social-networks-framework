package SNproject.Graph;

import SNproject.graph.EdgeMap;
import SNproject.graph.Node;
import SNproject.graph.NodeMap;
import SNproject.SNApp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Kuba
 */
public class Graph {
    
    private NodeMap nodemap;
    private EdgeMap edgemap;
    
    private static SNApp app;
    
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
    
    public static void setApp(SNApp initapp){
        app = initapp;
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
    
    public PathLengthReturn getAveragePathLength(){
    
        final PathLengthReturn taskRetVal = new PathLengthReturn();
        
        //This task calculates the average path length
        Task<Double> task = new Task() {
        
            @Override
            public Double call(){
            
                ArrayList<Node> nodesAlreadyCalculated = new ArrayList();
                int pathLength = 0;
                int totalPathLength = 0;

                int numNodes = nodemap.size();
                final int numPathsToCalculate = ((numNodes - 1) * (numNodes)) / 2;
                int pathCalcCounter = 0;

                //Iterate through every node as a start point
                for(Node start : nodemap.values()){

                    //For every starting node, iterate through every node as an end
                    for(Node end : nodemap.values()){

                        //If the destination node was already a starting node, skip
                        //If the two nodes are the same, skip
                        if(nodesAlreadyCalculated.contains(end) || start.equals(end))
                            continue;

                        //Calculate the path length
                        pathLength = nodemap.getPathLength(start, end);
                        
                        if(pathLength == -1)
                            taskRetVal.numMisses++;
                        else
                            totalPathLength += pathLength;
                        
                        pathCalcCounter++;

                        updateProgress(pathCalcCounter, numPathsToCalculate);
                        
                        if(pathCalcCounter % 10 == 0)
                            updateMessage("Found " + pathCalcCounter + " paths out of " + numPathsToCalculate);
                        
                    }

                    //Since every path was already calculated for the start node,
                    //Ignore it for the rest of the paths
                    nodesAlreadyCalculated.add(start);

                }
        
            taskRetVal.averagePathLength = (double)totalPathLength/(double)numPathsToCalculate;
            return null;
            
            }
        
        };
        
        if(app != null){
        
            Platform.runLater(() -> {

                app.getProgressBar().progressProperty().unbind();
                app.getProgressLabel().textProperty().unbind();
                
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
        
        return taskRetVal;
        
    }
    
    public static Graph generateRandomGraph(GraphProperties parameters){
        
        final Graph graph = new Graph();
        
        Task task = new Task<Void>() {
        
            @Override 
            public Void call(){
            
                final Random rand = new Random();
                
                if(parameters == null)
                    throw new IllegalArgumentException("Parameters was null");

                int numNodes =      parameters.getNumNodes();
                double edgeProb =   parameters.getLinkProb();
                int maxNumEdges =   parameters.getMaxNumEdges();
                int numEdgesCreated = 0;
                boolean finishedFlag = false;
                
                if(app != null){
                    app.appendTextAreanl("Generating graph with " + numNodes + " and with probability " + edgeProb);
                    app.appendTextAreanl("Graph will have a maximum of " + maxNumEdges + " edges");
                }

                if(app != null)
                    app.appendTextAreanl("Populating node map...");

                //Populate the node map
                for(int i = 0; i < numNodes; i++)
                    graph.getNodeMap().put(i, new Node(i));
                
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

                        //if(numEdgesCreated % 50 == 0 || finishedFlag)
                            updateProgress(numEdgesCreated, parameters.getMaxNumEdges());
                        
                        if(numEdgesCreated % 10 == 0 || finishedFlag)
                            updateMessage(String.format("Generated %d edges from a possible %d (%d%%)", 
                                    numEdgesCreated, 
                                    maxNumEdges, 
                                    (int)((numEdgesCreated/(double)maxNumEdges)*100.0)));
                        
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

                app.getProgressBar().progressProperty().unbind();
                app.getProgressLabel().textProperty().unbind();
                
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
        
            app.appendTextAreanl("Generated graph has " + graph.getNumNodes() + " nodes and " + graph.getNumEdges() + " edges");
        
        }
        
        if(app != null){
        
            Platform.runLater(() -> {
            
                app.getProgressBar().progressProperty().unbind();
                app.getProgressLabel().textProperty().unbind();
            
            });
        
        }
        
        return graph;
        
    }
    
}
