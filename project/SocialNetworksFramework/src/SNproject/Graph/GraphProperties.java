package SNproject.Graph;

/**
 *
 * @author Kuba
 */
public class GraphProperties{
    
    private double link_prob;
    private int num_nodes;
    private int max_num_edges;
    
    public void setMaxNumEdges(int i){
        max_num_edges = i;
    }
    
    public void setLinkProb(double d){
        link_prob = d;
    }
    
    public void setNumNodes(int i){
        num_nodes = i;
    }
    
    public int getMaxNumEdges(){
        return max_num_edges;
    }
    
    public double getLinkProb(){
        return link_prob;
    }
    
    public int getNumNodes(){
        return num_nodes;
    }
    
}
