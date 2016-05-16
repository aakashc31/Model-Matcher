
This code was written for an assignment in Data Structures course at IIT Delhi.

Link: http://www.cse.iitd.ac.in/~subodh/courses/COL106/a6.html

---------------------------------------------------------------------

Description of the assignment:

In this assignment, you will deal with 3D geometric models. The first step is to read and store in a data structure one model at a time. A model is listed triangle by triangle, one triangle per line in the following format:
X1 Y1 Z1 X2 Y2 Z2 X3 Y3 Z3
The given values are strings representing decimal values of the X, Y, and Z coordinates of each vertex of the triangle.
The internal data structure will store the model as a graph. In fact, you will read all the files in a given input directory. Thus there may be a number of 3D models, aka graphs.

The goal is to find, for each 3D model, other models with a similar shape. Two models have a similar shape if minor modifications to one of the models yields the other one. The modifications can be one of these:

merger of adjacent triangles lying on the same plane (Two triangles are adjacent if they share an edge.)
a rotation, translation or scale

As an approximation, after merger of planar facets, two models match if they have the same
number of triangles, edges and vertices
number of shared and open edges (a shared edge is shared by two trianglesfacets, an open edge belongs to only one trianglefacet.)
set of all dihedral angles (Dihedral angle is the angle between adjacent polygon's planes)
correctness (a model is correct if no edge is shared by more than two trianglesfacets)

You may use a hash function to check for matches. The output will contain one line per input model. The first word on the line is the name of the model file. It is followed by blank-separated names all its matching models' file names. The output is in the sorted order of model file names. 

Merger of all faces, polygon or triangle, is to be supported, but no test case will include two adjacent facets with multiple shared edges.
Remove any duplicate polygons after mergers.
Two triangle share and edge if two of their vertices are co-located. If all three vertices are co-located, the triangles are duplicate, and one may be removed.
You may simplify the computation of dihedral angles between polygons by simply taking the smallest angle between their planes. (Absolute value of the dot product of their normals will do.)
Please implement your own Hash table. Do not use any other implementation (like JAVA's).
You should count all consituent triangles of polygons, i.e., the number of triangles before the merger.

--------------------------------------------------------------------------------

Instructions for use

For compiling:
	Windows: 
		run "compile.bat" on command line or just double click 
	Linux:
		make command 

Running:
	java ModelMatcher <location of folder with files>


Also there are scripts "runner.bat" and "checker.bat", which I used to check this code against the given test cases. 

