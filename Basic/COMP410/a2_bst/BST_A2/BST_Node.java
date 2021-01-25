package BST_A2;

public class BST_Node {
  String data;
  BST_Node left;
  BST_Node right;
  BST_Node parent;
  int size;
  int height = 0;
  int counterMin = 0;
  int counterMax = 0;
  
  BST_Node(String data){ this.data=data; }

  // --- used for testing  ----------------------------------------------
  //
  // leave these 3 methods in, as is

  public String getData(){ return data; }
  public BST_Node getLeft(){ return left; }
  public BST_Node getRight(){ return right; }

  // --- end used for testing -------------------------------------------

  
  // --- fill in these methods ------------------------------------------
  //
  // at the moment, they are stubs returning false 
  // or some appropriate "fake" value
  //
  // you make them work properly
  // add the meat of correct implementation logic to them

  // you MAY change the signatures if you wish...
  // make the take more or different parameters
  // have them return different types
  //
  // you may use recursive or iterative implementations

  //Root
  BST_Node root;
 
  
  //Const. for BST
  BST_Node(){
	  root = null;
  }
  
  //Bool to compare if node at root is same as node s
  public boolean containsNode(String s){
		while(root != null) {
			if(root.data.compareTo(s) == 0) {
				return true;
			} else if(root.data.compareTo(s) > 0 ) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return false;
	}
	
  //Simple insert for linking root to the new/next node
  void insert(String s) {
	  root = insertNode(root, s);
	  size++;
  }
  
  //Simple remove for unlinking root to the last node
  void remove(String s) {
	  root = removeNode(root, s);
	  size--;
  }

public BST_Node insertNode(BST_Node root, String s){
		  //Move until null, leaf or first node
	 	  if(root == null) {
			 root = new BST_Node(s);
			 parent = root;
			 return root;
		  }
	 	  //Checking for positive or negative
	      int result = root.data.compareTo(s);
	      if(-1 * result > height) {
	    	  height = (result * -1) - 1;
	      }
	      //Node already exists
		  if(result == 0) {
			  return root;
		  //testing for left == null to stop replacement?
		  } else if(result > 0) {
		  //Root. to setup for recursion
			  root.left = insertNode(root.left, s);
		  //Setup for print statement
			  left = root.left;
		  } else if(result < 0) {
			  root.right = insertNode(root.right, s);
			  right = root.right;
		  }
		parent = root;
		return root;		
	  }
  
  public BST_Node removeNode(BST_Node root, String s){
      if(root == null) {
		  return root;
      }
      int result = root.data.compareTo(s);
  
      if(result == 0) {
    	  if(root.left == null) {
    		  return root.right;
    	  } else if(root.right == null) {
    		  return root.left;
    	  }
    	  //Need to travel down left and find min to replace "removed"
    	  root.data = findMin();
    	  root.right = removeNode(root.right, s);
    	  root = null;
      } else if(result > 0) {
    	  root.left = removeNode(root.left, s);
      } else if(result < 0) {
    	  root.right = removeNode(root.right, s);
      }
    return root;
  }
  
  public String findMin(){
	  //Find the min by traveling while root is not a leaf
	  String min = root.data;
	  while(root.left != null) {
		  min = root.left.data;
		  root = root.left;
		  counterMin++;
	  }
	  return min;
	  }
  
  public String findMax(){
	  String max = root.data;
	  while(root.right != null) {
		  max = root.right.data;
		  root = root.right;
		  counterMax++;
	  }
	  return max;
	  }
  
  public int getHeight(){
	  // Unsure atm
	  return height;
  }
  
  public int size() {
	  // TODO Auto-generated method stub
	  return size;
  }
	
  

  // --- end fill in these methods --------------------------------------


  // --------------------------------------------------------------------
  // you may add any other methods you want to get the job done
  // --------------------------------------------------------------------
  
  public String toString(){
    return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")
            +",Right: "+((this.right!=null)?right.data:"null");
  }
}