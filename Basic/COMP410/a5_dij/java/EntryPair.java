package DiGraph_A5;

public class EntryPair {
  public Node value;
  public long priority;

  public EntryPair(Node aValue, long aPriority) {
    value = aValue;
    priority = aPriority;
  }

  public Node getValue() { return value; }
  public long getPriority() { return priority; }
}