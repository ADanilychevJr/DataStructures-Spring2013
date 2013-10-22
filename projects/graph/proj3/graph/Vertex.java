package graph;
<<<<<<< HEAD

import list.*;

=======
import list.*;
/**
 * The Vertex class is designed to represent a vertex
 * Contains references to the DList and DListNode that contain this vertex
 * Also contains reference to the Object that this vertex represents
 */
>>>>>>> Final
public class Vertex {
	DListNode vertexNode;
	DList adjList;
	Object vertex;
<<<<<<< HEAD
	
	public Vertex(Object v) {
		vertex = v;
	}
	
	public void setNode(DListNode vNode) {
		vertexNode = vNode;
	}
	
	public DListNode getNode() {
		return vertexNode;
	}
	
	public void setList(DList aList) {
		adjList = aList;
	}
	
	public DList getList() {
		return adjList;
	}
	
	public String toString() {
		return vertex.toString();
	}
}
=======
	/**
	 * Vertex constructor, takes in an Object v and assigns it to a field in
	 * this Vertex
	 * 
	 */
	public Vertex(Object v) {
		vertex = v;
	}

	/**
	 * setNode(DListNode vNode) makes this vertex reference the DListNode it 
	 * is contained by
	 * @param vNode is the DListNode that contains this vertex
	 */
	public void setNode(DListNode vNode) {
		vertexNode = vNode;
	}
	/**
	 * getNode returns the DListNode that contains this vertex
	 * @return the DListNode that contains this vertex
	 */
	public DListNode getNode() {
		return vertexNode;
	}

	/**
	 * setList makes this vertex reference the DList that contains it
	 * @param aList is the DList that contains this vertex
	 */
	public void setList(DList aList) {
		adjList = aList;
	}

	/**
	 * getList returns the DList that contains this vertex
	 * @return the DList that contains this vertex
	 */
	public DList getList() {
		return adjList;
	}

	/**
	 * toString() returns the string representation of the vertex (the Object it represents)
	 */
	public String toString() {
		return vertex.toString();
	}
}
>>>>>>> Final
