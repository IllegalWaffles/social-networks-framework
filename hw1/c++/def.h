#ifndef DEF
#define DEF

#include <vector>
#include <iostream>
#include <algorithm>
#include <unordered_map>

/*HEADERS AND DEFINITIONS*/
using namespace std;

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

bool processEdge(const unsigned short int, const unsigned short int, unordered_map<unsigned short int, Node*>*);

#endif
