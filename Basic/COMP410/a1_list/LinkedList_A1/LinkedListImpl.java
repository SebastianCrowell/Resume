/**
 * COMP 410
 *See inline comment descriptions for methods not described in interface.
 *
*/
package LinkedList_A1;

public class LinkedListImpl implements LIST_Interface {
  Node sentinel; //this will be the entry point to your linked list (the head)
  
  public LinkedListImpl(){//this constructor is needed for testing purposes. Please don't modify!
    sentinel=new Node(0); //Note that the root's data is not a true part of your data set!
  }
  
  //implement all methods in interface, and include the getRoot method we made for testing purposes. Feel free to implement private helper methods!
  
  public Node getRoot(){ //leave this method as is, used by the grader to grab your linkedList easily.
    return sentinel;
  }

  private Node head;
  private Node tail;
  
public void DoubleLinkedList() {
	this.head = null;
	this.tail = null;
}
  
@Override
public boolean insert(double elt, int index) {
	// TODO Auto-generated method stub
	Node newNode = new Node(elt);
	
	//Catch for placing value outside of list
	if(index > size()) {
		return false;
	}
	
	//Checking to see if adding at beginning
	if(isEmpty()) {
		tail = newNode;
	} else {
		head.prev = newNode;
	}
	
	newNode.next = head;
	head = newNode;
//	sentinel.prev = tail;
//	sentinel.next = head;
	return true;
}


@Override
public boolean remove(int index) {
	// TODO Auto-generated method stub
	if(index == 0 || index > size()) {
		return false;
	}
	if(isEmpty()) {
		sentinel.next = null;
		sentinel.prev = null;
		return false;
	}
	Node temp = head;
	if(head == tail) {
		tail = null;
		sentinel.prev = null;
	} else {
		head.next.prev = null;
		sentinel.next = null;
	}
	head = head.next;
	temp.next = null;
	return true;
}

@Override
public double get(int index) {
	// TODO Auto-generated method stub
	if(index == 0 || index >= size()) {
		return Double.NaN;
	} else {
	Node current = head;
	for(int i = 0; i <= index; i++) {
		current = current.next;
		}
	return current.getData();
	}
}
	
@Override
public int size() {
	// TODO Auto-generated method stub
	int x = 0;
	Node current = sentinel.next;
	while(current != null) {
		x++;
		current = current.next;
	}
	return x;
}

@Override
public boolean isEmpty() {
	// TODO Auto-generated method stub
	if(size() == 0) {
		return true;
	}
	if(sentinel == null) {
		return true;
	} else {
	return false;
	}
}

@Override
public void clear() {
	// TODO Auto-generated method stub
	if(sentinel == null) {
		return;
	}
	head = null;
	tail = null;
	sentinel.next = null;
	sentinel.prev = null;
}
}