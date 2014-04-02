package programmateurs.beans;

public class SessionStatusObject {

    private Integer userID;
    private Integer checkinID;
    private String user_first;
    private String user_last;
    private String email;
    private String password;
    private String imagepath;
    private Double latitude;
    private Double longitude;

    public final int getUserID() {
        return userID;
    }

    public final void setUserID(final Integer userID) {
        this.userID = userID;
    }

    public final int getCheckinID() {
        return checkinID;
    }

    public final void setCheckinID(final Integer checkinID) {
        this.checkinID = checkinID;
    }

    public final String getUser_first() {
        return user_first;
    }

    public final void setUser_first(final String user_first) {
        this.user_first = user_first;
    }

    public final String getUser_last() {
        return user_last;
    }

    public final void setUser_last(final String user_last) {
        this.user_last = user_last;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

    public final String getImagepath() {
        return imagepath;
    }

    public final void setImagepath(final String imagepath) {
        this.imagepath = imagepath;
    }

    public final double getLatitude() {
        return latitude;
    }

    public final void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public final double getLongitude() {
        return longitude;
    }

    public final void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public final String toString() {
        return "SessionStatusObject [userID=" + userID + ", checkinID="
                + checkinID + ", currentBuilding=" + ", user_first="
                + user_first + ", user_last=" + user_last + ", email=" + email
                + ", password=" + password + ", imagepath=" + imagepath
                + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
