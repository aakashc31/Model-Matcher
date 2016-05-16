import java.util.*;
import java.io.*;

// This data structure represents a point in 3-D space
public class Point{
	
	public double x, y, z;
	
	public Point(){
		x = 0; y = 0; z = 0;
	}
	
	public Point(double a, double b, double c){
		x = a; y = b; z = c;
	}
	
	public void println(){
		System.out.println(x + " " + y + " " + z);
	}

	public void print(){
		System.out.print(x + " " + y + " " + z);
	}

	public vec3 diffVector(Point p){
		return (new vec3(p.x - x, p.y - y, p.z - z));
	}

	public Boolean isLessThan(Point p){
		if(x == p.x){
			if(y == p.y){
				return z < p.z;
			}
			return y < p.y;
		}
		return x < p.x;
	}

	// returns 0 if both are equal. -1 is < p, +1 if > p
	public int compare(Point p){
		if(x == p.x && y == p.y && z == p.z)
			return 0;
		if(isLessThan(p))
			return -1;
		return 1;
	}

	@Override
	public boolean equals(Object ob){
		Point p = (Point)ob;
		return (this.compare(p) == 0);
	}

	@Override 
	public int hashCode(){
		return (int)(x + y + z);
	}
}