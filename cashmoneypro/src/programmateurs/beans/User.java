package programmateurs.beans;

import java.util.ArrayList;
/**
 *
 * @author Currell Probably
 */
public class User {
    private long userID;
    private String username;
    private String passhash;
    private String first;
    private String last;
    private String email;
    private ArrayList<Category> categories;

    public User(final long userID, final String username, final String passhash, final String first,
            final String last, final String email) {
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
     * @return The User's identification number
     */
    public final long getUserID() {
        return userID;
    }

    /**
     * @return The User's username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * @param passhash The new passhash
     */
    public final void setPasshash(final String passhash) {
        this.passhash = passhash;
    }

    /**
     * @return The passhash
     */
    public final String getPasshash() {
        return passhash;
    }

    /**
     * @return The User's first name
     */
    public final String getFirst() {
        return first;
    }

    /**
     * @return The User's last name
     */
    public final String getLast() {
        return last;
    }

    /**
     * @return the User's email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * @param toAdd A new category
     */
    public final void addCategory(final Category toAdd) {
        categories.add(toAdd);
    }

    /*
     * public String[] getCategoryNames(){ categories =
     * dbHandler.getCategoriesForUser(user.getUserID()); String[] categoryArray
     * = new String[categories.length]; if(categories != null){ for(int i =0; i
     * < categories.length ; i++){ if(categories[i]!=null) categoryArray[i] =
     * categories[i].getCategory_name(); } } }
     */
    @Override
    public final String toString() {
        return "User [userID=" + userID + ", username=" + username
                + ", passhash=" + passhash + ", first=" + first + ", last="
                + last + ", email=" + email + "]";
    }

}
