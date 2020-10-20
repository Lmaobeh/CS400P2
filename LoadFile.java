import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class LoadFile{
	
	public static void main(String[] args){

	FriendFinderRBTree network = new FriendFinderRBTree();

	File lead = new File("input.txt");
	//System.out.println(lead);
	try {
		Scanner tokens = new Scanner(lead);
		while (tokens.hasNextLine()){
			String[] add = tokens.nextLine().split(",");
			//lookup person in our network. if Present, add
			Person person = network.lookup(add[0]);
			if (person == null) //if person doesn't exist in our tree, create new
				person = new Person(add[0]);
			person.setBio(add[1]);
			person.setResidency(add[2]);
			String[] friends = add[3].split(",");
			for (String f : friends){ //add persons friends
				if (network.contains(f))
					person.insertFriend(network.lookup(f));
				else {
					Person toAdd = new Person(f);
					person.insertFriend(toAdd);
				}
			}
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







