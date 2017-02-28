#include "def.h"
#include "debug.h"

#include <algorithm>

//Processes an edge, consisting of two int ID's.

//Adds each node to the map if they don't exist, or gets
//from the map if it does exist.

//Then adds a connection to each node, unless such a connection already exists.
//In which case, the function does nothing and returns false;
bool processEdge(const unsigned short int v1ID, const unsigned short int v2ID, NodeMap& nodeMap){

    Node *node1 = NULL;
    Node *node2 = NULL;

    //Figure out the first node
    if(nodeMap.count(v1ID) == 0){
        //No match found
        //Make a new node
        node1 = new Node(v1ID);
        nodeMap.insert( {v1ID, node1} );

    }else if(nodeMap.count(v1ID) == 1)
        //One match found
        //Fetch the node already present
        node1 = nodeMap.find(v1ID)->second;
    else
        //Too many matches found
        throw "Too many matches found, error";

    //Figure out the second node
    //Same as above 
    if(nodeMap.count(v2ID) == 0){

        node2 = new Node(v2ID);
        nodeMap.insert( {v2ID, node2} );

    }else if(nodeMap.count(v2ID) == 1){

        node2 = nodeMap.find(v2ID)->second;

    }
    else
        throw "Too many matches found, error";

    //Check if either node is already connected to the other.
    //If there exists such a connection, it's not unique,
    //so we can return false without doing anything.
    if(node1->hasConnection(node2) || node2->hasConnection(node1)){

        return false;

    }
    //Otherwise, add the connection to each node.
    else
    {
        //Add these connections.
        node1->addConnection(node2);
        node2->addConnection(node1);
        return true;
        
    }
}

long long computeTriangles(const NodeMap map, bool verbose){
    
    //Keep track of every triangle found, so that there are no repeats
    //Keys for each triangle take the following form: 
    //lowestID + " " + middleID + " " + highestID
    TriangleMap triangleMap;
    register unsigned int counter = 0;
    
    //Iterate through the node map
    for(auto const& pair : map){
        
        //Now iterate through the base node's neighbors
        Node* basenode_addr = pair.second;
        Node basenode = *(pair.second);
        
        if(basenode_addr == NULL)
            continue;
        
        cout << "Base node id: " << basenode.ID << endl;
        
        for(unsigned int i = 0; i < basenode.connCount; i++){
        
            Node firstlevel = *(basenode.getConnections()->at(i));
            cout << "\tFirst level id: " << firstlevel.ID << endl;
            
            //Now iterate one more level deep
            for(unsigned int j = 0; j < firstlevel.connCount; j++){
            
                Node secondlevel = *(firstlevel.getConnections()->at(j));
                cout << "\t\tSecond level id: " << secondlevel.ID << endl;
                
                if(vectorContains(*(secondlevel.getConnections()), basenode_addr)){
                
                    cout << "\t\t\tTriangle found ";
                    cout << ++counter << endl;
                
                }
                
            
            }
            
        
        }
        
    
    }
    
    cout << "Total size: " << map.size() << endl;
    
    return 0;

}

template<typename T>
bool vectorContains(const vector<T> vec, const T t_type){

    return find(vec.begin(), vec.end(), t_type) != vec.end();

}