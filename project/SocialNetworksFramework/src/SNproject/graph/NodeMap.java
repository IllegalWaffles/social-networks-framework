package SNproject.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Kuba Gasiorowski
 * ID: 109776237
 * NetID : kgasiorowski
 */
public class NodeMap extends HashMap<Integer, Node>{
    
    private int edgecount;

    public NodeMap() {edgecount = 0;}
    public int getEdgecount(){return edgecount;}
    public void incEdgecount(){edgecount++;}
    
    public void printDegreeDistribution(String filename) throws IOException{
        
        HashMap<Integer, Integer> distributionMap = new HashMap<Integer, Integer>();
        boolean omitZeros = true;
        
        int highest_degree = -1;
        for(Node n : this.values())
            highest_degree = highest_degree < n.connections().size()? n.connections().size() : highest_degree;
        
        int degreeArray[] = new int[highest_degree+1];
        
        for(Node n : this.values())
            degreeArray[n.connections().size()]++;
        
        BufferedWriter out = new BufferedWriter(new PrintWriter(new File(filename)));
        out.write(String.format("%-6s, %-4s,\n", "Degree", "Freq"));
        
        for(int i = 1; i < degreeArray.length; i++)
            if(!(degreeArray[i] == 0 && omitZeros))
                out.write(String.format("%d, %d,\n", i, degreeArray[i]));
        
        out.close();
        
    }
    
    private int countTriangles(){
    
        HashMap<String, Triangle> trianglesList = new HashMap<String, Triangle>();
        Triangle triangle;
        
        int numTriangles = 0;
        
        for(Node n0 : this.values()) //Base level
        {
        
            int n0int = n0.getID();//For the sake of consistency
            for(int n1int : n0.connections()) //First level
            {
            
                Node n1 = get(n1int);
                for(int n2int : n1.connections()) //Second level
                {
                
                    if(get(n2int).connections().contains(n0.getID())){
                    
                        //triangle was found
                        triangle = new Triangle(n0int, n1int, n2int);
                        if(trianglesList.get(triangle.hashString()) == null){
                            
                            trianglesList.put(triangle.hashString(), triangle);
                            numTriangles++;
                            
//                            if(numTriangles % 10000 == 0)
//                                System.out.println("Counted " + numTriangles + " triangles");
                            
                        }
                        
                    }
                
                }
                
            }
        
        }
        
        return numTriangles;
    
    }
    
    public double clusteringCoefficient(int numNodes, double numTriangles){
    
        return numTriangles/nChoose3(numNodes);
    
    }
    
    public double clusteringCoefficient(){
    
        int numtriangles = this.countTriangles();
        
        return clusteringCoefficient(this.size(),numtriangles);
    
    }
    
    public void printStats(String filename) throws Exception{
    
        BufferedWriter out = new BufferedWriter(new PrintWriter(new File(filename)));
        
        int numTriangles;
        
        try{
            System.err.println("Printing degree distribution...");
            printDegreeDistribution(filename.substring(0, filename.indexOf(".txt")) + "-degree_distribution.csv");
            System.err.println("Done!");
        }catch(Exception e){
        
            throw new Exception("Make sure the output file ends with .txt");
        
        }
        
        System.err.println("Counting triangles... (this may take a long time)");
        numTriangles = countTriangles();
        System.err.println("Done!");
        
        System.err.println("Printing the rest of the stuff...");
        out.write("STATISTICS FOR GRAPH\n");
        out.write("Number of unique nodes: " + this.size());
        out.write("\nNumber of edges: " + this.edgecount);
        out.write("\nNumber of triangles counted: " + numTriangles);
        out.write(String.format("\nClustering coefficient: %f", clusteringCoefficient(this.values().size(), numTriangles)));
        out.close();
        
        System.err.println("Done!\nExiting...");
        
    }
    
    public static NodeMap buildFromFile(String filename) throws IOException{
    
        NodeMap map = new NodeMap();
        int counter = 0;
        Scanner sc = new Scanner(new File(filename));
        
        while(sc.hasNextLine())
        {
        
            String temp[] = sc.nextLine().split(" ");
            
            int v1 = Integer.parseInt(temp[0]);
            int v2 = Integer.parseInt(temp[1]);
            
            Node nodeV1, nodeV2;
            
            if(map.containsKey(v1))  {  
                nodeV1 = map.get(v1);
            }else
                nodeV1 = new Node(v1);
            
            if(map.containsKey(v2))
                nodeV2 = map.get(v2);
            else
                nodeV2 = new Node(v2);
                
            if(Node.connect(nodeV1, nodeV2))
                map.incEdgecount();
            
            map.put(v1, nodeV1);
            map.put(v2, nodeV2);
            
        }
        
        return map;
    
    }
    
    public static double nChoose3(int n){
    
        double temp = n;
        for(int i = 1; i < 3; i++)
            temp *= n-i;
    
        return temp/(double)6;
        
    }
    
    public boolean addConnection(int ID1, int ID2){
        
        Node node1, node2;
        
        if(!containsKey(ID1) || !containsKey(ID2) || ID1 <= 0 || ID2 <= 0)
            return false;
        
        node1 = get(ID1);
        node2 = get(ID2);
        
        if(node1.connect(node2));
            incEdgecount();
        
        return true;
        
        
    }
    
}
