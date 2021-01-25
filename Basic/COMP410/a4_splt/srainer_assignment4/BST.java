package srainer_assignment4;

public class BST implements BST_Interface {
	public BST_Node root;
	int size;
	public BST_Node current;
	public BST_Node parent;
	public BST_Node grandparent;
	  
	public BST(){
		size = 0;
		root = null;
		current = null;
		parent = null;
		grandparent = null;
	}
	public BST_Node getParent(){
		return parent;
	}
	
	public BST_Node getGrandparent(){
		return grandparent;
	}
	  
	@Override
	//used for testing, please leave as is
	public BST_Node getRoot(){
		return root;
	}

	public boolean splayTree(String s) {
		BST_Node temp;
		BST_Node temp1;
		temp = root;
		
		if(size > 3) {
		while(temp != grandparent) {
			 if(temp.left == null) {
				 temp.right = temp;
			 } else {
				 temp.left = temp;
			 }
			 grandparent = temp;
		} 
		} else if(size > 1) {
			parent = root;
			if(grandparent == null) {
				if(parent.left == null) {
					//zig left turn
					temp = root;
//					current = root.right;
					root = current;
//					root.left = temp;
				} else if (parent.right == null) {
					//zig right turn
					temp = root;
//					current = root.left;
					root = current;
//					root.right = temp;
				} else if(parent.left == current) {
					//zig right turn
					temp = root;
					temp1 = root.right;
					root = current;
					root.right = temp;
					root.right.right = temp1;
				} else if(parent.right == current) {
					//zig turn left
					temp = root;
					temp1 = root.left;
					root = current;
					root.left = temp;
					root.left.left = temp1;
				}
			} else {
				if(grandparent.left == parent) {
					if(parent.left == current) {
						//zig-zig
					} else {
						//zig-zag
					}
				} else {
					if(parent.right == current) {
						//zig-zig
					} else {
						//zig-zag
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean insert(String s) { //It was me
		// TODO Auto-generated method stub
		if (empty()) {
			root = new BST_Node(s);
			size += 1;
			return true;
		}
		if (contains(s)) {
			return false;
		} else {
//			splayTree(s);
			size += 1;
			return root.insertNode(s);
		}
	}

	@Override
	public boolean remove(String s) {  //DIO
		// TODO Auto-generated method stub
		if (!contains(s)) {
			return false;
		} else {
			if (root.data.equals(s) && size == 1) {
				root = null;
				size -= 1;
				return true;
			} else {
				size -= 1;
//				splayTree(s);
				return root.removeNode(s);
			}
		}
	}

	@Override
	public String findMin() {
		// TODO Auto-generated method stub
		if (empty()) {
			return null;
		} else {
			return root.findMin().data;
		}
	}

	@Override
	public String findMax() {
		// TODO Auto-generated method stub
		if (empty()) {
			return null;
		} else {
			return root.findMax().data;
		}
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(String s) {
		// TODO Auto-generated method stub
		if (empty()) {
			return false;
		}
//		splayTree(s);
		return root.containsNode(s);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		if (empty()) {
			return -1;
		} else {
			return root.getHeight();
		}
	}
}
