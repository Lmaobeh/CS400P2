
public class Person implements Comparable<Person>{
  private String fullName;
  private String residency;
 

  private String bio;
  private RedBlackTree<Person> friendsList;
  
  public Person(String fullName) {
    this.fullName = fullName;
    this.friendsList = new RedBlackTree<Person>();
  }
  
  public Person(String fullName, String residencey,String bio) {
    this.fullName = fullName;
    this.residency = residency;
    this.bio = bio;
    this.friendsList = new RedBlackTree<Person>();
  }
  
  public Person(String fullName, String residencey,String bio, 
      RedBlackTree<Person> friendList) {
    this.fullName = fullName;
    this.residency = residency;
    this.bio = bio;
    this.friendsList = friendList;

   
  }
  
  
  
  public boolean hasFriend(Person p) {
    return friendsList.contains(p);
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
    str += "[ Full Name: " + (this.fullName == null ? " " : this.fullName) +"\n";
    str += "  Residency: " + (this.residency == null ? " " : this.residency) + "\n";
    str += "  Bio: " + (this.bio == null ? "null" : this.bio) +" ]\n";
    
    
    return str;
  }
  
  
  
  public static void main(String[] args) {
    Person p = new Person("Lucas");
    p.insertFriend(new Person("Lucas 2"));
    p.insertFriend(new Person("Lucas 3"));
    System.out.println(p.toString());
    System.out.println(p.getFriendsList().toString());

  }

}
