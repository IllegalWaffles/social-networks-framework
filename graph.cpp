#include <iostream>
#include <iomanip>
#include <string>
#include <fstream>
#include <vector>
#include <algorithm>
#include <ctime>
#include <unordered_map>

/*

	Author: Kuba Gasiorowski
	NetID: 	kgasiorowski
	ID:		109776237
	Email:	kuba.gasiorowski@stonybrook.edu OR kgasiorowski@cs.stonybrook.edu
	Github: https://github.com/IllegalWaffles

	Description:
	
	Expects two node ID's on each line of a plaintext file.
	Duplicate edges and node ID's are discarded. Just calculates 
	some stats based on input data for now. Works very slowly for
	files with more than 125,000 lines.
	
	TODO: Implement this program with a hash map. Probably would work much faster.

*/

/*HEADERS AND DEFINITIONS*/
using namespace std;

//NODE CLASS
struct Node{

	//Fields
	vector<Node*>* connections;
	unsigned short int ID;
	
	//Constructors
	Node(){
	
		ID = -1;
		connections = new vector<Node*>();
	
	}

	Node(unsigned short int ID_){
	
		ID = ID_;
		connections = new vector<Node*>();
	
	}

	//Functions
	bool operator==(const Node& node){
	
		return this->ID == node.ID;
	
	}

	bool operator!=(const Node& node){
	
		return !(*this == node);
		
	}
	
	void addConnection(Node *node){
	
		try{
			
			//Check if this nodes connections already has this node
			if(find(connections->begin(), connections->end(), node) == connections->end())
				connections->push_back(node);
	
		}catch(...){
		
			cerr << "Something went wrong with adding a connection!" << endl;
			exit(1);	
			
		}
	
	}

	vector<Node*>* getConnections(){
	
		return connections;
		
	}

};


//EDGE CLASS
struct Edge {
	
	unsigned short int v1, v2;
	
	Edge(){
		
		v1 = -1;
		v2 = -2;
	
	};
	
	Edge(unsigned short int v1_, unsigned short int v2_){
		
		v1 = v1_;
		v2 = v2_;
		
	}

	bool operator==(const Edge& edge){
	
		Edge thisEdge = *this;
	
		return ((edge.v1 == thisEdge.v1) && (edge.v2 == thisEdge.v2)) ||
			   ((edge.v1 == thisEdge.v2) && (edge.v2 == thisEdge.v1));
	
	}

};

bool vectorContainsEdge(vector<Edge>, Edge);
bool edgeCompare(Edge, Edge);
void addUniqueID(vector<unsigned short int>*, unsigned short int);
int addUniqueEdge(vector<Edge> *, Edge);

void processEdge(const unsigned short int, const unsigned short int, unordered_map<unsigned short int, Node*>);

/* SOURCE CODE */
int main(void)
{
	
	char holdopen;
	
	//Open input file
	string filename;
	cout << "Enter filename:";
	cin >> filename;
	fstream inputFile(filename.c_str(), ios_base::in);
	
	if(inputFile == NULL){
		
		cout << "File could not be opened" << endl;
		cin >> holdopen;
		return 1;
		
	}
	
	//Vars
	Edge newEdge;
	
	unordered_map<unsigned short int, Node*> nodeMap;
	
	vector<unsigned short int> nodeIDs;
	vector<Edge> edges;
	
	bool finished = false;
	
	unsigned short int node1ID = 0, node2ID = 0;
	unsigned int numEdges = 0, numlines = 0;
	
	clock_t oldTime = clock(), currTime = 0;
	clock_t diff = 0, startTime = clock();
	
	//Indicate beginning of calulations
	cout << "Beginning calculations..." << endl;
	//Read source node ID
	while(!finished){
		
		//Read both node ID's
		if(!(inputFile >> node1ID && inputFile >> node2ID))
			finished = true;
		
		//Show whatever progress is being made
		if(numlines % 1000 == 0 && numlines != 0){
		
			currTime = clock();
			diff = currTime - oldTime;
			
			cout << "Scanned " << numlines << " lines... That took ";
			cout << setw(5) << ((float)diff)/CLOCKS_PER_SEC << " seconds!" << endl;
			
			oldTime = currTime;
		
		}
		
		numlines++;

		processEdge(node1ID, node2ID, nodeMap);		

	
	}
	
	//Display some stats
	cout << "Done!" << endl;
	cout << "Number of edges: " << numEdges << endl;
	cout << "Number of unique nodes: " << nodeMap.size() << endl;
	
	diff = clock() - startTime;
	cout << "Amount of time taken: " << setw(4) << ((float)diff)/CLOCKS_PER_SEC << " seconds" << endl;
	
	//Hold the console window open
	cin >> holdopen;
	
	return 0;
	
}

void processEdge(const unsigned short int v1ID, const unsigned short int v2ID, unordered_map<unsigned short int, Node> nodeMap){

	Node node1;
	Node node2;
	
	//Figure out the first node
	if(nodeMap.count(v1ID) == 0){
		//No match found
		//Make a new node
		node1 = Node(v1ID);
		nodeMap.insert( {v1ID, node1} );
	}else if(nodeMap.count(v1ID == 1))
		//One match found
		//Fetch the node already present
		node1 = nodeMap.find(v1ID)->second;
	else
		//Too many matches found
		throw "Too many matches found, error";
	
	//Figure out the second node
	//Same as above
	if(nodeMap.count(v2ID) == 0){
		node2 = Node(v2ID);
		nodeMap.insert( {v2ID, node2} );
	}else if(nodeMap.count(v2ID == 1))
		node2 = nodeMap.find(v2ID)->second;
	else
		throw "Too many matches found, error";
	
	//Add these connections.
	node1.addConnection(&node2);
	node2.addConnection(&node1);
		
}

//Deprecated. Do not use
void addUniqueID(vector<unsigned short int> *v, unsigned short int ID){
	
	//Adds an ID to v. If it is already in v, do nothing.
	if(find(v->begin(), v->end(), ID) == v->end()){
		//IF the node ID is new from the ones already encountered
		
		try{
			//Add it to the vector
			v->push_back(ID);
		
		}catch(bad_alloc){
			
			cout << "Ran out of memory!" << endl;
			throw bad_alloc();
			
		}
		
	}
	
}

//Deprecated. Do not use
int addUniqueEdge(vector<Edge> *v, Edge edge){
	
	//Adds an edge to v. If it already has an equivalent edge, do nothing.
	if(!vectorContainsEdge(*v, edge)){
		
		try{
			
			v->push_back(edge);
			
		}catch(bad_alloc){
			
			cout << "Ran out of memory!" << endl;
			throw bad_alloc();
			
		}
		
	}
	
	return v->size();
	
}

//Deprecated. Do not use
bool vectorContainsEdge(vector<Edge> v, Edge edge){
	
	//Checks if v contains edge
	if(v.empty())
		return false;
	
	unsigned int i;
	for(i = 0; i < v.size(); i++)
		if(v[i] == edge)
			return true;
	
	return false;
	
}

//Deprecated. Do not use
bool edgeCompare(Edge edge1, Edge edge2){
	
	//Compares two edges
	return ((edge1.v1 == edge2.v1) && (edge1.v2 == edge2.v2)) ||
		   ((edge1.v1 == edge2.v2) && (edge1.v2 == edge2.v1));
	
}
