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
public class Person implements Comparable<Person>, Serializable {
  /**
   *Used for serialization
   */
  private static final long serialVersionUID = 42;
  private String fullName;
  private String residency;
  private String bio;
  private FriendFinderRBTree friendsList;

  /**
   * Makes a new person object with a name
   * 
   * @param fullName the persons name
   */
  public Person(String fullName) {
    this.fullName = fullName;
    this.friendsList = new FriendFinderRBTree();
  }

  /**
   * Makes a person with the three string feilds
   * 
   * @param fullName  Name
   * @param residency where they live
   * @param bio       A brief bio
   */
  public Person(String fullName, String residency, String bio) {
    this.fullName = fullName;
    this.residency = residency;
    this.bio = bio;
    this.friendsList = new FriendFinderRBTree();
  }

  /**
   * Makes a person with every feild. only used by the current user class
   * 
   * @param fullName
   * @param residency
   * @param bio
   * @param friendList
   */
  public Person(String fullName, String residency, String bio, FriendFinderRBTree friendList) {
    this.fullName = fullName;
    this.residency = residency;
    this.bio = bio;
    this.friendsList = friendList;


  }



  /**
   * Checks if this person has the following friend
   * 
   * @param p the friend to check for
   * @return returns true if they do and false if they dont
   */
  public boolean hasFriend(Person p) {
    return friendsList.contains(p.getFullName());
  }



  /**
   * Inserts a person object as a friend into this person's friends list
   * 
   * @param p The person to add
   * @return true if added, false if not
   */
  public boolean insertFriend(Person p) {
    try {
      return friendsList.insert(p);
    } catch (NullPointerException e) {
      return false;
    }

  }

  /**
   * @return the fullName
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * @param fullName the fullName to set
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * @return the residency
   */
  public String getResidency() {
    return residency;
  }

  /**
   * @param residency the residency to set
   */
  public void setResidency(String residency) {
    this.residency = residency;
  }

  /**
   * @return the bio
   */
  public String getBio() {
    return bio;
  }

  /**
   * @param bio the bio to set
   */
  public void setBio(String bio) {
    this.bio = bio;
  }


  /**
   * Compares two people
   * 
   * @param o the person to compare
   * @return 1 if the person is greater than o. 0 if they are the same and -1 when they are less
   *         (based on the name alphabetically)
   */
  public int compareTo(Person o) {
    return this.fullName.compareTo(o.fullName);
  }

  /**
   * checks if they are equal
   * 
   * @param o the person to compare to this person
   * @return true if the names are equal false if otherwise
   */
  public boolean equals(Person o) {
    return this.fullName.equals(o.fullName);
  }

  /**
   * Only used for the current user class
   * 
   * @return The friends list
   */
  public FriendFinderRBTree getFriendsList() {
    return this.friendsList;
  }

  /**
   * Sets the firends lit. Only used for testing.
   * 
   * @param friendsList the friends list to add
   */
  public void setFriendsList(FriendFinderRBTree friendsList) {
    this.friendsList = friendsList;
  }

  /**
   * Updates the changes made by current used for this person
   * 
   * @param c the current used
   */
  public void updateChanges(FriendFinder.CurrentUser c) {
    this.setFullName(c.getFullName());
    this.setResidency(c.getResidency());
    this.setBio(c.getBio());
    this.setFriendsList(c.getFriendsList());
  }

  /**
   * To string method for this person object
   */
  public String toString() {
    String str = "";
    str += "[ Full Name: " + (this.fullName == null ? " " : this.fullName) + "\n";
    str += "  Residency: " + (this.residency == null ? " " : this.residency) + "\n";
    str += "  Bio: " + (this.bio == null ? " " : this.bio) + " ]\n";


    return str;
  }
}
