package default_pkg;

/**
 *
 * @author Kuba
 */
public class Triangle {

    public int v1, v2, v3;
    
    public Triangle(int _v1, int _v2, int _v3){
    
        v1 = _v1;
        v2 = _v2;
        v3 = _v3;
        
    }
    
    public boolean equals(Triangle other){
    
        int thisVerts[] = parseMinMax();
        int otherVerts[] = other.parseMinMax();
        
        if(thisVerts[0] == otherVerts[0] && thisVerts[1] == otherVerts[1] && thisVerts[2] == otherVerts[2])
            return true;
        else
            return false;
    
    }
    
    public String hashString(){
    
        int temp[] = parseMinMax();
        
        String hash = "";
        hash += Integer.toString(temp[0]) + " ";
        hash += Integer.toString(temp[1]) + " ";
        hash += Integer.toString(temp[2]);
        
        return hash;
    
    }
    
    private int [] parseMinMax(){
    
        int temp[] = new int[3];
        
        temp[0] = Math.min(v1, Math.min(v2, v3));   //min
        temp[2] = Math.max(v1, Math.max(v2, v3));   //max
        temp[1] = v1 + v2 + v3 - temp[0] - temp[2]; //mid
        
        return temp;
    
    }
    
}
