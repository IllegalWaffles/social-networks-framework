package default_pkg;

import java.io.IOException;

/**
 *
 * @author Kuba
 */
public class Client {
    
    public static void main(String args[]){
    
        String filename = "edges.txt";
        NodeMap map = null;
        
        try{
            
            map = NodeMap.buildFromFile(filename);
            map.printStats("../../statistics.txt");
            
        }catch(Exception e){
        
            System.out.println("Error: " + e.getMessage());
        
        }
        
    }
    
}
