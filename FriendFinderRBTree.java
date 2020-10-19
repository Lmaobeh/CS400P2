import java.io.Serializable;
import java.util.LinkedList;


/**
@author Qosai Kadadha
**/
public class FriendFinderRBTree implements FriendFinderRBTreeInterface, Serializable{
  private RedBlackTree<Person> tree = new RedBlackTree<Person>();
  
  @Override
  public boolean insert(Person p) {
    if(this.contains(p.getFullName()))
        return false;
    tree.insert(p);
    return true;
  }

  @Override
  public boolean insert(String fullName) {
    Person p = new Person(fullName);
    if (this.contains(fullName)) {
      return false;
    }
    tree.insert(p);
     return true; 
   }
 

  @Override
  public String printAllInOrder() {
    if(isEmpty()) {
      return "Tree is Empty, cannot print empty tree";
    }
   RedBlackTree.Node<Person> temp = tree.root;
   while(temp.leftChild != null) { //while loop that sets tree pointer to Person who is first alphabetically
     temp = temp.leftChild;
   }
   String output = temp.data.toString();
    temp = temp.parent;
    while(true) {
      if(!output.contains(temp.data.toString())){
      output += temp.data.toString(); 
      }
      if((temp.leftChild != null) && (!output.contains(temp.leftChild.data.toString()))) {
        temp = temp.leftChild;
        continue;
      }
      if((temp.rightChild != null) && (!output.contains(temp.rightChild.data.toString()))) {
        temp = temp.rightChild;
        continue;
      }
      if(!output.contains(temp.parent.data.toString())) {
        temp = temp.parent;
        continue;
      }
    break;
    }
    return output;
  }

  @Override
  public String printAllLevelOrder() {
    if(isEmpty()) {
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
    
  

  @Override
  public boolean contains(String fullName) {
    if (fullName == null) return false;
    Person p = new Person(fullName);
    return containsHelper( tree.root, p);
    
  }
  
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

  @Override
  public String getPersonData(String fullName) {
    return lookup(fullName).toString();
  }
  
  public static void main(String[] args) {
    FriendFinderRBTree f = new FriendFinderRBTree();
    f.insert(new Person("Lucas"));
    f.insert("Dabis:");
    f.insert("Looook");
   //System.out.println(f.contains("g"));
   //System.out.println(f.contains("Looook"));
   //System.out.println(f.lookup("Looook"));
   System.out.println(f.printAllInOrder());
    
  }
  
}
