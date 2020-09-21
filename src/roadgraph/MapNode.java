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
		//���Ѵ�� ����
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
	
	
	//���۳��� �� ���(neighbor�� �ϳ�)�� �ָ� �Ÿ��� ����
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
	
	//���� �ϱ� ���� ������
	// @Override
	public int compareTo(MapNode target) {
		// �ڽ��� ���� ������ -1
        // �ڽ��� ���� ������ 0
        // �ڽź��� ���� ũ�� 1
		if(this.distanceToStart < target.distanceToStart) {
			return -1;
		} else if(this.distanceToStart > target.distanceToStart) {
			return 1;
		}
		return 0;
	}
}
