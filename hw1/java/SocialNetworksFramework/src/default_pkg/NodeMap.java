/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_pkg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.math.BigInteger;

/**
 *
 * @author Kuba
 */
public class NodeMap extends HashMap<Integer, Node>{
    
    private int edgecount;

    public NodeMap() {
        edgecount = 0;
    }
    
    
    public int getEdgecount(){
        return edgecount;
    }
    
    public void incEdgecount(){
        edgecount++;
    }
    
    public void printDegreeDistribution(String filename) throws IOException{
        
        HashMap<Integer, Integer> distributionMap = new HashMap<Integer, Integer>();
        
        int highest_degree = -1;
        for(Node n : this.values())
            highest_degree = highest_degree < n.connections().size()? n.connections().size() : highest_degree;
        
        int degreeArray[] = new int[highest_degree+1];
        
        for(Node n : this.values())
            degreeArray[n.connections().size()]++;
        
        BufferedWriter out = new BufferedWriter(new PrintWriter(new File(filename)));
        out.write(String.format("%-6s %-4s\n", "Degree", "Freq"));
        
        for(int i = 1; i < degreeArray.length; i++)
            out.write(String.format("%-6d %-4d\n", i, degreeArray[i]));
        
        out.close();
        
    }
    
    public int countTriangles(){
    
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
                            
                        }
                        
                    }
                
                }
                
            }
        
        }
        
        return numTriangles;
    
    }
    
    public void printStats(String filename) throws Exception{
    
        BufferedWriter out = new BufferedWriter(new PrintWriter(new File(filename)));
        
        int numTriangles;
        
        try{
            System.err.println("Printing degree distribution...");
            printDegreeDistribution(filename.substring(0, filename.indexOf(".txt")) + "-DD.txt");
            System.err.println("Done!");
        }catch(Exception e){
        
            throw new Exception("Make sure the output file ends with .txt");
        
        }
        
        System.err.println("Counting triangles... (this may take a long time)");
        //numTriangles = countTriangles();
        System.err.println("Done!");
        
        System.err.println("Printing the rest of the stuff...");
        out.write("STATISTICS FOR GRAPH\n");
        out.write("Number of unique nodes: " + this.size());
        out.write("\nNumber of edges: " + this.edgecount);
        //out.write("\nNumber of triangles counted: " + numTriangles);
        out.write("\n3209! = " + factorial(3209));
        out.close();
        
        System.err.println("Done!\nExiting...");
        
    }
    
    private BigInteger recfact(long start, long n) 
    {

        long i;

        if (n <= 16) { 
            BigInteger r = new BigInteger(Long.toString(start));
            for (i = start + 1; i < start + n; i++) 
                r = r.multiply(new BigInteger(Long.toString(i)));
            return r;
        }

        i = n / 2;

        return recfact(start, i).multiply(recfact(start + i, n - i));

    }

    public BigInteger factorial(long n) { return recfact(1, n); }
    
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
    
}
