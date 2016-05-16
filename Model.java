import java.util.*;
import java.io.*;

public class Model{
	
	public String modelName; //name of the file
	public Vector<Facet> v; //stores the list of all facets
	public Node[] Graph; //Graph[i] stores the indices of all the facets adjacent to v[i]
	public MyHashMap<Double,Integer> dihedralAngles;
	public int sharedEdges, openEdges, numEdges, numTriangles, numVertices;
	public Boolean isCorrect;

	public void readModelFromFile(String filename){
		try{
			v = new Vector<Facet>();
			MySet<Facet> facets = new MySet<Facet>();
			MySet<Point> allVertices = new MySet<Point>();
			FileInputStream fstream = new FileInputStream(filename);
			Scanner ob = new Scanner(fstream); String line;
			double[] x = new double[3];
			int id = 0;
			while(ob.hasNextLine()){
				line = ob.nextLine(); line.trim();
				String[] sp = line.split(" ");
				Facet ft = new Facet(id);
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						x[j] = Double.parseDouble(sp[3*i + j]);
					}
					Point currPoint = new Point(x[0], x[1], x[2]);
					allVertices.add(currPoint);
					ft.addVertex(currPoint);
				}
				ft.createEdges();
				// v.add(ft);
				facets.add(ft);
				id++;
			}
			Iterator<Facet> it = facets.keyVector().iterator();
			while(it.hasNext())
				v.add(it.next());
			numVertices = allVertices.size();
			numTriangles = v.size();
			modelName = filename.substring(filename.lastIndexOf('\\') + 1);
		}
		catch(FileNotFoundException e){
			System.out.println(filename + " File not found!");
		}
	}
	
	public void print(){
		System.out.println("\nModel name = " + modelName);
		for(int i=0; i<v.size(); i++)
			v.get(i).print();
		System.out.println("numVertices = " + numVertices + " numEdges = " + numEdges + " numTriangles = " + numTriangles + "\n");
	}

	// After this function is called, all coplanar adjacent Facets will have been merged and Graph created
	// also open and shared edges count, isCorrect. will be computed
	public void merge(){
		int numFacets = v.size();
		Graph = new Node[numFacets];
		Boolean[] isValid = new Boolean[numFacets];
		for(int i=0; i<numFacets; i++)
			isValid[i] = true;
		for(int i=0; i<numFacets; i++){
			if(!isValid[i])
				continue;
			Facet f1 = v.get(i);
			for(int j=0; j<numFacets; j++){
				if(!isValid[j] || (i == j))
					continue;
				Facet f2 = v.get(j);
				if(f1.isMergeSuccessfull(f2))
					isValid[j] = false;
			}
		}
		Vector<Facet> finalFacets = new Vector<Facet>();
		for(int i=0; i<numFacets; i++){
			if(isValid[i])
				finalFacets.add(v.get(i));
		}
		v = finalFacets;
		numFacets = v.size();
		MyHashMap<Edge, Vector<Integer>> edgeToIndex = new MyHashMap<Edge, Vector<Integer>>();
		for(int i=0; i<numFacets; i++){
			Facet f = v.get(i);
			Graph[i] = new Node();
			for(int j=0; j<f.edges.size(); j++){
				Edge e = f.edges.get(j);
				if(edgeToIndex.containsKey(e)){
					Vector<Integer> temp = edgeToIndex.get(e);
					for(int k=0; k<temp.size(); k++){
						Graph[i].add(temp.get(k));
						Graph[temp.get(k)].add(i);
					} 
					temp.add(i);
				}
				else{
					Vector<Integer> temp = new Vector<Integer>();
					temp.add(i);
					edgeToIndex.put(e,temp);
				}
			}
		}
		openEdges = 0; sharedEdges = 0; isCorrect = true;
		Vector<Edge> allEdges = edgeToIndex.keySet().keyVector();
		numEdges = allEdges.size();
		Iterator<Edge> it = allEdges.iterator();
		while(it.hasNext()){
			Edge e = it.next();
			Vector<Integer> temp = edgeToIndex.get(e);
			if(temp.size() == 1)
				openEdges++;
			else
				sharedEdges++;
			if(temp.size() > 2)
				isCorrect = false;
		}
	}

	public void computeDihedralAngles(){
		dihedralAngles = new MyHashMap<Double, Integer>();
		int next;
		int numFacets = v.size();
		for(int i=0; i<numFacets; i++){
			for(int j=0; j<Graph[i].size(); j++){
				next = Graph[i].get(j);
				if(next < i) //compute the angles in ordered pair.
					continue;
				double angle = v.get(i).ComputeDihedralAngle(v.get(next));
				int value = 1;
				if(dihedralAngles.containsKey(angle))
					value += dihedralAngles.get(angle);
				dihedralAngles.put(angle, value);
			}
		}
	}

	public Boolean isSimilar(Model m){
		double epsilon = 0.0001;
		Boolean ret = (sharedEdges == m.sharedEdges && openEdges == m.openEdges && numEdges == m.numEdges && numTriangles == m.numTriangles && numVertices == m.numVertices);
		if(!ret)
			return ret;
		if(isCorrect != m.isCorrect)
			return false;
		Vector<Double> s1 = dihedralAngles.keySet().keyVector(), s2 = m.dihedralAngles.keySet().keyVector();
		if(s1.size() != s2.size())
			return false;
		Collections.sort(s1); Collections.sort(s2);
		Iterator<Double> it1 = s1.iterator(), it2 = s2.iterator();
		double v1, v2;
		for(int i=0; i<s1.size(); i++){
			v1 = it1.next(); v2 = it2.next();
			if(Math.abs(v1 - v2) > epsilon)
				return false;
			if(dihedralAngles.get(v1) != m.dihedralAngles.get(v2))
				return false;
		}
		return true;
	}
}