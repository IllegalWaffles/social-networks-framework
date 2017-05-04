package SNproject.Data;

import SNproject.AppComponent;
import SNproject.Graph.Graph;
import SNproject.SNApp;
import SNproject.graph.EdgeMap;
import SNproject.graph.NodeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kuba
 */
public class DataManager extends AppComponent{
    
    private Graph graphToTest;
    
//    private NodeMap graphNodeMap;
//    private EdgeMap graphEdgeMap;
    
    private ArrayList<Graph> randomGraphList;
    
    /**
     * Initializes the data component of this application
     * @param init 
     */
    public DataManager(SNApp init){
        
        super(init);
        graphToTest = new Graph();
        
    }
    
    
    public void initData(){
        
        File graphFile = app.getFileManager().getGraphFile();
        
        if(graphFile == null){
            
            System.out.println("Error: no file chosen");
            app.appendTextArea("Error: no file chosen\n");
            
        }else{
            
            try{
            
                app.appendTextAreanl("Building node map from file " + graphFile.getAbsolutePath());
                
                graphToTest.setNodeMap(NodeMap.buildFromFile(graphFile.getAbsolutePath()));
                
                app.appendTextAreanl("Finished building node map");
                app.appendTextAreanl("Building edge map from nodemap generated");
                
                graphToTest.setEdgeMap(EdgeMap.buildEdgeMap(graphToTest.getNodeMap()));
                
                app.appendTextAreanl("Finished building edge map");
                app.appendTextAreanl("Finished initializing data");
                
                app.appendTextAreanl("READY!");
                
            }catch(IOException e){
                
                System.out.println("IO error: " + e.getMessage());
                app.appendTextAreanl("ERROR: IO error");
                app.getFileManager().setGraphFile(null);
                
            }catch(NumberFormatException e){
                
                System.out.println("Formatting error: " + e.getMessage());
                app.appendTextAreanl("ERROR: invalid graph file format\nPlease choose a valid file.");
                app.getFileManager().setGraphFile(null);
                
            }
            
        }
        
    }
    
    public void generateRandomGraphData(){
        
        
        
    }
    
}
