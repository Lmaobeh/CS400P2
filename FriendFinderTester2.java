// --== CS400 File Header Information ==--
// Name: <Michael Brudos>
// Email: <mbrudos@wisc.edu>
// Team: <NE>
// TA: <Daniel>
// Lecturer: <Gary>
// Notes to Grader: <optional extra notes>
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * @author Michael Brudos
 *  Tests the proper functionality of the FriendFinder.java, the primary class in Group Project 2. Tests are conducted using JUnit
 */
class FriendFinderTester2 {

  /**
   * Checks insert method to make sure two people with the same name are not added to tree 
   */
  @Test
  void testCurrentUserDoubleInsert() {
    // Double insert test
    FriendFinder app = new FriendFinder();
    //Insert first person
    app.updateCurrentUser("Lucas");
    app.masterList.insert("Lucas");
    // Create a string for test case
    String output = app.masterList.toString();
    String outputFail = output + output;
    if (app.masterList.toString().equals(outputFail)) {
      fail("Lucas has been inserted into the tree twice: Checked with toString()");
    }
    //This attempt should be successful with a different name
    String lucassUser = output.replace("Lucas", "Lucass");
    String correctOutput = output + lucassUser;
    app.masterList.insert("Lucass");
    if (!app.masterList.toString().equals(correctOutput)) {
      fail("The toString() output after adding Lucass was incorrect");
    }
 }
  
  /**
   * Changes the curUser field to different person objects in MasterList and verifies that changes are correct and appropriate
   */
  @Test
  void testChangeCurrentUser() {
    FriendFinder app = new FriendFinder();
    //Create person 1 and add
    Person p = new Person("Adam", "Dude", "Imaginary Lane", null);
    app.masterList.insert(p);
    app.updateCurrentUser("Adam");
    //Make sure curUser is the one person in the tree
    if(!app.curUser.equals(p)) {
      fail("The curUser is not Adam it is:" + app.curUser.getFullName());
    }
//    System.out.println(app.masterList);
//    System.out.println(p);
    //Use string for test case
    String adam = app.masterList.toString();
    //Test to make sure adam is still ok
    if(!app.masterList.toString().equals(p.toString())){
      fail("There is a different # of people in the tree than expected");
    }
    if(!app.curUser.getFullName().equals(p.getFullName())) {
      fail("The name of the curNode doesn't match the person provided: Adam");
    }
    if(!app.curUser.getResidency().equals(p.getResidency())) {
      fail("The residency of the curNode doesn't match the person provided: Adam");
    }
    if(!app.curUser.getBio().equals(p.getBio())) {
      fail("The bio of the curNode doesn't match the person provided: Adam");
    }
    if(app.curUser.getFriendsList() != null) {
      fail("The friendlist of the curNode doesn't match the person provided (curNode friend List is populated)");;
    }
    
    //insert another person and do operations
    p = new Person("Sherley", "Lady", "Imaginary Drive", null);
    app.masterList.insert(p);
    app.updateCurrentUser("Sherley");
    //Is it you Sherley?
    if(!app.curUser.equals(p)) {
      fail("The curUser is not Sherly it is:" + app.curUser.getFullName());
    }
    String sherley = adam.replace("Adam", "Sherley").replace("Imaginary Lane", "Imaginary Drive").replace("Dude", "Lady");
//    System.out.println(app.masterList.toString());
//    System.out.println();
//    System.out.println(sherley);
    //Is it really Sherley?
    if(!app.masterList.toString().contains(sherley)){
      fail("Sherley was not represented properly by toString method. This is likely a cause of a insert error");
    }
    if(!app.curUser.getFullName().equals(p.getFullName())) {
      fail("The residency of the curNode doesn't match the person provided: Sherley");
    }
    if(!app.curUser.getResidency().equals(p.getResidency())) {
      fail("The residency of the curNode doesn't match the person provided: Sherley");
    }
    if(!app.curUser.getBio().equals(p.getBio())) {
      fail("The bio of the curNode doesn't match the person provided: Sherley");
    }
    if(app.curUser.getFriendsList() != null) {
      fail("The friendlist of the curNode doesn't match the person provided: Sherley (curNode friend List is populated)");;
    }
    
  }
  
  /**
   * Checks the functionality of the checkMutualFriends method along with other important setters and methods from the CurrentUser class
   */
  @Test
  void testMutualFriendCheck() {
    // Create the friend lists
    FriendFinderRBTree adamTree = new FriendFinderRBTree();
    adamTree.insert("Sherley Timple");
    FriendFinderRBTree sherleyTree = new FriendFinderRBTree();
    sherleyTree.insert("Adam Shingles");
    //Create two people that are initially mutual friends
    Person adam = new Person("Adam Shingles", "Dude", "Imaginary Lane", adamTree);
    Person sherley = new Person("Sherley Timple", "Lady", "Imaginary Drive", sherleyTree);
    //Test non existing person becoming mutual
    Person rando = new Person("rando", "Dude", "Imaginary Imaginary", adamTree);
    //Test existing person becoming mutual
    Person crush = new Person("David", "Dude", "Imaginary Avenue", adamTree);
    //Make the FriendFinder and insert the appropriate people per test case
    FriendFinder app = new FriendFinder();
    app.masterList.insert(adam);
    app.masterList.insert(sherley);
    app.masterList.insert(crush);
 //   System.out.println(app.masterList.toString());
    //Check if Adam is mutual friends with Sherley from Adam's perspective
    app.updateCurrentUser("Adam Shingles");
    if(!app.curUser.checkForMutualFriend(sherley)) {
      fail("Adam and Sherley are mutual bestfriends");
    }
    //Check that a person who isn't a mutual friends doesn't return true
    if(app.curUser.checkForMutualFriend(rando)) {
      fail("Rando is not mutual friends with Adam");
    }
    // Check if Sherley is mutual friends with Adam from Sherley's perspective
    app.updateCurrentUser("Sherley Timple");
    if(!app.curUser.checkForMutualFriend(adam)) {
      fail("Sherley and Adam are mutual bestfriends");
    }
    //Check that a person who isn't a mutual friends doesn't return true
    if(app.curUser.checkForMutualFriend(rando)) {
      fail("Rando is not mutual friends with Sherley");
    }
    //Now make rando a mutual friend - case 2 for insertFriends method
    app.curUser.insertFriend(rando);
 //   System.out.println(app.masterList.toString());
    if(!app.curUser.checkForMutualFriend(rando)) {
      fail("Rando should be a mutual friends with Sherley after using insertFriend");
    }
    //Check that David isn't a mutual friend with Sherley yet
    if(app.curUser.checkForMutualFriend(crush)) {
      fail("David should not be mutual friends with Sherley before using insertFriend");
    }
    //Add David to friend list of Sherley
    app.curUser.insertFriend(crush);
    //David is now mutual friend of Sherley
    if(!app.curUser.checkForMutualFriend(crush)) {
      fail("David should be a mutual friends with Sherley after using insertFriend");
    }
    //Test setter methods
    app.curUser.setBio("Woman");
    app.curUser.setResidency("Kansas");
    if(!app.curUser.getBio().equals("Woman")) {
      fail("curUsers (Sherley) should have the bio 'Woman' after using setBio() but instead it has: " + app.curUser.getBio());
    }
    if(!app.curUser.getResidency().equals("Kansas")) {
      fail("curUsers (Sherley) should have the residency 'Kansas' after using setResidency() but instead it has: " + app.curUser.getResidency());
    }
    //Save progress and reload to determine proper functionality
    app.curUser.saveCurrentUser();
//    System.out.println(app.curUser);
    app.updateCurrentUser("Adam Shingles");
    app.updateCurrentUser("Sherley Timple");
//    System.out.println(app.curUser);
    //Check that old changes were saved
    if(!app.curUser.checkForMutualFriend(rando)) {
      fail("Rando should still be a mutual friends with Sherley after changing curUser");
    }
    if(!app.curUser.checkForMutualFriend(crush)) {
      fail("David should still be a mutual friends with Sherley after changing curUser");
    }
    if(!app.curUser.getBio().equals("Woman")) {
      fail("curUsers (Sherley) should have the bio 'Woman' after changing users but instead it has: " + app.curUser.getBio());
    }
    if(!app.curUser.getResidency().equals("Kansas")) {
      fail("curUsers (Sherley) should have the residency 'Kansas' after changing users but instead it has: " + app.curUser.getResidency());
    }
    
  }
  
  /**
   *  Tests to see if the file FriendFinder.ser can be read in properly for editing
   */
  @Test
  void testReadIn() {
    try {
    FriendFinder app = new FriendFinder("FriendFinder.ser");
 //   System.out.println(app.masterList);
    }
    catch(Exception e) {
//     System.out.println("Something went bad: " + e.getMessage());
//     e.printStackTrace();
      fail("Serialization was not setup properly");
    }
  }
  
 // @Test 
//  void testSave() {
//    try {
//      FriendFinder app = new FriendFinder("FriendFinder.ser");
//      app.masterList.insert("Paula");
//      System.out.println("Good");
//      app.exitAndSave("FriendFinder.ser");
//      app = new FriendFinder("FriendFinder.ser");
//      if(!app.masterList.contains("Paula")) {
//        fail("Paula wasn't saved from the last save");
//      }
//    }
//    catch(Exception e) {
//    System.out.println("Something went bad: " + e.getMessage());
//    e.printStackTrace();
//    fail("Serialization was not setup properly");
//    }
//  }
}