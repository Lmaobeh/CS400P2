import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class FriendFinderTester2 {

  @Test
  void testCurrentUserDoubleInsert() {
    // Double insert test
    FriendFinder app = new FriendFinder();
    app.updateCurrentUser("Lucas");
    app.masterList.insert("Lucas");
    String output = app.masterList.toString();
    String outputFail = output + output;
    if (app.masterList.toString().equals(outputFail)) {
      fail("Lucas has been inserted into the tree twice: Checked with toString()");
    }
    String lucassUser = output.replace("Lucas", "Lucass");
    String correctOutput = output + lucassUser;
    app.masterList.insert("Lucass");
    if (!app.masterList.toString().equals(correctOutput)) {
      fail("The toString() output after adding Lucass was incorrect");
    }
 }
  
  @Test
  void testChangeCurrentUser() {
    FriendFinder app = new FriendFinder();
    Person p = new Person("Adam", "Dude", "Imaginary Lane", null);
    app.masterList.insert(p);
    app.updateCurrentUser("Adam");
    //Make sure curUser is the one person in the tree
    if(!app.curUser.equals(p)) {
      fail("The curUser is not Adam it is:" + app.curUser.getFullName());
    }
//    System.out.println(app.masterList);
//    System.out.println(p);
    String adam = app.masterList.toString();
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
    if(!app.curUser.equals(p)) {
      fail("The curUser is not Sherly it is:" + app.curUser.getFullName());
    }
    String sherley = adam.replace("Adam", "Sherley").replace("Imaginary Lane", "Imaginary Drive").replace("Dude", "Lady");
//    System.out.println(app.masterList.toString());
//    System.out.println();
//    System.out.println(sherley);
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
  
  @Test
  void testMutualFriendCheck() {
    FriendFinderRBTree adamTree = new FriendFinderRBTree();
    adamTree.insert("Sherley Timple");
    FriendFinderRBTree sherleyTree = new FriendFinderRBTree();
    sherleyTree.insert("Adam Shingles");
    Person adam = new Person("Adam Shingles", "Dude", "Imaginary Lane", adamTree);
    Person sherley = new Person("Sherley Timple", "Lady", "Imaginary Drive", sherleyTree);
    //Test non existing person becoming mutual
    Person rando = new Person("rando", "Dude", "Imaginary Imaginary", adamTree);
    //Test existing person becoming mutual
    Person crush = new Person("David", "Dude", "Imaginary Avenue", adamTree);
    FriendFinder app = new FriendFinder();
    app.masterList.insert(adam);
    app.masterList.insert(sherley);
    app.masterList.insert(crush);
 //   System.out.println(app.masterList.toString());
    app.updateCurrentUser("Adam Shingles");
    if(!app.curUser.checkForMutualFriend(sherley)) {
      fail("Adam and Sherley are mutual bestfriends");
    }
    if(app.curUser.checkForMutualFriend(rando)) {
      fail("Rando is not mutual friends with Adam");
    }
    app.updateCurrentUser("Sherley Timple");
    if(!app.curUser.checkForMutualFriend(adam)) {
      fail("Sherley and Adam are mutual bestfriends");
    }
    if(app.curUser.checkForMutualFriend(rando)) {
      fail("Rando is not mutual friends with Sherley");
    }
    app.curUser.insertFriend(rando);
 //   System.out.println(app.masterList.toString());
    if(!app.curUser.checkForMutualFriend(rando)) {
      fail("Rando should be a mutual friends with Sherley after using insertFriend");
    }
    if(app.curUser.checkForMutualFriend(crush)) {
      fail("Daid should not be mutual friends with Sherley before using insertFriend");
    }
    app.curUser.insertFriend(crush);
    if(!app.curUser.checkForMutualFriend(crush)) {
      fail("David should be a mutual friends with Sherley after using insertFriend");
    }
    app.curUser.setBio("Woman");
    app.curUser.setResidency("Kansas");
    if(!app.curUser.getBio().equals("Woman")) {
      fail("curUsers (Sherley) should have the bio 'Woman' after using setBio() but instead it has: " + app.curUser.getBio());
    }
    if(!app.curUser.getResidency().equals("Kansas")) {
      fail("curUsers (Sherley) should have the residency 'Kansas' after using setResidency() but instead it has: " + app.curUser.getResidency());
    }
    app.curUser.saveCurrentUser();
//    System.out.println(app.curUser);
    app.updateCurrentUser("Adam Shingles");
    app.updateCurrentUser("Sherley Timple");
//    System.out.println(app.curUser);
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
}