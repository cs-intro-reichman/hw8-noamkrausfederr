/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        String newName = "" + name.charAt(0);
        newName = newName.toUpperCase();
        newName += name.substring(1,name.length());
        for(int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(newName)) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (name == null) {
            return false;
        }
        String newName = "" + name.charAt(0);
        newName = newName.toUpperCase();
        newName += name.substring(1,name.length());
        //if the network is full i return false
        if (userCount == (users.length-1)) {
            return false;
        }
        // if the name is already in the network i return false
        for(int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(newName)) {
                return false;
            }
        }
        // i add 
        User u = new User(newName);
        users[userCount] = u;
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if((name1 == null) || (name2 == null)) {
            return false;
        }

        String newName1 = "" + name1.charAt(0);
        newName1 = newName1.toUpperCase();
        newName1 += name1.substring(1,name1.length());
        String newName2 = "" + name2.charAt(0);
        newName2 = newName2.toUpperCase();
        newName2 += name2.substring(1,name2.length());

        // checks if the the first user is in the network
        boolean inNetwork = false;
        for(int i = 0; i < userCount; i++) {
            // if the user is in the users array, the boolean variable
            // changes to true
            if (users[i].getName().equals(newName1)) {
                inNetwork = true;
                break;
            } 
        }
        if (inNetwork == false) {
            return false;
        }
        //checks if thr next user is in the user array
        inNetwork = false;
        for(int i = 0; i < userCount; i++) {
                if (users[i].getName().equals(newName2)) {
                    inNetwork = true;
                break;
                } 
        }
        if (inNetwork == false) {
            return false;
        }
        //checks if the two users are the same person
        if(newName1.equals(newName2)) {
            return false;
        } 
        //checks if the second user already follows the first user
        else if (getUser(newName1).follows(newName2)) { 
            return false;
        }
        //executes the addfollowee method
        getUser(newName1).addFollowee(newName2);
        return inNetwork;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int max = 0;
        int index = 0;
        //if the user has no followers, i store the index of the first user with no followers
        if (getUser(name).getfCount() == 0) {
            for (int i = 0; i < userCount; i++) {
                if(users[i].getName() == name) continue;
                if (users[i].getfCount() == 0) {
                    index = i;
                    break;
                }
            }
        }
        // if the user has atleast one follower, i store the first user's index that has the maximum 
        // number of mutual followers
        else {
            for(int i = 0; i < userCount; i++) {
                if (users[i].getName().equals(name)) continue;
                int n = getUser(name).countMutual(users[i]);
                if (n > max) {
                    max = n;
                    index = i;
                }
            }
        }
        String recUser = users[index].getName();
        return recUser;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }
        int max = followeeCount(users[0].getName());
        System.out.println(max);
        int index = 0;
        for(int i = 1; i < userCount; i++) {
            if (followeeCount(users[i].getName()) > max) {
                max = followeeCount(users[i].getName());
                index = i;
            }
        }
        return users[index].getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        for(int i = 0; i < userCount; i++) {
            User u = users[i];
            for (int j = 0; j < u.getfCount(); j++) {
                if (u.getfFollows()[j].equals(name)){
                    count++;
                    j = u.getfFollows().length;
                }
            } 
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        if (userCount == 0){
            return "Network:";
        }
        String ans = "Network:" + "\n";
        for (int i = 0; i < userCount - 1; i++) {
            ans = ans + users[i].toString() + "\n";
        }
        ans += users[userCount - 1].toString();
        ans.trim();
        return ans;
    }
}
