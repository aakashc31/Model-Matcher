import java.util.*;
import java.io.*;

// Ordered pair of edges
public class Edge{
	
	public Point x,y; // x<=y
	public Edge(){
		x = new Point();
		y = new Point();
	}

	public Edge(Point a, Point b){
		if(a.compare(b) <= 0){
			x = a; y = b;
		}
		else{
			x = b; y = a;
		}
	}

	@Override
	public boolean equals(Object Ob){
		Edge e = (Edge)Ob;
		return (x.compare(e.x) == 0 && y.compare(e.y) == 0);
	}

	@Override
	public int hashCode(){
		return (int)(x.x + x.y +x.z + y.x + y.y + y.z);
	}

	public void println(){
		x.print(); System.out.print(" -> "); y.println();
	}
}
