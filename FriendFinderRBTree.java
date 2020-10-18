import java.util.LinkedList;


/**
@author Qosai Kadadha
**/
public class FriendFinderRBTree implements FriendFinderRBTreeInterface{
  private RedBlackTree<Person> tree = new RedBlackTree<Person>();
  FriendFinderRBTree FriendFinder = new FriendFinderRBTree();
  @Override
  public boolean insert(Person p) {
    if(FriendFinder.contains(p.getFullName()))
    FriendFinder.insert(p);
        return false;
  }

  @Override
  public boolean insert(String fullName) {
    Person p = new Person(fullName);
    if (FriendFinder.contains(fullName)) {
      return false;
    }
    FriendFinder.insert(p);
     return true; 
   }
 

  @Override
  public void printAllInOrder() {
   RedBlackTree.Node<Person> temp = tree.root;
   while(temp.leftChild != null) { //while loop that sets tree pointer to Person who is first alphabetically
     temp = temp.leftChild;
   }
    System.out.print("[" + temp.data.toString() + ", ");
    String output = "[" + temp.data.toString() + ", ";
    temp = temp.parent;
    while(true) {
      System.out.print(temp.data.toString() + ", ");
      output = temp.data.toString() + ", "; 
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
    System.out.print("]");
  }

  @Override
  public void printAllLevelOrder() {
    LinkedList<RedBlackTree.Node<Person>> q = new LinkedList<>();
    q.add(tree.root);
    System.out.print("[");
    while (!q.isEmpty()) {
      RedBlackTree.Node<Person> next = q.removeFirst();
      if (next.leftChild != null)
        q.add(next.leftChild);
      if (next.rightChild != null)
        q.add(next.rightChild);
      System.out.println(next.data.toString());
      if (!q.isEmpty())
        System.out.print(", ");
    }
    System.out.print("]");
  }
    
  

  @Override
  public boolean contains(String fullName) {
    Person p = new Person(fullName);
   if (tree.contains(p)) {
     return true;
   }
   return false;
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
    RedBlackTree.Node<Person> temp = tree.root.leftChild;
    if (FriendFinder.contains(fullName) == false) {
      return null;
    }
    while(true) { //compares fullName argument to every node in the tree until it finds the same name
      if (p.compareTo(temp.data) == 0) {
        return temp.data;
      }
      if(p.compareTo(temp.data) < 0) {
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
  
}
