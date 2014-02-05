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

	public int getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public int getCheckinID() {
		return checkinID;
	}
	public void setCheckinID(Integer checkinID) {
		this.checkinID = checkinID;
	}

	public String getUser_first() {
		return user_first;
	}
	public void setUser_first(String user_first) {
		this.user_first = user_first;
	}
	public String getUser_last() {
		return user_last;
	}
	public void setUser_last(String user_last) {
		this.user_last = user_last;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "SessionStatusObject [userID=" + userID + ", checkinID="
				+ checkinID + ", currentBuilding=" + ", user_first="
				+ user_first + ", user_last=" + user_last + ", email=" + email
				+ ", password=" + password + ", imagepath=" + imagepath
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}