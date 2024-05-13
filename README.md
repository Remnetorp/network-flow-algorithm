# network-flow
-----------------------------------------------------------------------------------------------------------------------------
This is a program to find the out how many "routes" (or edges) between the cities (or nodes), that can be removed in a graph before
the defined min requirement of flow from root to end is unfulfilled. All edges in the graph has a capacity defined by the input data.
In the input data there is also a list of edges IDs, and they will be removed in that order as long as the capacity min requirement
is fulfilled. 

------------------------------------------------------------------------------------------------------------------------------
The first line of input consists of a 4 different integer. The first integer is number of nodes, the second integer is the number of 
edges, the third integer is the minimum flow from start to finish, and the last integer is the amount of edges to remove from the 
network. Then follow N lines matching the number of edges, every line contains three integers to represent a edge, the two first 
integers are the numbers of the two nodes and the third integer is the capacity of the edge. Then follow R lines matching
the number of edges to remove, each lines consisting of the a integer representing the edge number.

To run the program:
(1) $ javac NetworkFlow.java Node.java Edge.java
(2) $ java NetworkFlow < data-input/data-input1.in
