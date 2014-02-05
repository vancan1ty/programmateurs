package programmateurs.beans;

public class User {
	private int userID;
	private String username;
	private String passhash;
	private String first;
	private String last;
	private String email;

	public User(int userID, String username, String passhash, String first,
			String last, String email) {
		super();
		this.userID = userID;
		this.username = username;
		this.passhash = passhash;
		this.first = first;
		this.last = last;
		this.email = email;
	}

	public int getUserID() {
		return userID;
	}
	public String getUsername() {
		return username;
	}
	public String getPasshash() {
		return passhash;
	}
	public String getFirst() {
		return first;
	}
	public String getLast() {
		return last;
	}
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", passhash=" + passhash + ", first=" + first + ", last="
				+ last + ", email=" + email + "]";
	}

}
