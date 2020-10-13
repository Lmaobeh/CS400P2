
public class CurrentUser extends Person  {
 
  public CurrentUser(Person p){
     super(p.getFullName(), p.getResidency(), p.getBio(), p.getFriendsList(), true,
         false);  
   }
   private boolean checkForMutualFriend(Person p) {
     return this.hasFriend(p) && p.hasFriend(this);
   }
   
   @Override
   public boolean insertFriend(Person p) {
     p.setMutualFriend(this.checkForMutualFriend(p)); 
     return this.insertFriend(p);
   }
   
   @Override
   public String toString() {
     String str = "";
     str += "[ Full Name: " + (this.getFullName() == null ? " " : this.getFullName()) +"\n";
     str += "Residency: " + (this.getResidency() == null ? " " : this.getResidency()) + "\n";
     str += "Bio: " + (this.getBio() == null ? " " : this.getBio()) + "\n";
     str += "Friends List: " + (this.getFriendsList() == null ? "[]" : this.getFriendsList().toString()) + " ]\n";
     return str;
   }
   
   
   
   public static void main(String[] args) {
     Person p = new Person("Lucas");
     CurrentUser cur = new CurrentUser(p);
     System.out.println(cur.toString());
    
     
     
   }
}
