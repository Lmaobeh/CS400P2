import java.io.File;

public class FriendFinder {

  private class CurrentUser extends Person  {
    
   
    /**
     * Instantiates a current user with the fields of the person param
     * @param p
     */
    public CurrentUser(Person p){
       super(p.getFullName(), p.getResidency(), p.getBio(), p.getFriendsList(), 
           false);  
     }
     /**
      * Checks if this current user is freinds with the person P
     * @param p
     * @return
     */
    private boolean checkForMutualFriend(Person p) {
       return this.hasFriend(p) && p.hasFriend(this);
     }
     
     /**
     *Inserts a friend and sets mutual friend status
     */
    @Override
     public boolean insertFriend(Person p) {
       p.setMutualFriend(this.checkForMutualFriend(p)); 
       return this.insertFriend(p);
     }
     
     /**
     *Prints the current use to string
     */
    @Override
     public String toString() {
       String str = "";
       str += "[ Full Name: " + (this.getFullName() == null ? " " : this.getFullName()) +"\n";
       str += "Residency: " + (this.getResidency() == null ? " " : this.getResidency()) + "\n";
       str += "Bio: " + (this.getBio() == null ? " " : this.getBio()) + "\n";
       str += "Friends List: " + (this.getFriendsList() == null ? "[]" : this.getFriendsList().toString()) + " ]\n";
       return str;
     }
     
    
  }
   
  
  private RedBlackTree<Person> masterList;
  private CurrentUser curUser;
  
  /**
   * Loads from a saved file
   * @param savedData
   */
  public FriendFinder(File savedData){
    
  }
  
  /**
   * Makes a new friend finder
   */
  public FriendFinder() {
    masterList = new RedBlackTree<Person>();
  }
  /**
   * Updates the current user, by getting them from the master list or by
   * making a new one.
   * @param name Name of there new user
   */
  public void updateCurrentUser(String name) {
    
    //if(this.masterList.contains(name){
      //currUser = new CurrentUser(this.masterList.lookup(name));
    //}
     curUser = new CurrentUser(new Person(name));
    masterList.insert(curUser);   
  }
  public static void main(String[] args) {
    FriendFinder f = new FriendFinder();
    f.updateCurrentUser("Lucas");
    System.out.println(f.curUser);
  }
  
 
  
}
