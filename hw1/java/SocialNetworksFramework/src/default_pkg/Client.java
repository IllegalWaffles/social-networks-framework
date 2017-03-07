package default_pkg;

import java.io.IOException;

/**
 *
 * @author Kuba
 */
public class Client {
    
    public static void main(String args[]){
    
        String filename = "edges.txt";
        NodeMap nodemap = null;
        EdgeMap edgemap = null;
        
        try{
            
            nodemap = NodeMap.buildFromFile(filename);
            nodemap.printStats("../../statistics.txt");
            
            edgemap = EdgeMap.buildEdgeMap(nodemap);
            edgemap.printNeighborhoodOverlap("../../statistics-NO.txt", nodemap);
            
        }catch(Exception e){
        
            System.out.println("Error: " + e.getMessage());
        
        }
        
    }
    
}
