import java.util.*;
import java.io.*;

// An abstraction over vector<int>. To be used for storing graph as an adjacency list
public class Node{
	Vector<Integer> v;
	public Node(){
		v = new Vector<Integer>();
	}
	public int get(int i){
		return v.get(i);
	}
	public void add(int x){
		v.add(x);
	}
	public int size(){
		return v.size();
	}
}