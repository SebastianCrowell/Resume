package BST_A2;

public class BST implements BST_Interface {
  public BST_Node root;
  BST_Node parent;
  int size;
  int height = 0;
  int counterMin = 0;
  int counterMax = 0;
  
  public BST(){ size=0; root=null; }
  
  @Override
  //used for testing, please leave as is
  public BST_Node getRoot(){ return root; }

  //Simple insert for linking root to the new/next node
  public boolean insert(String s) {
	  root = insertNode(root, s);
	  size++;
	return true;
  }
  
  //Simple remove for unlinking root to the last node
  public boolean remove(String s) {
	  root = removeNode(root, s);
	  size--;
	return true;
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
		  } else if(result < 0) {
			  root.right = insertNode(root.right, s);
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
	  /*Unsure atm, seems like the correct way to check as I want the 
	   * longest path? This would check both sides and return the longer
	   */
	  return root.height;
      }

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		if(root == null) {
		return true;
		}
		return false;
	}
	
	@Override
	public boolean contains(String s) {
		// TODO Auto-generated method stub
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
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}
	
	@Override
	public int height() {
		// TODO Auto-generated method stub
		return height;
	}
}
  