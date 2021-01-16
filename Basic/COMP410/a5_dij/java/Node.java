package DiGraph_A5;

import java.util.HashMap;

public class Node  {
	
	String name;
	long id;
	
	HashMap <String, Edge> outedges = new HashMap<>(); //destnode, edge
	HashMap <String, Edge> inedges = new HashMap<>(); //startnode, edge
	
	boolean visited = false;
	long distance = Long.MAX_VALUE;
	
	public Node (String name, long id) {
		this.name = name;
		this.id = id;
	}

	public void outEdges(String dLabel, Edge edge) {
		outedges.put(dLabel, edge);
	}
	
	public void inEdges(String sLabel, Edge edge) {
		inedges.put(sLabel, edge);
	}

}
