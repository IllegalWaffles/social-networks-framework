/*

        Author: Kuba Gasiorowski
        NetID: 	kgasiorowski
        ID:		109776237
        Email:	kuba.gasiorowski@stonybrook.edu OR kgasiorowski@cs.stonybrook.edu
        Github: https://github.com/IllegalWaffles

        Description:

        Expects two node ID's on each line of a plaintext file.
        Duplicate edges and node ID's are discarded. Just calculates 
        some stats based on input data for now.

*/

#include <iostream>
#include <iomanip>
#include <string>
#include <fstream>
#include <ctime>

#include "def.h"
#include "debug.h"

/* SOURCE CODE */
int main(void)
{
    
    //Open input file
    string filename = "edges.txt";
//    cout << "Enter filename:";
//    cin >> filename;
    fstream inputFile(filename.c_str(), ios_base::in);

    if(inputFile == NULL){

        cout << "File could not be opened" << endl;
        return 1;

    }

    //Vars
    NodeMap nodeMap;

    bool finished = false;
    unsigned short int node1ID = 0, node2ID = 0;
    unsigned int numlines = 0;
    register int edgecount = 0;	

    clock_t oldTime = clock(), currTime = 0;
    clock_t diff = 0, startTime = clock();

    //Indicate beginning of calulations
    cout << "Starting calculations..." << endl;
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

        //Try to process the edge.
        try{
            //If processEdge was true, then the edge was unique.
            if(processEdge(node1ID, node2ID, nodeMap))
                edgecount++;

        }catch(const char* excp){

            fprintf(stderr, "ERROR CAUGHT: %s", excp);

        }	

    }

    //Display some stats
    cout << endl << "Done!" << endl;

    cout << endl <<  "--------------------------------------" << endl;
    cout << "Number of edges: " << edgecount << endl;
    cout << "Number of unique nodes: " << nodeMap.size() << endl;

    diff = clock() - startTime;
    cout << "Amount of time taken: " << setw(4) << ((float)diff)/CLOCKS_PER_SEC << " seconds" << endl;
    cout << "--------------------------------------" << endl << endl;
    
    cout << "Counting total triangles.. " << endl;
    //long long numTriangles = computeTriangles(nodeMap, true);
    cout << "Done!" << endl;
    
    
    
//    cout << "Number of triangles: " << numTriangles << endl;
    cout << "Enter any char to continue..." << endl;
    
    return 0;

}
