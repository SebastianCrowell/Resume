package DiGraph_A5;
import java.util.*;

public class DiGraph implements DiGraphInterface {

  // in here go all your data and methods for the graph
	
  //only need hash map of nodes and edges, gotta do cleanings
  //track out edge in node as well
  HashMap<String, Node> nodenameMap = new HashMap<>(); //node name to Node
  HashMap<Long, Node> nodeidMap = new HashMap<>(); //node id to Node
  HashMap<Long, Edge> edgeidMap = new HashMap<>(); //edge id to Edge
 
  int numN;
  int numE;
  
  //add node
  //put in q
  //take out of q
  //check edges
  //put in visited list
  //add edges value

  public DiGraph ( ) { // default constructor
    // explicitly include this
    // we need to have the default constructor
    // if you then write others, this one will still be there
  }

  // rest of your code to implement the various operations
  
@Override
public boolean addNode(long idNum, String label) {
	// TODO Auto-generated method stub
	if(idNum < 0) {
		return false;
	}
	if(nodenameMap.containsKey(label) == true || nodeidMap.containsKey(idNum) == true || label == null) {
		return false;
	}
	//make array to store both
	numN++;
	Node anode = new Node(label, idNum);
	nodenameMap.put(label, anode);
	nodeidMap.put(idNum, anode);
	//call to a helper for the removal from pq
	//then check edges and then add to tl
	return true;
}

@Override
public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
	// TODO Auto-generated method stub
	if(idNum < 0) {
		return false;
	}
	if(edgeidMap.containsKey(idNum)) {
		return false;
	}
	if(!nodenameMap.containsKey(sLabel) || !nodenameMap.containsKey(dLabel)) {
		return false;
	}
	if (nodenameMap.get(sLabel).outedges.containsKey(dLabel)) {
		return false;
	}
	Edge aedge = new Edge(weight, sLabel, dLabel, idNum, eLabel);
	edgeidMap.put(idNum, aedge);
	//outedges of s
	//inedges of d
	Node s = nodenameMap.get(sLabel);
	Node d = nodenameMap.get(dLabel);
	s.outEdges(dLabel, aedge);
	d.inEdges(sLabel, aedge);
	numE++;
	return true;
}

@Override
public boolean delNode(String label) {
	// TODO Auto-generated method stub
	if(nodenameMap.containsKey(label) == true) {
	   Node temp = nodenameMap.get(label);
	   for(Edge forEachInEdge : temp.inedges.values()) {
		   Node s = nodenameMap.get(forEachInEdge.sLabel);
		   s.outedges.remove(temp.name);
		   edgeidMap.remove(forEachInEdge.id);
		   numE--;
	   }	
	   for(Edge forEachOutEdge : temp.outedges.values()) {
		   Node d = nodenameMap.get(forEachOutEdge.dLabel);
		   d.outedges.remove(temp.name);
		   edgeidMap.remove(forEachOutEdge.id);
		   numE--;
	   }
	   temp.outedges.clear();
	   temp.inedges.clear();
	   nodenameMap.remove(label);
	   nodeidMap.remove(temp.id);
	   numN--;
	   return true;
	} else {
	   return false;
	}
}

@Override
public boolean delEdge(String sLabel, String dLabel) {
	// TODO Auto-generated method stub
	Node s = nodenameMap.get(sLabel);
	Node d = nodenameMap.get(dLabel);
	if (s == null || d == null ) {
		return false;
	}
	if (s.outedges.get(dLabel) == null) {
		return false;
	}
	
	if(nodenameMap.containsKey(sLabel) && nodenameMap.containsKey(dLabel)) {
		Edge e = s.outedges.get(dLabel);
		edgeidMap.remove(e.id);
		s.outedges.remove(dLabel);
		d.inedges.remove(sLabel);
		numE--;
		return true;
	} else {
		return false;
	}
}

@Override
public long numNodes() {
	// TODO Auto-generated method stub
	//array size
	return nodenameMap.size();
}

@Override
public long numEdges() {
	// TODO Auto-generated method stub
	return edgeidMap.size();
}

@Override
public ShortestPathInfo[] shortestPath(String label) {
	//PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
	Node start = nodenameMap.get(label);
	MinBinHeap q = new MinBinHeap();
	start.distance = 0;
	EntryPair ep = new EntryPair (start, start.distance);
	q.insert(ep);
	while (q.size() != 0) {
		EntryPair p = q.getMin();
		q.delMin();
		Node n = p.value;
		if (n.visited == false) {
			for (Edge e : n.outedges.values()) {
				Node dest = nodenameMap.get(e.dLabel);
				if (n.distance + e.weight < dest.distance) {
					dest.distance = n.distance + e.weight;
					EntryPair d = new EntryPair(dest,dest.distance);
					q.insert(d);
				}
			}
			n.visited = true;
		}
	}
	int i = 0;
	ShortestPathInfo[] shortest = new ShortestPathInfo[nodenameMap.size()];
	for (Node n : nodenameMap.values()) {
		if (n.visited == false) {
			shortest[i] = new ShortestPathInfo(n.name, -1);
			i++;
		} else {
			shortest[i] = new ShortestPathInfo(n.name, n.distance);
			i++;
			n.distance = Long.MAX_VALUE;
			n.visited = false;
		}
	}
	return shortest;
}

}


