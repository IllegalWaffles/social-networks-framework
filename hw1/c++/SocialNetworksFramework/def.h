#ifndef DEF
#define DEF

#include <vector>
#include <iostream>
#include <algorithm>
#include <unordered_map>

/*HEADERS AND DEFINITIONS*/
using namespace std;

//Util functions
template<typename T>
bool vectorContains(vector<T>, T);
//void printDegreeDistribution(String s);

/***********************************************************/
//NODE CLASS
struct Node{

    //Fields
    vector<Node*>* connections;
    unsigned short int ID;
    unsigned short int connCount;

    //Constructors
    Node(){

        ID = -1;
        connCount = 0;
        connections = new vector<Node*>();

    }

    Node(unsigned short int ID_){

        ID = ID_;
        connCount = 0;
        connections = new vector<Node*>();

    }

    //Destructor
    ~Node(){

        delete connections;

    }

    //Functions
    //Comparator functions for nodes
    bool operator==(const Node& node){

        return this->ID == node.ID;

    }

    bool operator!=(const Node& node){

        return !(*this == node);

    }

    //Does not check for duplicate connections. Beware!
    void addConnection(Node *node){

        try{
            connections->push_back(node);
            connCount++;
        }catch(...){
            cerr << "Something went wrong with adding a connection!" << endl;
            exit(1);
        }
    }

    //Return a list of connections that this node has
    vector<Node*>* getConnections(){

        return connections;

    }

    //Returns true if a certain connection exists, false otherwise
    bool hasConnection(Node* node){

        return find(connections->begin(), connections->end(), node) != connections->end();

    }

};
/***********************************************************/

typedef unordered_map<unsigned short int, Node*> NodeMap;

bool processEdge(const unsigned short int, const unsigned short int, NodeMap&);
long long computeTriangles(const NodeMap, bool);

/***********************************************************/
//Triangle class
struct Triangle{

    int v1, v2, v3;
    
    Triangle(){
        Triangle(-1,-1,-1);
    }
    
    Triangle(int _v1, int _v2, int _v3){
        v1 = _v1, v2 = _v2, v3 = _v3;
    }
    
    bool operator==(const Triangle other){
        
        if(v1 == other.v1 || v1 == other.v2 || v1 == other.v3)
            if(v2 == other.v1 || v2 == other.v2 || v2 == other.v3)
                if(v3 == other.v1 || v3 == other.v2 || v3 == other.v3)
                    return true;
            
        return false;
        
    }
    
    bool operator!=(const Triangle other){
        return !(*this == other);
    }
    
    Triangle* operator=(int array[]){
    
        v1 = array[0];
        v2 = array[1];
        v3 = array[2];
        
        return this;
    
    }
    
};

typedef unordered_map<string, Triangle> TriangleMap;
/***********************************************************/

#endif
