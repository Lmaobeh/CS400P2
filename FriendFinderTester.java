// --== CS400 File Header Information ==--
// Name: Tricia Nazareth
// Email: tnazareth@wisc.edu
// Team: NE
// TA: Daniel Finer
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FriendFinderTester {

	/**
	   * Tests that checkForMutualFriend method to make sure it works correctly
	   */
	@Test
	void testCheckForMutualFriend() {
		FriendFinderRBTree taylorTree = new FriendFinderRBTree();
	    taylorTree.insert("Bryan Waters");
	    FriendFinderRBTree bryanTree = new FriendFinderRBTree();
	    bryanTree.insert("Taylor Towers");
	    Person taylor = new Person("Taylor Towers", "Lady", "Madison Avenue", taylorTree);
	    Person bryan = new Person("Bryan Waters", "Dude", "Lakeshore Path", bryanTree);
	    //Kyle becomes taylors mutual friend
	    Person kyle = new Person("kyle", "Dude", "Imaginary Avenue", taylorTree);
	    FriendFinder app = new FriendFinder();
	    app.masterList.insert(taylor);
	    app.masterList.insert(bryan);
	    app.masterList.insert(kyle); 
	    app.updateCurrentUser("Taylor Towers"); //makes Taylor the current user
	    if(!app.curUser.checkForMutualFriend(bryan)) {
	      fail("Taylor and Bryan mutual bestfriends");
	    }
	    if(app.curUser.checkForMutualFriend(kyle)) {
	      fail("Kyle is mutual friens with Taylor");
	    }
	    app.updateCurrentUser("Bryan Waters"); //updates the current user to Bryan
	    if(!app.curUser.checkForMutualFriend(taylor)) {
	      fail("Bryan and Taylor are mutual bestfriends");
	    }
	    if(app.curUser.checkForMutualFriend(kyle)) {
	      fail("Kyle is not mutual friends with Taylor");
	    }
	    app.curUser.insertFriend(kyle);
	    if(!app.curUser.checkForMutualFriend(kyle)) {
	      fail("kyle can't be mutual friends with himself");
	    }
	    if(app.curUser.checkForMutualFriend(bryan)) {
	      fail("Kyle is not mutual friends with Bryan, add as friends using inserFriend");
	    }
	    app.updateCurrentUser("Taylor Towers"); //makes Taylor the current user
	    app.curUser.setBio("Woman");
	    app.curUser.setResidency("Texas");
	    if(!app.curUser.getBio().equals("Woman")) {
	      fail("Taylor should be a woman after using setBio()");
	    }
	    if(!app.curUser.getResidency().equals("Texas")) {
	      fail("Taylor should be from Texas after using setResidency() but instead it has: " + app.curUser.getResidency());
	    }
	    app.curUser.saveCurrentUser();
	}
	
	/**
	   * Tests the insert friend method to check if the person is properly added as a mutual friend
	   *fails if the friend is already added
	   */
	@Test
	void testInsertFriend() { 
		 FriendFinder app = new FriendFinder();
		    app.masterList.insert("Tricia"); //adds friend
		    String result = app.masterList.toString();
		    if (!app.masterList.toString().equals(result)) {
		      fail("Friend has not been properly added");
		    }
		    app.masterList.insert("Tricia"); //same friend cannot be added twice
		    String wrongResult = result + result;
		    if (app.masterList.toString().equals(wrongResult)) {
		        fail("Tricia has been added to the friend tree twice.");
		      }
	}
		   
	
	 /**
	   * Tests the SaveCurrentUser method to make sure the user is properly saved in the application.
	   */
	@Test
	void testSaveCurrentUser() {
		FriendFinder app = new FriendFinder();
	    	Person friend1 = new Person("Taylor", "Lady", "Madison Avenue", null);
	    	app.masterList.insert(friend1);
	    	app.saveCurrentUser("Taylor");
	    	//checks if the current user is saved
	    	if(!app.curUser.equals(friend1)) {
	    		fail("Taylor is not saved as the current user");
	    }
	    	 app.curUser.setBio("Woman");
	 	    app.curUser.setResidency("Texas");
	 	   if(!app.curUser.getBio().equals("Woman")) {
	 	      fail("Taylor should be a woman after using setBio()");
	 	    }
	 	    if(!app.curUser.getResidency().equals("Texas")) {
	 	      fail("Taylor should be from Texas after using setResidency() but instead it has: " + app.curUser.getResidency());
	 	    }
	    	
	}
	
	 /**
	   * Tests the updateCurrentUser method to check if the user is properly updated from the master list.
	   */
	@Test
	void testUpdateCurrentUser() {
		FriendFinder app = new FriendFinder();
			Person friend1 = new Person("Taylor", "Lady", "Madison Avenue", null);
			app.masterList.insert(friend1);
			app.updateCurrentUser("Taylor");
	    	//checks if the current user is updated
	    	if(!app.curUser.equals(friend1)) {
		      fail(app.curUser.getFullName() + "is the current user, should be Taylor.");
		    }
		    String taylor = app.masterList.toString();
		    if((!app.masterList.toString().equals(friend1.toString())) || (app.curUser.getFriendsList() != null)){
		      fail("The friend list doesn't match the person added.");
		    }
		    if((!app.curUser.getFullName().equals(friend1.getFullName())) || (!app.curUser.getResidency().equals(friend1.getResidency()))) {
		      fail("The name or location of the current node does not match Taylor");
		    }
		    if(!app.curUser.getBio().equals(friend1.getBio())) {
		      fail("Incorrect bio for current user, Taylor");
		    }
		   
		    
	}

}
