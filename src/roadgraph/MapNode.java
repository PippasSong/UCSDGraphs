package roadgraph;

import java.util.*;
import geography.GeographicPoint;

public class MapNode {
	GeographicPoint location;
	ArrayList<MapEdge> edge;
	List<MapNode> neighbors;
	
	public MapNode(GeographicPoint location, ArrayList<MapEdge> edge) {
		this.location = location;
		this.edge = edge;
		neighbors = new LinkedList<MapNode>();
	}
	
	public int getNumEdge() {
		return edge.size();
	}
	
	public ArrayList<MapEdge> getEdge(){
		return edge;
	}
	
	public void addNeighbor(MapNode neighbor) 
	{
		neighbors.add(neighbor);
	}
	
	public GeographicPoint getLocation() {
		return location;
	}
	
	/**
	 * @return the neighbors
	 */
	public List<MapNode> getNeighbors() {
		return neighbors;
	}
}
