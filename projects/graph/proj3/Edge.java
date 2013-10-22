/**
 * The Edge class is designed to represent an edge
 * Contains references to two vertices of type Object (start , end),
 * and an int weight,
 * This Edge class is different than the Edge class in graph package
 * because it is used by Kruskal.java and that class cannot use classes
 * from the graph package
 */
public class Edge {
	private Object start;
	private Object end;
	private int weight;
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
}