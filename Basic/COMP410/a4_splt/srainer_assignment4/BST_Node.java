package srainer_assignment4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
//	BST_Node current;
//	BST_Node parent;
//	BST_Node grandparent;
	
	BST_Node(String data){
		this.data=data;
	}
	
	public String getData(){
		return data;
	}
	public BST_Node getLeft(){
		return left;
	}
	public BST_Node getRight(){
		return right;
	}
//	public boolean splayNode(){
//		return;
//  }	
//	public boolean splayTree(String s) {
//		if( grandparent == null) {
//			//zig
//		} else {
//			if(grandparent.left == parent) {
//				if(parent.left == current) {
//					//zig-zig
//				} else {
//					//zig-zag
//				}
//			} else {
//				if(parent.right == current) {
//					//zig-zig
//				} else {
//					//zig-zag
//				}
//			}
//		}
//		return true;
//	}
			//2. needs to check if any further moves to do by looking back up the tree
			
			// make helpers for zig, zig-zig, and zig-zag because we have
			// a left and right based turn depending on tree structure
			// might need parent as well to trace back (n/2) for node position
			// and a a grandparent as (parent/2)
		
		//zig
			//if(parent.left == null || parent.right == null) then zig
			//right turn						zig
			//temp = root;				   1	cont 4	 4
			//root = left;		   		  /	    ->        \
			//right = temp;		 		 4 			       1
			//left turn						    zig
			//temp = root;				 1	    cont 4	   4
			//root = root;		   		  \	    ->        /
			//left = temp;		 		   4 		     1
		//zig-zag
			//if(parent.left) == data
			//	parent.parent.right == data then zig-zag
			//	  else zig-zig
			//if(parent.right) == data
			//  parent.parent.left == data then zig-zag
			//	  else zig-zig
			//right turn					zig-zag
			//temp = root;				5	cont 4    4
			//root = left.right;   	   /		     / \
			//					  	  3       ->    3   5	
			//right = temp;            \
			//							4
			//left turn						zig-zag
			//temp = root;				3	cont 4    4
			//					   	     \		     / \
			//root = right.left;  	      5   ->    3   5	
			//					         /
			//left = temp;		        4	
		//zig zig
			//right turn					zig-zig
			//temp = root;				1	cont 3	 3
			//root = left.left;	   	   /			  \
			//					 	  4       ->   	   4
			//left.left = temp;		 /   				\
			//						3					 1
			//left turn						zig-zig
			//temp = root;			   1	cont 3	   3
			//					   	    \			  /
			//root = right.right; 	     4    ->   	 4
			//						      \			/
			//right.right = temp;  		   3	   1		
	
	public boolean containsNode(String s){ //it was me
		if(data.equals(s))return true;
		if(data.compareTo(s)>0){//s lexiconically less than data
			if(left==null)return false;
			return left.containsNode(s);
			//1. on this contains true, move node to root		
		}
		if(data.compareTo(s)<0){
			if(right==null)return false;
			return right.containsNode(s);
			//1. on this contains true, move node to root
		}
		return false; //shouldn't hit
	}
	public boolean insertNode(String s){
		if(data.compareTo(s)>0){
			if(left==null){
				left=new BST_Node(s);
				return true; // end if leaf 
				//1. on this insert true, move node to root
			}
			return left.insertNode(s); // recurse if not leaf 
		}
		if(data.compareTo(s)<0){
			if(right==null){
				right=new BST_Node(s);
				return true;
				//1. on this insert true, move node to root
			}
			return right.insertNode(s);
		}
		return false;//ie we have a duplicate
	}
	public boolean removeNode(String s){ //DIO
		if(data==null)return false;
		if(data.equals(s)){
			if(left!=null){
				//1. on this remove true, move node to root
				data=left.findMax().data;
				left.removeNode(data);
				if(left.data==null)left=null;
			}
			else if(right!=null){
				//1. on this remove true, move node to root
				data=right.findMin().data;
				right.removeNode(data);
				if(right.data==null)right=null;
			}
			else data=null;
			return true;
		}
		else if(data.compareTo(s)>0){
			if(left==null)return false;
			if(!left.removeNode(s))return false;
			if(left.data==null)left=null;
			return true;
		}
		else if(data.compareTo(s)<0){
			if(right==null)return false;
			if(!right.removeNode(s))return false;
			if(right.data==null)right=null;
			return true;
		}
		return false;
	}
	public BST_Node findMin(){
		if(left!=null)return left.findMin();
		//1. on this find true, move node to root
		return this;
	}
	public BST_Node findMax(){
		if(right!=null)return right.findMax();
		//1. on this find true, move node to root
		return this;
	}
	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}
	public String toString(){
		return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")+",Right: "+((this.right!=null)?right.data:"null");
	}
	
}
