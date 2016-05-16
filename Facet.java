import java.util.*;
import java.io.*;

public class Facet{
	
	public Vector<Point> pts;
	public Vector<Edge> edges;
	int id;
	
	public Facet(int i){
		pts = new Vector<Point>();
		edges = new Vector<Edge>();
		id = i;
	}

	public void setId(int i){
		id = i;
	}
	
	public int getId(){
		return id;
	}
	
	public void print(){
		System.out.println("Facet id = " + id);
		for(int i=0; i<edges.size(); i++)
			edges.get(i).println();
		System.out.println("");
	}

	public void addVertex(Point p){
		pts.add(p);
	}

	// based on the vertices, it creates the edges. Used here only for triangle.
	public void createEdges(){
		for(int i=0; i<pts.size(); i++){
			for(int j=i+1; j<pts.size(); j++){
				Edge e = new Edge(pts.get(i), pts.get(j));
				edges.add(e);
			}
		}
	}

	// returns normal unit vector to the plane of facet
	public vec3 getNormal(){
		if(pts.size() <= 2){
			return (new vec3());
		}
		vec3 v1 = pts.get(0).diffVector(pts.get(1));
		vec3 v2 = pts.get(0).diffVector(pts.get(2));
		vec3 ret = v1.crossProduct(v2);
		ret.normalize();
		return ret;
	}

	public double ComputeDihedralAngle(Facet f){
		vec3 v1 = getNormal();
		vec3 v2 = f.getNormal();
		return Math.abs(v1.angle(v2));
	}

	//does not upadte pts
	//merges current facet with f, if both are adjacent and coplanar
	public Boolean isMergeSuccessfull(Facet f){
		MySet<Edge> s = new MySet<Edge>();
		for(int i=0; i<edges.size(); i++)
			s.add(edges.get(i));
		Edge commonEdge = new Edge(); Boolean isAdjacent = false;
		for(int i=0; i<f.edges.size(); i++){
			if(s.contains(f.edges.get(i))){
				isAdjacent = true;
				commonEdge = f.edges.get(i);
			}
		}
		if(!isAdjacent){
			// System.out.println(id + " is not adjacent to " + f.id);
			return false;
		}
		// System.out.println(id + " is adjacent to " + f.id);

		vec3 v1 = getNormal(); vec3 v2 = f.getNormal();
		if(v1.isParallel(v2)){
			// System.out.println(id + " is coplanar to " + f.id);
			edges.removeElement(commonEdge);
			for(int i=0; i<f.edges.size(); i++){
				if(commonEdge.equals(f.edges.get(i)))
					continue;
				edges.add(f.edges.get(i));
			}
			return true;
		}
		// System.out.println(id + " is not coplanar to " + f.id);
		// System.out.println(id + "normal = " + v1.x + " " + v1.y + " " + v1.z);
		// System.out.println(f.id + "normal = " + v2.x + " " + v2.y + " " + v2.z);
		return false;
	}

	@Override
	public boolean equals(Object ob){
		Facet f = (Facet)ob;
		MySet<Point> vert = new MySet<Point>();
		MySet<Point> vert2 = new MySet<Point>();

		for(int i=0; i<pts.size(); i++)
			vert.add(pts.get(i));
		
		for(int i=0; i<f.pts.size(); i++){
			if(!vert.contains(f.pts.get(i)))
				return false;
			vert2.add(f.pts.get(i));
		}
		
		for(int i=0; i<pts.size(); i++){
			if(!vert2.contains(pts.get(i)))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode(){
		return pts.size();
	}
}