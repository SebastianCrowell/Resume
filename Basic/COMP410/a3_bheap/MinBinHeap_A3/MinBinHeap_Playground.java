package MinBinHeap_A3;

public class MinBinHeap_Playground {
  public static void main(String[] args){   
    //Add more tests as methods and call them here!!
    TestBuild();
    TestInsert();
    TestGetmin();
  }
  
  public static void TestBuild(){ 
    // constructs a new minbinheap, constructs an array of EntryPair, 
    // passes it into build function. Then print collection and heap.
    MinBinHeap mbh= new MinBinHeap();
    EntryPair[] collection= new EntryPair[8];
    collection[0]=new EntryPair("i",3);
    collection[1]=new EntryPair("b",5);
    collection[2]=new EntryPair("c",1);
    collection[3]=new EntryPair("d",0);
    collection[4]=new EntryPair("e",46);
    collection[5]=new EntryPair("f",5);
    collection[6]=new EntryPair("g",6);
    collection[7]=new EntryPair("h",17);
    mbh.build(collection);
    printHeapCollection(collection);
    printHeap(mbh.getHeap(), mbh.size());
  }
  
  public static void TestInsert(){ 
	MinBinHeap mbhtwo= new MinBinHeap();
	mbhtwo.insert(new EntryPair("a",3));
	mbhtwo.insert(new EntryPair("b",5));
	mbhtwo.insert(new EntryPair("c",6));
	mbhtwo.insert(new EntryPair("first",1));
	printHeap(mbhtwo.getHeap(), mbhtwo.size());
  }
  
  public static void TestGetmin() {
	  MinBinHeap mbhthree = new MinBinHeap();
	  mbhthree.insert(new EntryPair("f", 4));
	  mbhthree.insert(new EntryPair("g", 1));
	  mbhthree.insert(new EntryPair("d", 7));
	  mbhthree.insert(new EntryPair("b", 5));
	  mbhthree.insert(new EntryPair("z", 13));
	  mbhthree.insert(new EntryPair("t", 2));
	  mbhthree.insert(new EntryPair("y", 12));
	  printHeap(mbhthree.getHeap(), mbhthree.size());
	  mbhthree.getMin();
	  mbhthree.delMin();
	  printHeap(mbhthree.getHeap(), mbhthree.size());
	  mbhthree.insert(new EntryPair("n", 9));
	  mbhthree.insert(new EntryPair("m", 3));
	  mbhthree.insert(new EntryPair("w", 6));
	  mbhthree.insert(new EntryPair("q", 11));
	  mbhthree.insert(new EntryPair("u", 10));
	  printHeap(mbhthree.getHeap(), mbhthree.size());
	  mbhthree.delMin();
	  printHeap(mbhthree.getHeap(), mbhthree.size());
	  mbhthree.insert(new EntryPair("na", 14));
	  mbhthree.insert(new EntryPair("ma", 99));
	  mbhthree.insert(new EntryPair("wa", 18));
	  mbhthree.insert(new EntryPair("qa", 23));
	  mbhthree.insert(new EntryPair("ua", 50));
	  mbhthree.insert(new EntryPair("nd", 43));
	  mbhthree.insert(new EntryPair("md", 34));
	  mbhthree.insert(new EntryPair("wd", 16));
	  mbhthree.insert(new EntryPair("qd", 20));
	  mbhthree.insert(new EntryPair("ud", 87));
	  mbhthree.insert(new EntryPair("nf", 40));
	  mbhthree.insert(new EntryPair("mf", 30));
	  mbhthree.insert(new EntryPair("wf", 17));
	  mbhthree.insert(new EntryPair("qf", 94));
	  mbhthree.insert(new EntryPair("uf", 25));
	  mbhthree.insert(new EntryPair("ng", 28));
	  mbhthree.insert(new EntryPair("mg", 41));
	  mbhthree.insert(new EntryPair("wg", 27));
	  mbhthree.insert(new EntryPair("qg", 49));
	  mbhthree.insert(new EntryPair("ug", 51));
	  mbhthree.insert(new EntryPair("nh", 59));
	  mbhthree.insert(new EntryPair("mh", 60));
	  mbhthree.insert(new EntryPair("wh", 78));
	  mbhthree.insert(new EntryPair("qh", 88));
	  mbhthree.insert(new EntryPair("uh", 98));
	  mbhthree.insert(new EntryPair("nj", 91));
	  mbhthree.insert(new EntryPair("mj", 93));
	  mbhthree.insert(new EntryPair("wj", 77));
	  mbhthree.insert(new EntryPair("qj", 15));
	  mbhthree.insert(new EntryPair("uj", 31));
	  mbhthree.insert(new EntryPair("ni", 33));
	  mbhthree.insert(new EntryPair("mi", 41));
	  mbhthree.insert(new EntryPair("wi", 52));
	  mbhthree.insert(new EntryPair("qi", 35));
	  mbhthree.insert(new EntryPair("ui", 63));
	  mbhthree.insert(new EntryPair("nu", 97));
	  mbhthree.insert(new EntryPair("mu", 68));
	  mbhthree.insert(new EntryPair("wu", 36));
	  mbhthree.insert(new EntryPair("qu", 37));
	  mbhthree.insert(new EntryPair("uu", 85));
	  printHeap(mbhthree.getHeap(), mbhthree.size());
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  mbhthree.delMin();
	  printHeap(mbhthree.getHeap(), mbhthree.size());
  }
  
  public static void printHeapCollection(EntryPair[] e) { 
    //this will print the entirety of an array of entry pairs you will pass 
    //to your build function.
    System.out.println("Printing Collection to pass in to build function:");
    for(int i=0;i < e.length;i++){
      System.out.print("("+e[i].value+","+e[i].priority+")\t");
    }
    System.out.print("\n");
  }
  
  public static void printHeap(EntryPair[] e,int len) { 
    //pass in mbh.getHeap(),mbh.size()... this method skips over unused 0th index....
    System.out.println("Printing Heap");
    for(int i=1;i < len+1;i++){
      System.out.print("("+e[i].value+","+e[i].priority+")\t");
    }
    System.out.print("\n");
    //added
    System.out.print(len + "\n");
  }
}