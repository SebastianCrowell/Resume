package MinBinHeap_A3;

public class MinBinHeap implements Heap_Interface {
  private EntryPair[] array; //load this array
  private int size;
  private static final int arraySize = 10000; //Everything in the array will initially 
                                              //be null. This is ok! Just build out 
                                              //from array[1]

  public MinBinHeap() {
    this.array = new EntryPair[arraySize];
    array[0] = new EntryPair(null, -100000); //0th will be unused for simplicity 
                                             //of child/parent computations...
                                             //the book/animation page both do this.
  }
    
  //Please do not remove or modify this method! Used to test your entire Heap.
  @Override
  public EntryPair[] getHeap() { 
    return this.array;
  }
  
	@Override
	public void insert(EntryPair entry) {
		// need a left build and a right build
		// Left(i) = 2 * i
		// Right(i) = 2 * i + 1
		// then we need to track the parent node
		// Parent(i) = (i) / 2
		//  node   [2] [5] [6] [7] [13] [15]
		//  pos     1   2   3   4   5    6
		//for(int i = 0; i <= size; i++) {
		//	array[i] = entry;
		//}
		//array[size++] = entry;
		size++;
		array[size] = entry;
		bubbleUp(size);
	}
	
	int getLeftNode(int nodePos) {
		// Left build
		return 2 * nodePos;
	}
	
	int getRightNode(int nodePos) {
		// Right build
		return 2 * nodePos + 1;
	}
	
	int getParentNode(int nodePos) {
		// Parent of node
		if(nodePos == 1) {
			return nodePos;
		} else {
		return nodePos / 2;
		}
	}
	
	private void bubbleUp(int nodePos) {
		// helper for checking and moving back 
		// up to swap, node pos is the actual position 
		// according to the array format
		int parentNode;
	    EntryPair tempNode;
		// need to check this later as is might need to be 0
		if(nodePos > 1) {
			parentNode = getParentNode(nodePos);
			// trying to look at priority instead of just position
			if (array[parentNode].priority > array[nodePos].priority) {
			//if(parentNode > nodePos) {
				tempNode = array[parentNode];
				array[parentNode] = array[nodePos];
				array[nodePos] = tempNode;
				bubbleUp(parentNode);	
			}
		}
		
	}
	
	@Override
	public void delMin() {
		// deletes the first node
		// EntryPair temp = array[1];
		if(size == 0) {
			return;
		} else {
		array[1] = array[size];
		array[size] = null;
		if(size > 0) {
			bubbleDown(1);
		}
		size--;
		}
	}
	
	public void bubbleDown(int nodePos) {
		// replacing where the node is removed to keep heap struct
		int leftNode, rightNode;
	    EntryPair tempNode, temp2Node;
		leftNode = getLeftNode(nodePos);
		rightNode = getRightNode(nodePos);
		tempNode = array[nodePos];
		//array out of bounds (use size)
		if(array[rightNode] == null && array[leftNode] == null) {
			return;
		} else
		if(array[nodePos].priority > array[leftNode].priority || array[nodePos].priority > array[rightNode].priority) {
			if(array[rightNode] == null || array[leftNode] == null) {
				if(array[rightNode] == null) {
					temp2Node = array[nodePos];
					array[nodePos] = array[leftNode];
					array[leftNode] = temp2Node;
					bubbleDown(leftNode);
				} else if(array[leftNode] == null){
					temp2Node = array[nodePos];
					array[nodePos] = array[rightNode];
					array[rightNode] = temp2Node;
					bubbleDown(rightNode);
				}
			} else
			if(array[rightNode].priority > array[leftNode].priority) {
				array[nodePos] = array[leftNode];
				array[leftNode] = tempNode;
				bubbleDown(leftNode);
			} else {
				array[nodePos] = array[rightNode];
				array[rightNode] = tempNode;
				bubbleDown(rightNode);
			}
		}
	}
	
	@Override
	public EntryPair getMin() {
		// this is the root or first node
		if(size == 0) {
			return null;
		} else {
			return array[1];
		}
	}
	
	@Override
	public int size() {
		// O(log n) for height?
		// changes according to the insert and remove methods
		return size;
	}
	
	@Override
	public void build(EntryPair[] entries) {
		// put everything into the array and heap then order them
		// supposed to call bubble down on parent node
		for(int i = 1; i < entries.length + 1; i++) {
			array[i] = entries[i - 1];
			size++;
			bubbleUp(i);
		}
//		for(int j = ((entries.length) / 2) - 1; j >= 0; j--) {
//			size++;
//			bubbleDown(j);
//		}
	}
}