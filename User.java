/** Represents a user in a social network. A user is characterized by a name,
 *  a list of user names that s/he follows, and the list's size. */
 public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; // fixed-size array for storing followees
        fCount = 0;                      // initial number of followees
    }

    /** Creates a user with some followees. The only purpose of this constructor is 
     *  to allow testing the toString and follows methods, before implementing other methods. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /** Returns the name of this user. */
    public String getName() {
        return name;
    }

    /** Returns the follows array. */
    public String[] getfFollows() {
        return follows;
    }

    /** Returns the number of users that this user follows. */
    public int getfCount() {
        return fCount;
    }
    // keren, john, ron, linda, null, null
    // john, ron, linda, null, null, null
    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        //i use a for loop to go over the user's followers array,
        //if i find the name in the array i return true
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }
    /** Makes this user follow the given name. If successful, returns true. 
     *  If this user already follows the given name, or if the follows list is full, does nothing and returns false; */
    public boolean addFollowee(String name) {

        //i check if the user is already in the array, if so i return false
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
                return false;
            }
        }

        // i check if the number of followers is at the maximum number,
        // if not, i add the given user to the followers array and raise 
        // the fcount by 1
        if(fCount != maxfCount) {
            follows[fCount] = name;
            fCount++;
            return true;
        }
        return false;
    }

    /** Removes the given name from the follows list of this user. If successful, returns true.
     *  If the name is not in the list, does nothing and returns false. */
    public boolean removeFollowee(String name) {
        int index = 0;
        Boolean remove = false;

        // i look for the index of the given name in the array and store 
        // it in a variable called index
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
                index = i;
                remove = true;
                break;
            }
        }

        // if the name is not in the array i return false;
        if (remove == false){
            return false;
        }

        // starting from the index, i move each name backwards one step
        for (int i = index; i < fCount-1; i++) {
            follows[i] = follows[i+1];
        }
        
        // i change the last name to null and reduce the fcount by one
        follows[fCount - 1] = null;
        fCount--;

        // i check to see if i removed the name properly and if not, 
        // i return false
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
                remove = false;
                break;
            }
        }
        return remove;
    }

    /** Counts the number of users that both this user and the other user follow.
    /*  Notice: This is the size of the intersection of the two follows lists. */
    public int countMutual(User other) {
       int count  = 0;
       // i count the number of users this account follows that the  
       //the other account follows also and i return the count;
       for (int j = 0; j < other.fCount; j++) { 
            if (this.follows(other.follows[j])){
                count++;
            }
        }
        return count;
    }

    /** Checks is this user is a friend of the other user.
     *  (if two users follow each other, they are said to be "friends.") */
    public boolean isFriendOf(User other) {
        // i check if this user and the other user follow each other
        // and return true if they both do and falsr otherwise
        if (this.follows(other.name)&&(other.follows(this.name))){
            return true;
        }
        return false;
    }
    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}
