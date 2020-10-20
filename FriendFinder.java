// --== CS400 File Header Information ==--
// Name: Lucas Nguyen
// Email: lfnguyen@wisc.edu
// Team: NE
// TA: Daniel Finer
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.io.*;

/**
 * @author Lucas
 *
 */
public class FriendFinder {


  class CurrentUser extends Person {


    /**
     * Instantiates a current user with the fields of the person param
     * 
     * @param p
     */
    public CurrentUser(Person p) {
      super(p.getFullName(), p.getResidency(), p.getBio(), p.getFriendsList());
    }

    /**
     * Checks if this current user is freinds with the person P ** tests that returns true if they
     * are mutal or false if they arent
     * 
     * @param p
     * @return
     */
    public boolean checkForMutualFriend(Person p) {
      return this.hasFriend(p) && p.hasFriend(this);
    }

    /**
     * Inserts a friend and sets mutual friend status tests that a person that is already in the
     * application is properly loaded into the current users friend list and if they are not in the
     * application a profile is made Tests throwing an exception if the key already exists
     *
     */
    @Override
    public boolean insertFriend(Person p) {
      // gets the person to from the master list
      if (masterList.contains(p.getFullName())) {
        p = masterList.lookup(p.getFullName());
        return super.insertFriend(p);
      }
      // adds the new person to master list
      masterList.insert(p);

      return super.insertFriend(p);
    }

    /**
     * Prints the current use to string
     *
     */
    @Override
    public String toString() {
      String str = "";
      str += "[ Full Name: " + (this.getFullName() == null ? " " : this.getFullName()) + "\n";
      str += "Residency: " + (this.getResidency() == null ? " " : this.getResidency()) + "\n";
      str += "Bio: " + (this.getBio() == null ? " " : this.getBio()) + "\n";
      str += "Friends List: "
          + (this.getFriendsList() == null ? "[]" : this.getFriendsList().toString()) + " ]\n";
      return str;
    }

    /**
     * writes the possibly saved data to the list. ** test that this saves changed data to the
     * master list
     */
    public void saveCurrentUser() {
      Person p = masterList.lookup(this.getFullName());
      p.updateChanges(this);

    }

    /**
     * Sets the residency and saves it to the person object
     */
    @Override
    public void setResidency(String residency) {
      super.setResidency(residency);
      this.saveCurrentUser();
    }

    /**
     * Sets the bio and saves it to the perosn object
     */
    @Override
    public void setBio(String bio) {
      super.setBio(bio);
      this.saveCurrentUser();
    }



  }


  public FriendFinderRBTree masterList;
  public CurrentUser curUser;

  /**
   * Loads from a saved file
   * 
   * @param savedData
   */
  @SuppressWarnings("unchecked")
  public FriendFinder(String fileName)
      throws FileNotFoundException, IOException, ClassNotFoundException {
    FileInputStream fileStream = new FileInputStream(fileName);
    ObjectInputStream os = null;
    Object o = null;
    try {
      os = new ObjectInputStream(fileStream);
      // catches EOFException if the serialization file is empty
      o = os.readObject();
      // catches EOFExeption when reading a null object
      // Makes a new master list.
    } catch (EOFException e) {
      masterList = new FriendFinderRBTree();
      fileStream.close();
      return;
    }
    // gets object and casts it down to a FriendFinderRBTree

    FriendFinderRBTree tree = (FriendFinderRBTree) o;
    // instantiates a new tree if it is null
    if (o == null) {
      masterList = new FriendFinderRBTree();
      fileStream.close();
      os.close();
      return;
    }
    // points master list to saved tree
    masterList = tree;
    os.close();
    fileStream.close();

  }

  /**
   * Makes a new friend finder
   */
  public FriendFinder() {
    masterList = new FriendFinderRBTree();
  }

  /**
   * Updates the current user, by getting them from the master list or by making a new one. ** test
   * getting the persons profile or making a new one, also the existance of a taken profile
   * 
   * @param name Name of there new user
   */
  public void updateCurrentUser(String name) {

    if (this.masterList.contains(name)) {
      curUser = new CurrentUser(this.masterList.lookup(name));
      return;
    }

    curUser = new CurrentUser(new Person(name));
    masterList.insert(new Person(name));
  }


  /**
   * Exits and saves the master list to a file specified by file name
   * 
   * @param fileName the file name to save to
   * @throws FileNotFoundException if the file is not found
   * @throws IOException           if there is an issue with writing the object
   */
  public void exitAndSave(String fileName) throws FileNotFoundException, IOException {
    FileOutputStream fileStream = new FileOutputStream(fileName);
    ObjectOutputStream os = new ObjectOutputStream(fileStream);
    if (curUser != null) {
      curUser.saveCurrentUser();
    }
    os.writeObject(masterList);
    os.close();
    fileStream.close();
  }



}
