import java.io.Serializable;
import java.util.LinkedList;


/**
@author Qosai Kadadha
**/
public class FriendFinderRBTree implements FriendFinderRBTreeInterface, Serializable{
  private RedBlackTree<Person> tree = new RedBlackTree<Person>();
   /**
    * Inserts a person into the red black tree, the person objects name is the 
    * key. No duplicate keys
   * @param p the person object to insert
   * @return true if added, false if not added
   */
  @Override
  public boolean insert(Person p) { 
    if(this.contains(p.getFullName()))
        return false;
    tree.insert(p);
    return true;
  }
/**
    * Insets a person based on the name. This will make a person object and 
    * instantiate with the name
   * @param fullName the person objects name
   * @return true if added, false if already exists
   */
  @Override
  public boolean insert(String fullName) {
    Person p = new Person(fullName);
    if (this.contains(fullName)) {
      return false;
    }
    tree.insert(p);
     return true; 
   }
 
/**
   * Prints the people in alphabetical order
   @return String variable that contains person objects in alphabetical order
   */
  @Override
  public String printAllInOrder() {
    if(isEmpty()) { //ensures tree is not empty before attempting to print 
      return "Tree is Empty, cannot print empty tree";
    }
   RedBlackTree.Node<Person> temp = tree.root;
   while(temp.leftChild != null) { //while loop that sets tree pointer to Person who is first alphabetically
     temp = temp.leftChild;
   }
   String output = temp.data.toString();
    temp = temp.parent;
    while(true) { //loop runs until it runs out of nodes to check in tree
      if(!output.contains(temp.data.toString())){ //puts node in output string if it isn't already in the string
      output += temp.data.toString(); 
      }
      if((temp.leftChild != null) && (!output.contains(temp.leftChild.data.toString()))) { //checks if leftchild exists and is not in string than return to start of loop with leftchild
        temp = temp.leftChild;
        continue;
      }
      if((temp.rightChild != null) && (!output.contains(temp.rightChild.data.toString()))) {//checks if rightchild exists and is not in string than return to start of loop with leftchild
        temp = temp.rightChild;
        continue;
      }
      if(!output.contains(temp.parent.data.toString())) {//checks if parent is in string and returns to start of loop if not within string
        temp = temp.parent;
        continue;
      }
    break;
    }
    return output;
  }
 /**
   * Prints the tree in level order
   @return String variable that contains tree in level order
   */
  @Override
  public String printAllLevelOrder() {
    if(isEmpty()) { //ensures tree is not empty before attempting to print 
      return "Tree is Empty, cannot print empty tree";
    }
    LinkedList<RedBlackTree.Node<Person>> q = new LinkedList<>();
    String output = "";
    q.add(tree.root);
    
    while (!q.isEmpty()) {
      RedBlackTree.Node<Person> next = q.removeFirst();
      if (next.leftChild != null) {
        q.add(next.leftChild);
      }
      if (next.rightChild != null) {
        q.add(next.rightChild);
      }  
      output += next.data.toString();
    }
    return output;
  }
    
  
 /**
    * takes a name and checks if the person exists
   * @param fullName the name of the person
   * @return true if name is inside tree, false otherwise
   */
  @Override
  public boolean contains(String fullName) {
    if (fullName == null) return false;
    Person p = new Person(fullName);
    return containsHelper( tree.root, p);
    
  }
  //private recursive helper method for contains
  private boolean containsHelper(RedBlackTree.Node<Person> node,Person p) {
    if (node == null) return false;
    int compare = p.compareTo(node.data);
   
    if (compare == 0 ) {
      return true;
    }
    else if(compare < 0) {
      return containsHelper(node.leftChild, p);
    }
    else {
      return containsHelper(node.rightChild, p);
    }

  }
/**
    * checks if tree has any elements 
   * @return true if empty, false if not empty
   */
  @Override
  public boolean isEmpty() {
    try {
    if (tree.root != null) {
      return false;
    }
    return true;
    }
    catch(NullPointerException e) {
      return true;
    }
  }
/**
    * Looks up and returns the person object based on the name
   * @param fullName the name of the person
   * @return person object
   */
  @Override
  public Person lookup(String fullName) {
    Person p = new Person(fullName);
    RedBlackTree.Node<Person> temp = tree.root;
    if (this.contains(fullName) == false) {
      return null;
    }
    while(true) { //compares fullName argument to every node in the tree until it finds the same name
      if (p.compareTo(temp.data) == 0) {
        return temp.data;
      }
      else if(p.compareTo(temp.data) < 0) {
        temp = temp.leftChild;
        continue;
      }
      else if(p.compareTo(temp.data) > 0){
        temp = temp.rightChild;
        continue;
      }
    }
    
  }
/**
    * Gets the string representation of the person object based on the name
   * @param fullName the name of the person
   * @return string representaion of the person
   */
  @Override
  public String getPersonData(String fullName) {
    return lookup(fullName).toString();
  }
  
  @Override
  public String toString() {
    return this.printAllLevelOrder();
  }
    
  }
  

