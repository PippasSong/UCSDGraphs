package roadgraph;

import java.util.*;
import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode> {
	GeographicPoint location;
	ArrayList<MapEdge> edge;
	List<MapNode> neighbors;
	double distanceToStart;
	
	public MapNode(GeographicPoint location, ArrayList<MapEdge> edge) {
		this.location = location;
		this.edge = edge;
		neighbors = new LinkedList<MapNode>();
		//무한대로 설정
		this.distanceToStart = 2.0/0.0;
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
	
	public void setDistance(double distance) {
		this.distanceToStart = distance;
	}
	
	
	//시작노드와 끝 노드(neighbor중 하나)를 주면 거리를 리턴
	public double betweenDis(MapNode startNode, MapNode endNode) {
		Double num = null;
		for(MapEdge e : edge) {
			if(e.end.equals(endNode.getLocation())) {
				num = e.getDistance();
				return num;
			}
		}
		return num;
	}
	
	//정렬 하기 위해 재정의
	// @Override
	public int compareTo(MapNode target) {
		// 자신의 값이 작으면 -1
        // 자신의 값과 같으면 0
        // 자신보다 값이 크면 1
		if(this.distanceToStart < target.distanceToStart) {
			return -1;
		} else if(this.distanceToStart > target.distanceToStart) {
			return 1;
		}
		return 0;
	}
}
