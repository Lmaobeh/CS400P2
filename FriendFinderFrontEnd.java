// --== CS400 File Header Information ==--
// Name: Pieran Robert
// Email: probert@wisc.edu 
// Team: NE
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader:
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
*This is a driver class that allows a user to access the FriendFinder Applications
*different users can log in and create their own Red and Black Trees filled with their friends
*All of these different trees are stored in one master tree
*/
public class FriendFinderFrontEnd {

	public static void main(String [] args) {
		Scanner reader = new Scanner(System.in);
		String name = "";
		boolean run = true;
		System.out.println("Welcome to Friend Finder\nPlease Enter your name: ");
		name = reader.nextLine();
		FriendFinder tree = null;
		try {
		tree = new FriendFinder("FriendFinder.ser");
		tree.updateCurrentUser(name);
		} catch(FileNotFoundException e) {
		  System.out.println("Could not retrive the file to load the data");
		} catch (Exception e) {
		  System.out.println("Another error occurred... instantiating a blank application");
		  tree = new FriendFinder();
		}
		

		//List of commands that user can choose from that will run continuously
		while(run) {
	
		System.out.println("Thanks " + tree.curUser.getFullName() + ", please input a command"); //prints instructions for users
		System.out.println("Enter p to view all users \nEnter v to view your profile \nEnter f to modify your personal information"
		        + "\nEnter vf to view a persons profile"
		        + "\nEnter c to change or add a new friend "
		        +  "\nEnter x to check for a mutual friend "
		        + "\nEnter u to change the user"
		        + "\nEnter q to end program"
		        + "\nEnter q! to exit and save");
		 switch(reader.next()) {
		//Prints out master tree
		 case "p": System.out.println(tree.masterList);
		 break;
		//Prints out the current users profile
		 case "v": System.out.println(tree.curUser);
		 break;
		//Allows users to change their personal information
		 case "f": 
		 boolean run2 = true;
		 do {
		 System.out.println("What information would you like to change:");
	         System.out.print("Enter r to change residency" +
	         "\nEnter b to change bio");
		 switch(reader.next()) {
		//changes residency
		 case "r":	System.out.println("Enter new Residency: ");
		     reader.nextLine();
			 tree.curUser.setResidency(reader.nextLine());
			 run2 = false;
			 break;
		//changes personal bio
		 case "b": System.out.println("Enter new Bio ");
		     reader.nextLine();
			 tree.curUser.setBio(reader.nextLine());
			 run2 = false ;
			 break;
		 default:
		   System.out.println("Please enter a valid command");
		   
		   }
		 } while (run2);
		   break;
		 //allows current user to view a friends profile
		 case "vf":
		   System.out.println("Enter the person's name");
		   reader.nextLine();
		   String personName = reader.nextLine();
		   if(tree.masterList.contains(personName)) {
		     System.out.println( tree.new CurrentUser(tree.masterList.lookup(personName)));
		   }
		   else {
		     System.out.println("this person doesn't exist");
		   }
		   break;
		//Allows a user to add a new friends
		 case "c": System.out.println("Add a new friend: \nWhat is their name?");
		 reader.nextLine();
		 String newFriend = reader.nextLine();
		 if (tree.masterList.contains(newFriend)) {
		   if (newFriend.equals(tree.curUser.getFullName()) || !tree.curUser.insertFriend(tree.masterList.lookup(newFriend)) ) {
		     System.out.println(newFriend + " is already your friend"); //if friend is already added
		   }
		 }
		//if friend does not already have a profile a new profile is created
		 else {
		 Person newPerson = new Person(newFriend);
		 tree.masterList.insert(newPerson);
		 tree.curUser.insertFriend(newPerson);
		 }
		 break;
		 //Allows a user to check to see if they and another user are mutual friends
		 case "x": System.out.println("For what friend would you like to check mutuality?");
		 reader.nextLine();
		 Person p = tree.masterList.lookup(reader.nextLine());
		 if (p == null) {
		   System.out.println("This person does not exist");
		   break;
		 }
		 String friend = p.getFullName();
		 if(tree.curUser.checkForMutualFriend(tree.masterList.lookup(friend)))
			 System.out.println(friend + " and you are mutual friends!");
		 else {System.out.println("Sadly, " + friend + " and you are not mutual friends.");}
		 break;
		//Allows the changing of users
		 case "u":
		   System.out.println("Enter the name of the new user: ");
		   reader.nextLine();
		   String newUser = reader.nextLine();
		   tree.updateCurrentUser(newUser);
		   break;
		//Lets a user quit the application
		 case "q": run = false;
		 break;
		//Lets the user quit and save the application
		 case "q!" : run = false;
		 try {
		 tree.exitAndSave("FriendFinder.ser");
		 } catch (FileNotFoundException e) {
		   System.out.println("The file to save to was not found");
		 } catch (Exception e) {
		   System.out.println("An error occured while writing to the file");
		 }
		 break;
		 default:
		   System.out.println("Please enter a valid command");
		 }
		
		}
	
	}
	
}
