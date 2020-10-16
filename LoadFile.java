import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LoadFile {
	HashTable<Person> network = new HashTable<>();
	


	public static void main(String[] args){

	File lead = new File("input.txt");
	//System.out.println(lead);
	try {
		Scanner tokens = new Scanner(lead);
		System.out.print(tokens.next());
		while (tokens.hasNextLine()){
			String[] add = tokens.nextLine().split(",");
			Person person = new Person(add[0]);
			Person.setBio(add[1]);
			person.setResidency(add[2]);
			String[] friends = add[3].split(",");
			for (String f : friends){
				Person frend = new Person(f);
				person.insertFriend(frend);
				if (!network.contains(frend))//comment out next two if we dont want
					//to add a persons friend to the network
					network.insert(frend);
			}
			network.insert(person);
				
		}

	}
	catch (Exception e){
	System.out.print("YOU Fd UP");
	}
	
	}






}
