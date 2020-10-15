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

public class TestRedBlackTree {

  /**
   * Returns the red black tree level order to string with the colors represented as boolean values.
   * 
   * @param bst the red black binary search tree
   * @return The string representation of the tree
   */
  private static String getColorWithData(RedBlackTree<Integer> bst) {
    String output = "[";
    LinkedList<RedBlackTree.Node<Integer>> q = new LinkedList<>();
    q.add(bst.root);
    while (!q.isEmpty()) {
      RedBlackTree.Node<Integer> next = q.removeFirst();
      if (next.leftChild != null)
        q.add(next.leftChild);
      if (next.rightChild != null)
        q.add(next.rightChild);
      output += next.data.toString() + ":" + next.isBlack;// @TODO Added Part to to string
      if (!q.isEmpty())
        output += ", ";
    }
    return output + "]";

  }

  /**
   * This creates different trees for a case two A insert (Sibling is black) for the tree passed
   * 
   * @param bst             The red black binary search tree
   * @param isBlackSideLeft the side to make black
   */
  private static void createCase2ATree(RedBlackTree<Integer> bst, boolean isBlackSideLeft) {
    bst.root = new RedBlackTree.Node<Integer>(10);
    bst.root.isBlack = true;
    bst.root.leftChild = new RedBlackTree.Node<Integer>(5);
    bst.root.leftChild.parent = bst.root;
    bst.root.rightChild = new RedBlackTree.Node<Integer>(15);
    bst.root.rightChild.parent = bst.root;
    if (isBlackSideLeft) {
      bst.root.leftChild.isBlack = true;
      return;
    }
    bst.root.rightChild.isBlack = true;
  }



  /**
   * Tests root property, And Case 2B where the sibling is red These are tested using natural
   * insertion
   */
  @Test
  public void testCase2B() {
    RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
    bst.insert(10);
    // test root
    assertEquals(bst.root.isBlack, true);
    bst.insert(3);
    bst.insert(12);
    bst.insert(1);
    // test case 2B on a 4 node tree
    assertEquals(getColorWithData(bst), "[10:true, 3:true, 12:true, 1:false]");
    bst.insert(7);
    bst.insert(2);
    // test case 2b on a 6 node tree
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[10:true, 3:false, 12:true, 1:true," + " 7:true, 2:false]");
  }

  /**
   * Tests the four subcases for case 2A using manmade red black trees
   */
  @Test
  public void testCase2A() {
    RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
    // test three node insert
    bst.insert(10);
    bst.insert(5);
    bst.insert(3);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[5:true, 3:false, 10:false]");
    // test case 2A Left left case
    bst = new RedBlackTree<Integer>();
    createCase2ATree(bst, false);
    bst.insert(3);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[5:true, 3:false, 10:false, 15:true]");
    // test case 2A right right case
    bst = new RedBlackTree<Integer>();
    createCase2ATree(bst, true);
    bst.insert(17);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[15:true, 10:false, 17:false, 5:true]");
    // test case 2A left right case
    bst = new RedBlackTree<Integer>();
    createCase2ATree(bst, false);
    bst.insert(7);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[7:true, 5:false, 10:false, 15:true]");
    // test case 2A right left case
    bst = new RedBlackTree<Integer>();
    createCase2ATree(bst, true);
    bst.insert(12);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[12:true, 10:false, 15:false, 5:true]");
  }

  /**
   * Tests natural insertion example from lecture.
   */
  @Test
  public void testNaturalInsertion() {
    RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
    bst.insert(7);
    bst.insert(14);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst), "[7:true, 14:false]");
    bst.insert(18);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[14:true, 7:false, 18:false]");
    bst.insert(23);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[14:true, 7:true, 18:true, 23:false]");
    bst.insert(1);
    bst.insert(11);

    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[14:true, 7:true, 18:true, 1:false, " + "11:false, 23:false]");
    bst.insert(20);

    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[14:true, 7:true, 20:true, 1:false, " + "11:false, 18:false, 23:false]");
    bst.insert(29);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[14:true, 7:true, 20:false, 1:false, " + "11:false, 18:true, 23:true, 29:false]");

  }

  /**
   * Tests the cascading fix for a cae 2B insertion
   */
  @Test
  public void testCascadingFix() {
    RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
    bst.insert(7);
    bst.insert(14);
    bst.insert(18);
    bst.insert(23);
    bst.insert(1);
    bst.insert(11);
    bst.insert(20);
    bst.insert(29);
    bst.insert(25);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[14:true, 7:true, 20:false, 1:false, "
            + "11:false, 18:true, 25:true, 23:false, 29:false]");
    bst.insert(27);
    assertEquals("Output:" + getColorWithData(bst), getColorWithData(bst),
        "[20:true, 14:false, 25:false, 7:true, "
            + "18:true, 23:true, 29:true, 1:false, 11:false, 27:false]");
  }
}
