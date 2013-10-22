package graph;
import list.*;
public class Edge {
	private Object start;
	private Object end;
	private int weight;
	protected DListNode itsNode1;
	protected DList itsList1;
	protected DListNode itsNode2;
	protected DList itsList2;
	/** 
	 * This Edge constructor constructs an Edge Object with origin vertex
	 * "start", destination vertex "end", and weight "weight"
	 * Start and End are symmetrical (when graph is undirected)
	 * @param start is the origin vertex
	 * @param end is the destination vertex
	 * @param weight is the weight of the vertex
	 */
	public Edge(Object start, Object end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	/**
	 * getStart() returns this this edge's origin vertex
	 * @return an Object that represents this edge's origin vertex
	 */
	public Object getStart() {
		return start;
	}
	/**
	 * getEnd() returns this edge's destination vertex
	 * @return an Object that represents this edge's destination vertex
	 */
	public Object getEnd() {
		return end;
	}
	/** 
	 * getWeight() returns this edge's weight
	 * @return an int representing this edge's weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * setWeight takes in an int newWeight and changes this edge's weight to newWeight
	 * @param newWeight is the new weight (int)
	 */
	public void setWeight(int newWeight){
		weight = newWeight;
	}
}