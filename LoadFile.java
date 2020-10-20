import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
// --== CS400 File Header Information ==--
// Name: <Sam Hossain>
// Email: <sdhossain@wisc.edu>
// Team: <NE>
// TA: <Daniel>
// Lecturer: <Florian>
// Notes to Grader: <optional extra notes>

public class LoadFile{
	
	public static void main(String[] args){

	FriendFinderRBTree network = new FriendFinderRBTree();
	//read in file with the persons to be inserted into the tree
	File lead = new File("input.txt");
	try {
		Scanner tokens = new Scanner(lead);
		while (tokens.hasNextLine()){
			//split each line into a name, bio, location, and list of friends
			String[] add = tokens.nextLine().split(",");
			//lookup person in our network. if Present, add
			Person person = network.lookup(add[0]);
			if (person == null) //if person doesn't exist in our tree, create new
				person = new Person(add[0]);
			//add the persons attributes, either to the new object or the existing one
			person.setBio(add[1]);
			person.setResidency(add[2]);
			String[] friends = add[3].split(",");
			for (String f : friends){ //add person's friends
				//if already present in master network, dont need to create new object
				if (network.contains(f))
					person.insertFriend(network.lookup(f));
				else {
					Person toAdd = new Person(f);
					person.insertFriend(toAdd);
				}
			}
			//add the person back to our master network
			if (!network.contains(person.getFullName()))
				network.insert(person);
		}
		System.out.print(network.printAllInOrder());
	}


	
	catch (Exception e){
		e.printStackTrace();
		}
	
	}
}







