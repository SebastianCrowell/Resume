package SPLT_A4;

public class SPLT implements SPLT_Interface{
  private BST_Node root;
  private int size;
  
  public SPLT() {
    this.size = 0;
  } 
  
  public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
    return root;
  }

	private BST_Node containsHelper(BST_Node node, String s) {
		if (node == null || s == node.data) {
			return node;
		}
		if ( s.compareTo(node.data) < 0) {
			return containsHelper(node.left, s);
		} 
		return containsHelper(node.right, s);
	}

	private void removeHelper(BST_Node start, String s) {
		BST_Node temp1 = null;
		BST_Node temp2 = null;
		BST_Node temp3 = null;
		
		while (start != null){
			if (start.data == s) {
				temp1 = start;
			}
			if (start.data.compareTo(s) < 0) {
				start = start.right;
			} else {
				start = start.left;
			}
		}

		if (temp1 == null) {
			return;
		}
		
		// Splay
		size -= 1; 
		splay(temp1);
		if (temp1.right != null) {
			temp2 = temp1.right;
			temp2.par = null;
		} else {
			temp2 = null;
		}
		temp3 = temp1;
		temp3.right = null;
		temp1 = null;
		
		// Stitch
		if (temp3.left != null)
		{ 
			temp3.left.par = null;
		}
		root = stitchTree(temp3.left, temp2);
		temp3 = null;
	}

	// Rotate node
	private void leftRotate(BST_Node node) {
		BST_Node check = node.right;
		node.right = check.left;
		if (check.left != null) {
			check.left.par = node;
		}
		check.par = node.par;
		if (node.par == null) {
			this.root = check;
		} else if (node == node.par.left) {
			node.par.left = check;
		} else {
			node.par.right = check;
		}
		check.left = node;
		node.par = check;
	}

	// Rotate at node
	private void rightRotate(BST_Node node) {
		BST_Node check = node.left;
		node.left = check.right;
		if (check.right != null) {
			check.right.par = node;
		}
		check.par = node.par;
		if (node.par == null) {
			this.root = check;
		} else if (node == node.par.right) {
			node.par.right = check;
		} else {
			node.par.left = check;
		}
		check.right = node;
		node.par = check;
	}

	// Splay with node
	private void splay(BST_Node node) {
		while (node.par != null) {
			if (node.par.par == null) {
				if (node == node.par.left) {
					
				// Zig
				rightRotate(node.par);
				} else {
					
				// Zag
				leftRotate(node.par);
				}
			} else if (node == node.par.left && node.par == node.par.par.left) {
				
				// Zig-zig 
				rightRotate(node.par.par);
				rightRotate(node.par);
			} else if (node == node.par.right && node.par == node.par.par.right) {
				
				// Zag-zag rotation
				leftRotate(node.par.par);
				leftRotate(node.par);
			} else if (node == node.par.right && node.par == node.par.par.left) {
				
				// Zig-zag rotation
				leftRotate(node.par);
				rightRotate(node.par);
			} else {
				
				// Zag-zig rotation
				rightRotate(node.par);
				leftRotate(node.par);
			}
		}
	}

	// If we have two separate trees due to an action, combine them for one
	private BST_Node stitchTree(BST_Node left, BST_Node temp){
		if (left == null) {
			return temp;
		}
		if (temp == null) {
			return left;
		}
		BST_Node node = findMax(left);
		splay(node);
		node.right = temp;
		temp.par = node;
		return node;
	}

	//Does string s exist
	public boolean contains(String s) {
		BST_Node node = containsHelper(root, s);
		if (node != null) {
			splay(node);
			return true;
		} else {
			return false;
		}
	}

	// Find min node (changed these from bools because I was getting confused)
	public BST_Node findMin(BST_Node node) {
		while (node.left != null) {
			node = node.left;
		}
		splay(node);
		return node;
	}

	// Find max node
	public BST_Node findMax(BST_Node node) {
		while (node.right != null) {
			node = node.right;
		}
		splay(node);
		return node;
	}
	
	//Copied from og (not sure why but I needed this)
	@Override
	public String findMin() {
		// TODO Auto-generated method stub
		if (empty()) {
			return null;
		} else {
			splay(root.findMin());
			return root.findMin().data;
		}
	}

	//Copied from og
	@Override
	public String findMax() {
		// TODO Auto-generated method stub
		if (empty()) {
			return null;
		} else {
			splay(root.findMax());
			return root.findMax().data;
		}
	}
	
	// Node after
	public BST_Node after(BST_Node parent) {
		// Check right, then go left
		if (parent.right != null) {
			return findMin(parent.right);
		}

		// Check parents
		BST_Node grandparent = parent.par;
		while (grandparent != null && parent == grandparent.right) {
			parent = grandparent;
			grandparent = grandparent.par;
		}
		return grandparent;
	}

	// Find node before
	public BST_Node before(BST_Node parent) {
		// Check left, then go right
		if (parent.left != null) {
			return findMax(parent.left);
		}
		
		// Check parents
		BST_Node grandparent = parent.par;
		while (grandparent != null && parent == grandparent.left) {
			parent = grandparent;
			grandparent = grandparent.par;
		}
		return grandparent;
	}


	public void insert(String s) {
		//Added this cont from og for check on dupes
		if (contains(s)) {
			return;
		} else {
		size += 1;
		BST_Node node = new BST_Node(s);
		BST_Node grandparent = null;
		BST_Node parent = this.root;
		
		while (parent != null) {
			grandparent = parent;
			if (node.data.compareTo(parent.data) < 0) {
				parent = parent.left;
			} else {
				parent = parent.right;
			}
		}
		
		// Grandparent is parent of parent
		node.par = grandparent;
		if (grandparent == null) {
			root = node;
		} else if (node.data.compareTo(grandparent.data) < 0) {
			grandparent.left = node;
		} else {
			grandparent.right = node;
		}

		// Splay for ins
		splay(node);
		}
	}

	// Delete (converts from string)
	public void remove(String s) {
		removeHelper(this.root, s);
	}

	//Copied from og
	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//Copied from og
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	//Copied from og
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