#include "def.h"
#include "debug.h"

//Processes an edge, consisting of two int ID's.

//Adds each node to the map if they don't exist, or gets
//from the map if it does exist.

//Then adds a connection to each node, unless such a connection already exists.
//In which case, the function does nothing and returns false;
bool processEdge(const unsigned short int v1ID, const unsigned short int v2ID, unordered_map<unsigned short int, Node*>* nodeMap){

	Node *node1 = NULL;
	Node *node2 = NULL;

	//Figure out the first node
	if(nodeMap->count(v1ID) == 0){
		//No match found
		//Make a new node
		node1 = new Node(v1ID);
		nodeMap->insert( {v1ID, node1} );

	}else if(nodeMap->count(v1ID) == 1)
		//One match found
		//Fetch the node already present
		node1 = nodeMap->find(v1ID)->second;
	else
		//Too many matches found
		throw "Too many matches found, error";

	//Figure out the second node
	//Same as above
	if(nodeMap->count(v2ID) == 0){

		node2 = new Node(v2ID);
		nodeMap->insert( {v2ID, node2} );
		
	}else if(nodeMap->count(v2ID) == 1){
		
		node2 = nodeMap->find(v2ID)->second;

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
