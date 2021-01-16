package DiGraph_A5;

public class Edge {
	
	String sLabel;
	String dLabel;
	long id;
	long weight;
	String name;
	
	public Edge (long weight, String sLabel, String dLabel, long id, String name) {
		this.weight = weight;
		this.sLabel = sLabel;
		this.dLabel = dLabel;
		this.id = id;
		this.name = name;
		//....
	}
}
