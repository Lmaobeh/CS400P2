
public class Person implements Comparable<Person>{
  private String fullName;
  private String residency;
  private boolean isMutualFriend;

  private String bio;
  private RedBlackTree<Person> friendsList;
  
  public Person(String fullName) {
    this.fullName = fullName;
  }
  
  public Person(String fullName, String residencey,String bio) {
    this.fullName = fullName;
    this.residency = residency;
    this.bio = bio;
  }
  
  public Person(String fullName, String residencey,String bio, 
      RedBlackTree<Person> friendList, boolean isMutalFriend) {
    this.fullName = fullName;
    this.residency = residency;
    this.bio = bio;
    this.friendsList = friendList;

    this.isMutualFriend = true;
  }
  
  
  
  public boolean hasFriend(Person p) {
    return friendsList.contains(p);
  }
  
  public void setMutualFriend(boolean isMutualFriend) {
    this.isMutualFriend = isMutualFriend; 
  }
 
  
  public boolean insertFriend(Person p) {
    try {
     friendsList.insert(p);
    } catch (IllegalArgumentException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
    return true;
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


  public int compareTo(Person o) {
    return this.fullName.compareTo(o.fullName);
  }
  
  public boolean equals(Person o) {
    return this.fullName.equals(o.fullName);
  }
  
  public RedBlackTree<Person> getFriendsList(){
    return this.friendsList;
  }
  
  public String toString() {
    String str = "";
    str += "[ Full Name: " + (this.fullName == null ? "null" : this.fullName) +"\n";
    str += "Residency: " + (this.residency == null ? "null" : this.residency) + "\n";
    str += "Bio: " + (this.bio == null ? "null" : this.bio) +"\n";
    str += "Mutal friend? " + (this.isMutualFriend ? "Yes" : "No") + " ]\n";
    
    return str;
  }
  
  
  
  public static void main(String[] args) {
    Person p = new Person("Lucas");
    System.out.println(p.toString());

  }

}
