/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph  {
	//TODO: Add your member variables here in WEEK 3
	HashMap<GeographicPoint, MapNode> graph;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		this.graph = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return graph.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return graph.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		int number = 0;
		
		for(GeographicPoint temp : graph.keySet()) {
			MapNode mapTemp = graph.get(temp);
			number += mapTemp.getNumEdge();
		}
		
		return number;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if(!graph.containsKey(location)||location!=null) {
			graph.put(location, new MapNode(location, new ArrayList<MapEdge>()));
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if(from!=null && to!=null && roadName!=null && roadType!=null && length>=0 && graph.containsKey(from) && graph.containsKey(to)) {
			MapEdge temp= new MapEdge(from, to, roadName, roadType, length);
			//add edge
			graph.get(from).getEdge().add(temp);
			//add neighbor(to) to node(from)
			graph.get(from).getNeighbors().add(graph.get(to));
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	//작성할 필요 없다
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it. 시각화 위한 매개변수
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		MapNode startPoint = graph.get(start);
		MapNode goalPoint = graph.get(goal);
		
		if(start==null||goal==null) {
			return null;
		}
		
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		toExplore.add(startPoint);
		boolean found = false;
		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			if (curr == goalPoint) {
				found = true;
				break;
			}
			List<MapNode> neighbors = curr.getNeighbors();
			ListIterator<MapNode> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				MapNode next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.add(next);
				}
			}
		}
		//경로 없는 경우
		if (!found) {
			System.out.println("No path exists");
			return null;
		}
		// reconstruct the path
		return parentPath(startPoint, goalPoint, parentMap);

	}
	
	//parentmap을 리턴하는 핼퍼 메소드
	public LinkedList<GeographicPoint> parentPath(MapNode startPoint, MapNode goalPoint, HashMap<MapNode, MapNode> parentMap){
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode curr = goalPoint;
		while (curr != startPoint) {
			path.addFirst(curr.getLocation());
			curr = parentMap.get(curr);
		}
		path.addFirst(startPoint.getLocation());
		return path;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		MapNode startPoint = graph.get(start);
		MapNode goalPoint = graph.get(goal);
		
		//시작점이나 끝 점이 null인 경우
		if(start==null||goal==null) {
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		//방문한 노드 수
		int numNode = 0;
		//priority queue
		PriorityQueue<MapNode> toExplore = new PriorityQueue<>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		boolean found = false;
		
		
		//시작지점과, 거리 0을 큐에 넣는다
		startPoint.setDistance(0);
		toExplore.offer(startPoint);
		
		//큐가 비지 않으면 계속 실행
		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			numNode++;
			//처음 노드가 아닌 경우 처음 노드와 현재 노드 사이의 edge값 반환
			if(!visited.contains(curr)) {
				visited.add(curr);
				if(curr == goalPoint) {
					found = true;
					break;
				}
				List<MapNode> neighbors = curr.getNeighbors();
				System.out.println(curr.getLocation());
				for(MapNode n : neighbors) {
					//n이 visited에 있지 않은 경우
					if(!visited.contains(n)) {
						double newDis =  curr.betweenDis(curr, n);
						if(newDis + curr.distanceToStart < n.distanceToStart) {
							n.setDistance(curr.distanceToStart + newDis);
							parentMap.put(n, curr);
							toExplore.offer(n);
							nodeSearched.accept(curr.getLocation());
						}
					}
					
				}
	
				
			}
		}
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (!found) {
			System.out.println("No path exists");
			return null;
		}
		System.out.println("Nodes visited to search : "+numNode);
		return parentPath(startPoint, goalPoint, parentMap);
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		MapNode startPoint = graph.get(start);
		MapNode goalPoint = graph.get(goal);
		
		//시작점이나 끝 점이 null인 경우
		if(start==null||goal==null) {
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		//방문한 노드 수
		int numNode = 0;
		//priority queue
		PriorityQueue<MapNode> toExplore = new PriorityQueue<>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		boolean found = false;
		
		
		//시작지점과, 거리 0을 큐에 넣는다
		startPoint.setDistance(0);
		toExplore.offer(startPoint);
		
		//큐가 비지 않으면 계속 실행
		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			numNode++;
			//처음 노드가 아닌 경우 처음 노드와 현재 노드 사이의 edge값 반환
			if(!visited.contains(curr)) {
				visited.add(curr);
				if(curr == goalPoint) {
					found = true;
					break;
				}
				List<MapNode> neighbors = curr.getNeighbors();
				System.out.println(curr.getLocation());
				for(MapNode n : neighbors) {
					//n이 visited에 있지 않은 경우
					if(!visited.contains(n)) {
						//dijkstra에서 n과 목표점 까지의 거리를 추가
						double newDis =  curr.betweenDis(curr, n);
						if(newDis + curr.distanceToStart < n.distanceToStart) {
							n.setDistance(curr.distanceToStart + newDis + n.getLocation().distance(goal));
							parentMap.put(n, curr);
							toExplore.offer(n);
							nodeSearched.accept(curr.getLocation());
						}
					}
					
				}
	
				
			}
		}
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (!found) {
			System.out.println("No path exists");
			return null;
		}
		System.out.println("Nodes visited to search : "+numNode);
		return parentPath(startPoint, goalPoint, parentMap);
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		
		
	}
	
}
