
/**
 * @author Lucas
 *
 */
public interface FriendFinderRBTreeInterface {
   /**
    * Inserts a person into the red black tree, the person objects name is the 
    * key. No duplicate keys
   * @param p the person object to insert
   * @return true if added, false if not added
   */
  public boolean insert(Person p);
   /**
    * Insets a person based on the name. This will make a person object and 
    * instantiate with the name
   * @param fullName the person objects name
   * @return true if added, false if already exists
   */
  public boolean insert(String fullName);
   /**
   * Prints the people in alphabetical order
   */
  public void printAllInOrder(); 
   /**
   * Prints the people in level order
   */
  public void printAllLevelOrder();
   /**
    * takes a name and checks if the person exists
   * @param fullName the name of the person
   * @return
   */
  public boolean contains(String fullName);
   /**
    * 
   * @return true if empty, false if not empty
   */
  public boolean isEmpty();
   /**
    * Looks up and returns the person object based on the name
   * @param fullName the name of the person
   * @return person object
   */
  public Person lookup(String fullName);
   /**
    * Gets the string representation of the person object based on the name
   * @param fullName the name of the person
   * @return string representaion of the person
   */
  public String getPersonData(String fullName);
}
