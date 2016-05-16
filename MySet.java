// HashSet implementation using linear probing for conflicts

import java.io.*;
import java.util.*;

public class MySet<Key>{
	class Node{
		Key k;
		Node next;
		public Node(Key kp){
			k = kp;
			next = null;
		}
	}

	int maxn;
	Vector<Node> ar;
	int size;

	public MySet(){
		maxn = 10000;
		ar = new Vector<Node>();
		for(int i=0; i<maxn; i++)
			ar.add(null);
		size = 0;
	}

	public void add(Key k){
		int index = Math.abs(k.hashCode()) % maxn;
		if(ar.get(index) == null){
			ar.setElementAt(new Node(k), index);
			size++;
		}
		else{
			if(this.contains(k))
				return;
			Node curr = new Node(k);
			curr.next = ar.get(index);
			ar.setElementAt(curr, index);
			size++;
		}
	}

	public boolean contains(Key k){
		int index = Math.abs(k.hashCode()) % maxn;
		Node curr = ar.get(index);
		while((curr != null) && (!curr.k.equals(k)))
			curr = curr.next;
		return (curr != null);
	}

	public void clear(){
		ar.clear();
		for(int i=0; i<maxn; i++)
			ar.add(null);
		size = 0;
	}

	public boolean isEmpty(){
		return size==0;
	}

	public int size(){
		return size;
	}

	public void remove(Key k){
		int index = Math.abs(k.hashCode()) % maxn;
		Node curr = ar.get(index);
		if(curr == null)
			return;
		if(curr.k.equals(k)){
			ar.setElementAt(curr.next,index);
			return;
		}
		Node prev = ar.get(index); curr = prev.next;
		while((curr != null) && (!curr.k.equals(k))){
			prev = curr;
			curr = curr.next;
		}
		if(curr == null)
			return;
		prev.next = curr.next; //deleting curr
	}

	public Vector<Key> keyVector(){
		Vector<Key> ret = new Vector<Key>();
		for(int i=0; i<maxn; i++){
			Node curr = ar.get(i);
			while(curr != null){
				ret.add(curr.k);
				curr = curr.next;
			}
		}
		return ret;
	}
}