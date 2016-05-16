import java.util.*;
import java.io.*;

// For representing vectors in 3-D space
public class vec3{
	public double x,y,z;

	public vec3(){
		x = 0; y = 0; z = 0;
	}

	public vec3(double a, double b, double c){
		x = a; y = b; z = c;
	}

	public double magnitude(){
		return Math.sqrt(x*x + y*y + z*z);
	}

	public void normalize(){
		double norm = magnitude();
		if(norm == 0)
			return;
		x /= norm; y /= norm; z /= norm;
	}

	public vec3 crossProduct(vec3 v){
		double a,b,c;
		a = y*v.z - z*v.y;
		b = z*v.x - x*v.z;
		c = x*v.y - y*v.x;
		return (new vec3(a,b,c));
	}

	public double dotProduct(vec3 v){
		return x*v.x + y*v.y + z*v.z;
	}

	public double angle(vec3 v){
		double cos = dotProduct(v)/(magnitude() * v.magnitude());
		cos = (Math.round(cos*10000))/10000;
		return cos;
	}

	// assumes normalized
	public Boolean isParallel(vec3 v){
		double epsilon = 0.0001;
		if(Math.abs(x - v.x) <= epsilon && Math.abs(y - v.y) <= epsilon && Math.abs(z - v.z) <= epsilon) //parallel
			return true;
		if(Math.abs(x + v.x) <= epsilon && Math.abs(y + v.y) <= epsilon && Math.abs(z + v.z) <= epsilon) //anti parallel
			return true;
		return false;
	}
}
