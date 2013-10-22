/* Kruskal.java */

import graph.*;
import set.*;
import list.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */
public class Kruskal {
	/**
	 * minSpanTree() returns a WUGraph that represents the minimum spanning tree
	 * of the WUGraph g. The original WUGraph g is NOT changed.
	 */
	public static WUGraph minSpanTree(WUGraph g) {
		//create a new Graph
		WUGraph newGraph = new WUGraph();
		//add the vertex of the old graph to the new graph
		for(Object vertex : g.getVertices()){
			newGraph.addVertex(vertex);
		}
		//create a new list of edges
		DList edgeList = new DList();
		//hash table of vertices to check if they have been visited
		//hash table also stores the position to be used as index for the DisjointSets forest used later
		HashTableChained vertices = new HashTableChained();
		//add all the edges to the list
		Edge tempEdge;
		int disjointIndex = 0; //used as the indices for vertices when finding the minSpanTree
		for(Object vertex : newGraph.getVertices()){	
			Neighbors tempNeighbors = g.getNeighbors(vertex);
			Object[] ends = tempNeighbors.neighborList;
			int[] weights = tempNeighbors.weightList;
			for(int i = 0; i < ends.length; i++){
				if(vertices.find(ends[i])==null && vertex!=ends[i]){
					tempEdge = new Edge(vertex,ends[i],weights[i]);
					edgeList.insertFront(tempEdge);
				}
			}
			vertices.insert(vertex, disjointIndex); //adds the vertex to a list of visited vertexes and gives it an index
			disjointIndex++;
		}
<<<<<<< HEAD
		
=======

>>>>>>> Final
		//puts edges into an array and sorts them with mergeSort
		int i = 0;
		Edge[] edgeArray = new Edge[edgeList.length()];
		while (edgeList.front() != null) {
			edgeArray[i] = (Edge) edgeList.front().item;
			edgeList.remove(edgeList.front());
			i++;
		}
		mergeSort(edgeArray);
<<<<<<< HEAD
		
=======

>>>>>>> Final
		//Utilizes DisjointSets to determine the minimum spanning tree through the use of Kruskal's algorithm
		DisjointSets forest = new DisjointSets(g.getVertices().length);
		for (i=0; i<edgeArray.length; i++) {
			int start = (Integer) vertices.find(edgeArray[i].getStart()).value();
			int end = (Integer) vertices.find(edgeArray[i].getEnd()).value();
			if(forest.find(start) != forest.find(end)) {	//checks if the two vertexes are already connected (in the same set)
				forest.union(forest.find(start), forest.find(end));
				newGraph.addEdge(edgeArray[i].getStart(), edgeArray[i].getEnd(), edgeArray[i].getWeight());
			} 
		}
		return newGraph;
	}
	/**
	 * Mergesort algorithm.
	 * @param a an array of int items.
	 **/
	public static void mergeSort(Edge[] a) {
		Edge[] tmpArray = new Edge[a.length];
		mergeSort(a, tmpArray, 0, a.length - 1);
	}
	/**
	 * Internal method that makes recursive calls.
	 * @param a an array of int items.
	 * @param tmpArray an array to place the merged result.
	 * @param left the left-most index of the subarray.
	 * @param right the right-most index of the subarray.
	 **/
	private static void mergeSort(Edge[] a, Edge[] tmpArray, int left, int right) {
		if (left < right) {
			int center = (left + right) / 2;
			mergeSort(a, tmpArray, left, center);
			mergeSort(a, tmpArray, center + 1, right);
			merge(a, tmpArray, left, center + 1, right);
		}
	}
	/**
	 * Internal method that merges two sorted halves of a subarray.
	 * @param a an array of int items.
	 * @param tmpArray an array to place the merged result.
	 * @param leftPos the left-most index of the subarray.
	 * @param rightPos the index of the start of the second half.
	 * @param rightEnd the right-most index of the subarray.
	 **/
	private static void merge(Edge[] a, Edge[] tmpArray, int leftPos, int rightPos,
			int rightEnd) {
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;
		// Main loop
		while (leftPos <= leftEnd && rightPos <= rightEnd) {
			if (a[leftPos].getWeight() < a[rightPos].getWeight()) {
				tmpArray[tmpPos++] = a[leftPos++];
			} else {
				tmpArray[tmpPos++] = a[rightPos++];
			}
		}
		while (leftPos <= leftEnd) {
			tmpArray[tmpPos++] = a[leftPos++];
		}
		while(rightPos <= rightEnd) {
			tmpArray[tmpPos++] = a[rightPos++];
		}
		// Copy TmpArray back
		for (int i = 0; i < numElements; i++, rightEnd--) {
			a[rightEnd] = tmpArray[rightEnd];
		}
	}
}