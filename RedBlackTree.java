// --== CS400 File Header Information ==--
// Name: Lucas Nguyen
// Email: lfnguyen@wisc.edu
// Team: NE
// TA: Daniel Finer
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes within a
 * binary search tree. You can use this class' insert method to build a binary search tree, and its
 * toString method to display the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public boolean isBlack;

    public Node(T data) {
      this.data = data;
      this.isBlack = false;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }
      return output + "]";
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (root == null) {
      root = newNode;
    } // add first node to an empty tree
    else
      insertHelper(newNode, root); // recursively insert into subtree
    root.isBlack = true;// Make root black.
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */
  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
  }
  
  public boolean contains(T data) {
    if(data == null) return false;
    return containsHelper(this.root, data);
  }
  
  public boolean containsHelper(Node<T> node, T data) {
    if (node == null) return false;
    int compare = data.compareTo(node.data);
   
    if (compare == 0 ) {
      return true;
    }
    else if(compare < 0) {
      return containsHelper(node.leftChild, data);
    }
    else {
      return containsHelper(node.rightChild, data);
    }
  }
  
  

  /**
   * This method is run during the insert helper and ensures that the red black properties are
   * preserved after each red node is inserted into this red black tree
   * 
   * @param newNode node that is to be inserted into this red black tree
   */
  private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {

    Node<T> sibling = getSibling(newNode.parent);
    // the new node is the root or Parent is black. there is nothing to be done
    if (newNode.parent == null || newNode.parent.isBlack) {
      return;
    }
    // The sibling is black
    else if (sibling == null || sibling.isBlack) {
      insertCase2A(newNode);
    }
    // the sibling is red
    else if (!getSibling(newNode.parent).isBlack) {
      insertCase2B(newNode);
    }
  }

  /**
   * The following method is run if the insert follows a case 2A pattern. This is if the sibling of
   * the newly inserted nodes parent is black. The method runs 1 of 4 rotation types and then
   * recolors.
   * 
   * @param newNode
   */
  private void insertCase2A(Node<T> newNode) {
    Node<T> grandParent = getGrandParent(newNode);
    Node<T> parent = newNode.parent;
    // left right or right left rotation
    if ((!newNode.isLeftChild() && parent.isLeftChild())
        || (newNode.isLeftChild() && !parent.isLeftChild())) {
      rotate(newNode, parent);
      rotate(newNode, grandParent);
      // Recolor after the rotation
      recolor2A(newNode, grandParent);
      return;
    }
    // right or left rotation
    rotate(newNode.parent, grandParent);
    // recolor after the rotatiorn
    recolor2A(newNode.parent, grandParent);
    return;

  }

  /**
   * Is run if the insert follows a case 2B pattern meaning that the sibling of the parent to the
   * newly inserted node is red. recolors and then call the enforce tree properties
   * 
   * @param newNode the newly added node
   */
  private void insertCase2B(Node<T> newNode) {
    if (newNode == null)
      return;
    Node<T> grandParent = getGrandParent(newNode);
    Node<T> parent = newNode.parent;
    Node<T> sibling = getSibling(parent);
    sibling.isBlack = true;
    parent.isBlack = true;
    if (grandParent.parent != null) {
      grandParent.isBlack = false;
    }
    enforceRBTreePropertiesAfterInsert(grandParent);
  }

  /**
   * Recolors the nodes for a case 2A operations
   * 
   * @param newNode     the newly added node
   * @param grandParent the grandparent node
   */
  private void recolor2A(Node<T> newNode, Node<T> grandParent) {
    newNode.isBlack = true;
    grandParent.isBlack = false;
  }

  /**
   * gets the grandparent to the child node pased
   * 
   * @param newNode the child node
   * @return the grandparent node, or null if the grandparent does not exist
   */
  private Node<T> getGrandParent(Node<T> newNode) {
    if (newNode.parent == null)
      return null;
    if (newNode.parent.parent == null)
      return null;
    return newNode.parent.parent;
  }

  /**
   * gets the sibling of a parent node.
   * 
   * @param parent the parent node
   * @return the sibling or null if the parent is null or the sibling is null
   */
  private Node<T> getSibling(Node<T> parent) {
    if (parent == null)
      return null;
    if (parent.parent == null)
      return null;
    if (parent.isLeftChild())
      return parent.parent.rightChild;
    return parent.parent.leftChild;
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    if (child == null || child.parent == null || !child.parent.equals(parent)) {
      throw new IllegalArgumentException();
    }
    Node<T> grandParent = parent.parent;
    boolean parentIsLeftChild = parent.isLeftChild();
    if (child.isLeftChild()) {
      // right rotation
      // set parents left child to childs right child.
      parent.leftChild = child.rightChild;
      // Adjust parent pointer of the right child if it exits
      if (child.rightChild != null)
        child.rightChild.parent = parent;
      // set child's right child to parent
      child.rightChild = parent;
      // set the parent parents pointer to the child
      parent.parent = child;
      // set child's parent pointer to grandparent
      child.parent = grandParent;
      // set grandparent pointer to child or root
      if (grandParent == null)
        this.root = child;
      else if (parentIsLeftChild)
        grandParent.leftChild = child;
      else
        grandParent.rightChild = child;
      return;
    }
    // left rotations
    // set parent right child to child's left child
    parent.rightChild = child.leftChild;
    if (child.leftChild != null)
      child.leftChild.parent = parent;
    // set child's left child to parent
    child.leftChild = parent;
    // set the parent parents pointer to the child
    parent.parent = child;
    // set child's parent pointer to grandparent
    child.parent = grandParent;
    // set grandparent pointer to child or root
    if (grandParent == null)
      this.root = child;
    else if (parentIsLeftChild)
      grandParent.leftChild = child;
    else
      grandParent.rightChild = child;



  }

  public static void main(String[] args) {
    RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
    bst.insert(10);
    bst.insert(3);
    bst.insert(12);
    bst.insert(1);
    System.out.println(bst);
    System.out.println(bst.contains(1));
    System.out.println(bst.contains(0));



  }

}
