import java.io.*;
import java.util.*;

public class MyHashMap<Key, Value>{

	class Node{
		Key k; Value v;
		Node next;
		public Node(Key kp, Value vp){
			k = kp; v = vp;
			next = null;
		}
	}

	int maxn;
	Vector<Node> ar;
	int size;

	public MyHashMap(){
		maxn = 10000;
		ar = new Vector<Node>();
		for(int i=0; i<maxn; i++)
			ar.add(null);
		size = 0;
	}


	private Node getNodeForKey(Key k){
		int index = Math.abs(k.hashCode()) % maxn;
		Node curr = ar.get(index);
		while(curr != null && (!curr.k.equals(k)))
			curr = curr.next;
		return curr;
	}

	public void put(Key k, Value v){
		Node curr = getNodeForKey(k);
		if(curr != null){ //the key was already present
			curr.v = v;
			return;
		}
		int index = Math.abs(k.hashCode()) % maxn;
		curr = new Node(k,v);
		curr.next = ar.get(index);
		ar.setElementAt(curr,index);
		size++;
	}

	public Value get(Key k){
		Node curr = getNodeForKey(k);
		if(curr == null)
			return null;
		return curr.v;
	}

	public boolean containsKey(Key k){
		Node curr = getNodeForKey(k);
		return (curr != null);
	}

	public boolean isEmpty(){
		return size == 0;
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
			ar.setElementAt(curr.next, index);
			size--;
			return;
		}
		Node prev = ar.get(index); curr = prev.next;
		while((curr != null) && (!curr.k.equals(k))){
			prev = curr;
			curr = curr.next;
		}
		if(curr == null)
			return;
		size--;
		prev.next = curr.next; //deleting curr
	}

	public void clear(){
		size = 0; ar.clear();
		for(int i=0; i<maxn; i++)
			ar.add(null);
	}

	public MySet<Key> keySet(){
		MySet<Key> ret = new MySet<Key>();
		for(int i=0; i<maxn; i++){
			Node curr = ar.get(i);
			while(curr!=null){
				ret.add(curr.k);
				curr = curr.next;
			}
		}
		return ret;
	}
}
