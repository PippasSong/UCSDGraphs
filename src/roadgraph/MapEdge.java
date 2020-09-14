package roadgraph;


import geography.GeographicPoint;

public class MapEdge {
	GeographicPoint start;
	GeographicPoint end;
	String startName;
	String roadType;
	double distance;
	
	public MapEdge(GeographicPoint start, GeographicPoint end, String startName, String roadType, double distance) {
		this.start = start;
		this.end = end;
		this.startName = startName;
		this.roadType = roadType;
		this.distance = distance;
		
	}
}
