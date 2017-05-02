package SNproject.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Kuba Gasiorowski
 * ID: 109776237
 * NetID : kgasiorowski
 */
public class EdgeMap extends HashMap<String, Edge>{
    
    public static EdgeMap buildEdgeMap(NodeMap nodemap){
    
        EdgeMap edgemap = new EdgeMap();
        Edge newedge;
        
        for(Node n : nodemap.values()){
        
            for(int c : n.connections()){
            
                newedge = new Edge(n.getID(), c);
                if(!edgemap.containsKey(newedge.getHashString()))
                    edgemap.put(newedge.getHashString(), newedge);
                    
            }
        
        }
        
        return edgemap;
    
    }
    
    public void printNeighborhoodOverlap(String filename, NodeMap nodemap) throws IOException {
    
        BufferedWriter out = new BufferedWriter(new PrintWriter(new File(filename)));
        out.write("V1   V2   NHO\n");
        
        for(Edge e : this.values())
        {

            Node n1 = nodemap.get(e.v1);
            Node n2 = nodemap.get(e.v2);
        
            int numIntersection = Node.intersection(n1, n2);
            int numUnion = Node.union(n1, n2);
            
            out.write(String.format("%d %d %f\n", n1.getID(), n2.getID(), (double)numIntersection/(double)numUnion));
            
        }

        out.close();
    
    }
    
}
