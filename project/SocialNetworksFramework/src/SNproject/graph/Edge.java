package SNproject.graph;

/**
 *
 * @author Kuba Gasiorowski
 */
public class Edge {
    
    public int v1, v2;
    
    private Edge(){
    
        this(0, 0);
    
    }
    
    public Edge(int _v1, int _v2){
    
        v1 = _v1;
        v2 = _v2;
    
    }
    
    public String getHashString(){
    
        return Integer.toString(Math.max(v1, v2)) + " " + Integer.toString(Math.min(v1, v2));
    
    }
    
    public boolean equals(Edge other){
    
        return (this.v1 == other.v1 && this.v2 == other.v2) || 
               (this.v2 == other.v1 && this.v1 == other.v2); 
    
    }
    
}
