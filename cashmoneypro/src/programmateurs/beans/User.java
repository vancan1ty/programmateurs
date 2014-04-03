package programmateurs.beans;

import java.util.ArrayList;

/**
 * The User class represents a user of the mobile application with an ID that
 * is used for database retrieval,username, password (called passhash), a first
 * and last name, and an email address. The user also has a list of categories 
 * associated with his/her accounts. This class is basically just an 
 * information holder.
 * 
 * @author Currell
 * @version 0.0c
 */
public class User {
    
    //CHECKSTYLE:OFF	Variables defined in class javadoc.
    private long userID;
    private String username;
    private String passhash;
    private String first;
    private String last;
    private String email;
    private ArrayList<Category> categories;
    //CHECKSTYLE:ON
    
    /**
     * Constructor for User class. Initializes all instance fields with given
     * parameters. By default, a user has no categories associated with him or
     * her.
     * 
     * @param userID user's ID
     * @param username user's username
     * @param passhash user's password
     * @param first user's first name
     * @param last user's last name
     * @param email user's email
     */
    //CHECKSTYLE:OFF	No ambiguity about variables when "this" is used.
    public User(long userID, String username, String passhash, String first,
            String last, String email) {
    	//CHECKSTYLE:ON
    	
        super();
        this.userID = userID;
        this.username = username;
        this.passhash = passhash;
        this.first = first;
        this.last = last;
        this.email = email;
        categories = new ArrayList<Category>();
    }

    
    /**
     * Getter for user's ID.
     * 
     * @return user ID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Getter for user's username.
     * 
     * @return user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for user's password (called passhash).
     * 
     * @param passhash password to set
     */
    //CHECKSTYLE:OFF	No ambiguity about variables when "this" is used.
    public void setPasshash(String passhash) {
    	//CHECKSTYLE:ON
        this.passhash = passhash;
    }

    /**
     * Getter for user's password (called passhash).
     * 
     * @return user's password
     */
    public String getPasshash() {
        return passhash;
    }

    /**
     * Getter for user's first name.
     * 
     * @return user's first name
     */
    public String getFirst() {
        return first;
    }

    /**
     * Getter for user's last name.
     * 
     * @return user's last name
     */
    public String getLast() {
        return last;
    }

    /**
     * Getter for user's email address.
     * 
     * @return user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Adds a given category to the list of categories associated with this
     * user.
     * 
     * @param toAdd category to associate with user
     */
    public void addCategory(Category toAdd) {
        categories.add(toAdd);
    }

    /* The following is a method which, though functional, was replaced for
     * consistency purposes. It has been left intentionally just in case.
     * 
     * public String[] getCategoryNames(){ categories =
     * dbHandler.getCategoriesForUser(user.getUserID()); String[] categoryArray
     * = new String[categories.length]; if(categories != null){ for(int i =0; i
     * < categories.length ; i++){ if(categories[i]!=null) categoryArray[i] =
     * categories[i].getCategory_name(); } } }
     */
    
    
    
    @Override
    public String toString() {
        return "User [userID=" + userID + ", username=" + username
                + ", passhash=" + passhash + ", first=" + first + ", last="
                + last + ", email=" + email + "]";
    }

}
