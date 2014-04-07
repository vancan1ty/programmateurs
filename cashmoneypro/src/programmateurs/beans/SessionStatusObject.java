package programmateurs.beans;

/**
 * To be honest, I'm not really sure why this class exists or if we even use it
 * ever. Be patient with the comments; this file had no documentation when I
 * found it.
 * 
 * From the name, I'm guessing it's just meant to keep track of the current
 * session, but Anchor sort of fills that role already.
 * 
 * @author Currell
 * @version 0.0c
 * 
 */
public class SessionStatusObject {

    // CHECKSTYLE:OFF Variables defined in class javadoc
    private Integer userID;
    private Integer checkinID;
    private String userFirst;
    private String userLast;
    private String email;
    private String password;
    private String imagepath;
    private Double latitude;
    private Double longitude;

    // CHECKSTYLE:ON

    /**
     * Getter for ID of current user.
     * 
     * @return ID of user currently logged in
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for current user ID.
     * 
     * @param userID
     *            ID of user currently logged in
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setUserID(Integer userID) {
        // CHECKSTYLE:ON
        this.userID = userID;
    }

    /**
     * Getter for checkinID.
     * 
     * @return ID for current session
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public int getCheckinID() {
    //CHECKSTYLE:ON
        return checkinID;
    }

    /**
     * This is the setter for checkinID.
     * 
     * @param checkinID
     *            for current session
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setCheckinID(Integer checkinID) {
        // CHECKSTYLE:ON
        this.checkinID = checkinID;
    }

    /**
     * Getter for user's first name.
     * 
     * @return first name of user
     */
    public String getUserFirst() {
        return userFirst;
    }

    /**
     * Setter for user's first name.
     * 
     * @param userFirst
     *            first name of user
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setUserFirst(String userFirst) {
        // CHECKSTYLE:ON
        this.userFirst = userFirst;
    }

    /**
     * Getter for user's last name.
     * 
     * @return last name of user
     */
    public String getuserLast() {
        return userLast;
    }

    /**
     * Setter for last name of user.
     * 
     * @param userLast
     *            user's last name
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setUserLast(String userLast) {
        // CHECKSTYLE:ON
        this.userLast = userLast;
    }

    /**
     * Getter for user's email.
     * 
     * @return email of current user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for user's email.
     * 
     * @param email
     *            current user's email
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setEmail(String email) {
        // CHECKSTYLE:ON
        this.email = email;
    }

    /**
     * Getter for user's password.
     * 
     * @return current user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for user's password.
     * 
     * @param password
     *            current user's password
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setPassword(String password) {
        // CHECKSTYLE:ON
        this.password = password;
    }

    /**
     * Getter for imagepath.
     * 
     * @return imagepath
     */
    public String getImagepath() {
        return imagepath;
    }

    /**
     * Setter for imagepath.
     * 
     * @param imagepath
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setImagepath(String imagepath) {
        // CHECKSTYLE:ON
        this.imagepath = imagepath;
    }

    /**
     * Getter for lattitude.
     * 
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter for latitude.
     * 
     * @param latitude
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setLatitude(Double latitude) {
        // CHECKSTYLE:ON
        this.latitude = latitude;
    }

    /**
     * Getter for longitude.
     * 
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter for latitude.
     * 
     * @param longitude
     */
    // CHECKSTYLE:OFF No ambiguity about variable when "this" is used.
    public void setLongitude(Double longitude) {
        // CHECKSTYLE:ON
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "SessionStatusObject [userID=" + userID + ", checkinID="
                + checkinID + ", currentBuilding=" + ", userFirst=" + userFirst
                + ", userLast=" + userLast + ", email=" + email + ", password="
                + password + ", imagepath=" + imagepath + ", latitude="
                + latitude + ", longitude=" + longitude + "]";
    }
}
