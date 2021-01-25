package BST_A2;

public class BST_Playground {
/*
 * you will test your own BST implementation in here
 *
 * we will replace this with our own when grading, and will
 * do what you should do in here... create BST objects,
 * put data into them, take data out, look for values stored
 * in it, checking size and height, and looking at the BST_Nodes
 * to see if they are all linked up correctly for a BST
 * 
*/
  
  public static void main(String[]args){
	  
	  test1();
	  
   // you should test your BST implementation in here
   // it is up to you to test it thoroughly and make sure
   // the methods behave as requested above in the interface
  
   // do not simple depend on the oracle test we will give
   // use the oracle tests as a way of checking AFTER you have done
   // your own testing

   // one thing you might find useful for debugging is a print tree method
   // feel free to use the printLevelOrder method to verify your trees manually
   // or write one you like better
   // you may wish to print not only the node value, and indicators of what
   // nodes are the left and right subtree roots,
   // but also which node is the parent of the current node
 
  }
  
  public static void test1() {
	  BST_Node BST = new BST_Node();
	  BST.insert("B");
	  BST.insert("A");
	 // BST.insert("D");
	 // BST.insert("C");
	 // BST.insert("F");
	 // BST.insert("E");
	 // printInOrder(BST);
	 // BST.remove("D");
	  printInOrder(BST);
	  /*                  B					level 0   l root
	   *                 / \				
	   *                A   D				level 1   l root
	   *                   / \
	   *              	  C	  F				level 2   leaf
	   *                     /
	   *                    E				level 3   leaf
	  */
//	  BST.insert("c");
//	  BST.insert("b");
//	  BST.insert("a");
//	  BST.insert("d");
//	  BST.insert("e");
//	  BST.insert("f");
//	  BST.insert("g");
//	  BST.insert("q");
//	  BST.insert("w");
//	  BST.insert("i");
//	  BST.insert("z");
//	  BST.insert("x");
//	  BST.insert("y");
//	  BST.insert("A");
//	  printInOrder(BST);
	  //printGivenLevel(BST, 0);
	  /*                  c					level 0   l/r root
	   *                 / \				
	   *                d   b				level 1   l/r root
	   *               /     \				
	   *              e       a				level 2   l root, r leaf
	   *             /         \				
	   *            f           A 			level 3   l root
	   *           /              			
	   *          g                			level 4   l root
	   *         /                  		
	   *        q                   		level 5   l/r root
	   *       / \                   
	   *      w   i                 		level 6   l root, r leaf
	   *     /                          
	   *    z                            	level 7   r root
	   *     \                          
	   *      x                        		level 8   l root
	   *     /                         
	   *    y                           	level 9   leaf
	   */
//	  BST.remove("x");
//	  BST.remove("q");
//	  printInOrder(BST);
	  /*                  c					level 0   l/r root
	   *                 / \				
	   *                d   b				level 1   l/r root
	   *               /     \				
	   *              e       a				level 2   l root, r leaf
	   *             /         \				
	   *            f           A 			level 3   l root
	   *           /              			
	   *          g                			level 4   l root
	   *         /                  		
	   *        i                   		level 5   l root
	   *       /                    
	   *      w                    		    level 6   l root
	   *     /                          
	   *    z                            	level 7   r root
	   *     \                          
	   *      y               				level 8   leaf                   	
	   */
  }
  
  static void printLevelOrder(BST tree){ 
  //will print your current tree in Level-Order...
  //https://en.wikipedia.org/wiki/Tree_traversal
    int h=tree.getRoot().getHeight();
    for(int i=0;i<=h;i++){
      printGivenLevel(tree.getRoot(), i);
    }
    
  }
  static void printGivenLevel(BST_Node root,int level){
    if(root==null)return;
    if(level==0)System.out.print(root.data+" ");
    else if(level>0){
      printGivenLevel(root.left,level-1);
      printGivenLevel(root.right,level-1);
    }
  }
  static void printInOrder(BST_Node root){
  //will print your current tree In-Order
  if(root!=null){
    printInOrder(root.getLeft());
    System.out.print(root.getData() + " ");
    printInOrder(root.getRight());
  }
  }
  }
