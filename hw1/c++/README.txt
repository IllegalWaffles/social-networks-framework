Name: 	Kuba Gasiorowski
NetID:  kgasiorowski
Email:  kuba.gasiorowski@stonybrook.edu OR kgasiorowski@cs.stonybrook.edu
ID:     109776237

Included in this zip are:

1. A plaintext file containing two numbers on each line, which represents an edge between two numbered nodes.
2. A c++ source file of a program which reads the plaintext file, and gives some basic statistics.
3. An executable of the above source file. The extension was changed to ".abc" in order to avoid problems
with antivirus. Simply change the executable back to ".exe" for a functional application.
4. This readme.

Currently, the plain text file contains 3,209 unique nodes, 76,695 unique edges, and takes approximately 1 minute 
to compute on my machine. There are no attributes associated with these nodes, but that can be easily added. 
This data represents Facebook, in other words, the graph is undirected (A -> B, if and only if B -> A).

Sample edge:
236 186 

Indicates a two-way edge between nodes with ID's of 236 and 186.

All data was sourced from: "https://snap.stanford.edu/data/egonets-Facebook.html". Note that only a portion of the
data is used, because the data set is too large to comfortably work with, and some statistics are already
calculated for the whole dataset. 