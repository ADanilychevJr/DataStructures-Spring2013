/* WUGraph.java */

package graph;

import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

	private DList vList;
	private HashTableChained vTable;
	private HashTableChained eTable;
	private int vertexCount; 
	private int edgeCount;

	/**
	 * WUGraph() constructs a graph having no vertices or edges.
	 *
	 * Running time:  O(1).
	 */
	public WUGraph(){
		vList = new DList();
		vTable = new HashTableChained();
		eTable = new HashTableChained();
		vertexCount = 0;
		edgeCount = 0;
	}

	/**
	 * vertexCount() returns the number of vertices in the graph.
	 *
	 * Running time:  O(1).
	 */
	public int vertexCount(){
		return vertexCount;
	}

	/**
	 * edgeCount() returns the number of edges in the graph.
	 *
	 * Running time:  O(1).
	 */
	public int edgeCount(){
		return edgeCount;
	}

	/**
	 * getVertices() returns an array containing all the objects that serve
	 * as vertices of the graph.  The array's length is exactly equal to the
	 * number of vertices.  If the graph has no vertices, the array has length
	 * zero.
	 *
	 * (NOTE:  Do not return any internal data structure you use to represent
	 * vertices!  Return only the same objects that were provided by the
	 * calling application in calls to addVertex().)
	 *
	 * Running time:  O(|V|).
	 */
	public Object[] getVertices(){
		Object[] vertices = new Object[vertexCount()];
		DListNode current = vList.front();
		for(int i = 0; i<vertexCount(); i++){
			vertices[i] = ((Vertex) current.item).vertex;
			current = vList.next(current);
		}
		return vertices;
	}

	/**
	 * addVertex() adds a vertex (with no incident edges) to the graph.  The
	 * vertex's "name" is the object provided as the parameter "vertex".
	 * If this object is already a vertex of the graph, the graph is unchanged.
	 *
	 * Running time:  O(1).
	 */
	public void addVertex(Object vertex){
		if (!isVertex(vertex)){ //check to make sure its not already in the table
			Vertex v = new Vertex(vertex);
			vList.insertFront(v);
			vTable.insert(vertex, v); //vertex.hashCode();
			v.adjList = new DList();
			v.vertexNode = vList.front();
			vertexCount++;
		}


	}
	/**
	 * removeVertex() removes a vertex from the graph.  All edges incident on the
	 * deleted vertex are removed as well.  If the parameter "vertex" does not
	 * represent a vertex of the graph, the graph is unchanged.
	 *
	 * Running time:  O(d), where d is the degree of "vertex".
	 */
	public void removeVertex(Object vertex){
		if(!isVertex(vertex)){
			return;
		}
		Vertex v = (Vertex) vTable.find(vertex).value();
		DList adjList = v.adjList;
		if(adjList.length()>0){
			DListNode current = adjList.front();
			Edge tempEdge;
			while(current != adjList.sentinel()){
				tempEdge = (Edge) current.item;
				removeEdge(tempEdge.getEnd(),tempEdge.getStart());
				current = adjList.next(current);
			}
		}
		vList.remove(v.vertexNode);
		vTable.remove(vertex);
		vertexCount--;
	}

	/**
	 * isVertex() returns true if the parameter "vertex" represents a vertex of
	 * the graph.
	 *
	 * Running time:  O(1).
	 */
	public boolean isVertex(Object vertex){
		return vTable.find(vertex)!=null;
	}

	/**
	 * degree() returns the degree of a vertex.  Self-edges add only one to the
	 * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
	 * of the graph, zero is returned.
	 *
	 * Running time:  O(1).
	 */
	public int degree(Object vertex){
		if(!isVertex(vertex)){
			return 0;
		}
		Vertex v = (Vertex) vTable.find(vertex).value();
		return v.adjList.length();
	}

	/**
	 * getNeighbors() returns a new Neighbors object referencing two arrays.  The
	 * Neighbors.neighborList array contains each object that is connected to the
	 * input object by an edge.  The Neighbors.weightList array contains the
	 * weights of the corresponding edges.  The length of both arrays is equal to
	 * the number of edges incident on the input vertex.  If the vertex has
	 * degree zero, or if the parameter "vertex" does not represent a vertex of
	 * the graph, null is returned (instead of a Neighbors object).
	 *
	 * The returned Neighbors object, and the two arrays, are both newly created.
	 * No previously existing Neighbors object or array is changed.
	 *
	 * (NOTE:  In the neighborList array, do not return any internal data
	 * structure you use to represent vertices!  Return only the same objects
	 * that were provided by the calling application in calls to addVertex().)
	 *
	 * Running time:  O(d), where d is the degree of "vertex".
	 */
	public Neighbors getNeighbors(Object vertex){
		if (!isVertex(vertex)){
			return null;
		}
		Vertex v = (Vertex) vTable.find(vertex).value();
		DList adjList = v.adjList;
		DListNode current = adjList.front();
		//no neighbors (no edges)
		if(current==null){
			return null;
		}

		Object[] neighborList = new Object[adjList.length()];
		int[] weightList = new int[adjList.length()];

		int count = 0;
		Object tempVertex;
		Edge tempEdge;
		int tempWeight;
		while(current != null){
			tempEdge = (Edge) current.item;
			if(vertex == tempEdge.getStart()){
				tempVertex = tempEdge.getEnd();
			}else{
				tempVertex = tempEdge.getStart();
			}
			tempWeight = tempEdge.getWeight();
			neighborList[count] = tempVertex;
			weightList[count] = tempWeight;
			count++;
			current = adjList.next(current);
		}

		Neighbors n = new Neighbors();
		n.neighborList = neighborList;
		n.weightList = weightList;

		return n;

	}

	/**
	 * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
	 * u and v does not represent a vertex of the graph, the graph is unchanged.
	 * The edge is assigned a weight of "weight".  If the edge is already
	 * contained in the graph, the weight is updated to reflect the new value.
	 * Self-edges (where u == v) are allowed.
	 *
	 * Running time:  O(1).
	 */
	public void addEdge(Object u, Object v, int weight){
		if (this.isVertex(u) && this.isVertex(v)){
			VertexPair pair = new VertexPair(u,v);
			Edge edge = new Edge(u,v,weight);
<<<<<<< HEAD
			if (eTable.find(pair) == null ){//edge doesnt exist, but both verticies are already in the graph
				eTable.insert(pair,edge);//key is edge, value is weight
=======
			if (eTable.find(pair) == null ){
				eTable.insert(pair,edge);
>>>>>>> Final
				((Vertex) vTable.find(u).value()).adjList.insertFront(edge);
				edge.itsNode1 = ((Vertex) vTable.find(u).value()).adjList.front();
				edge.itsList1 = ((Vertex) vTable.find(u).value()).adjList;
				if (u != v) {
					((Vertex) vTable.find(v).value()).adjList.insertFront(edge);
					edge.itsNode2 = ((Vertex) vTable.find(v).value()).adjList.front();
					edge.itsList2 = ((Vertex) vTable.find(v).value()).adjList;
				}

				edgeCount++;
			} else {
				((Edge) eTable.find(pair).value()).setWeight(weight);
			}

		}
	}

	/**
	 * removeEdge() removes an edge (u, v) from the graph.  If either of the
	 * parameters u and v does not represent a vertex of the graph, the graph
	 * is unchanged.  If (u, v) is not an edge of the graph, the graph is
	 * unchanged.
	 *
	 * Running time:  O(1).
	 */
	public void removeEdge(Object u, Object v){
		if(isEdge(u,v)){
			Edge e = (Edge) eTable.find(new VertexPair(u,v)).value();
			eTable.remove(new VertexPair(u,v));
			e.itsList1.remove(e.itsNode1);
			if(e.itsNode2 != null){
				e.itsList2.remove(e.itsNode2);
			}
			edgeCount--;
		}

	}

	/**
	 * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
	 * if (u, v) is not an edge (including the case where either of the
	 * parameters u and v does not represent a vertex of the graph).
	 *
	 * Running time:  O(1).
	 */
	public boolean isEdge(Object u, Object v){
		VertexPair edge = new VertexPair(u,v);
		return eTable.find(edge)!=null;
	}

	/**
	 * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
	 * an edge (including the case where either of the parameters u and v does
	 * not represent a vertex of the graph).
	 *
	 * (NOTE:  A well-behaved application should try to avoid calling this
	 * method for an edge that is not in the graph, and should certainly not
	 * treat the result as if it actually represents an edge with weight zero.
	 * However, some sort of default response is necessary for missing edges,
	 * so we return zero.  An exception would be more appropriate, but
	 * also more annoying.)
	 *
	 * Running time:  O(1).
	 */
	public int weight(Object u, Object v){
		if(!isEdge(u,v)){
			return 0;
		}
		return ((Edge) eTable.find(new VertexPair(u,v)).value()).getWeight();

	}
}
