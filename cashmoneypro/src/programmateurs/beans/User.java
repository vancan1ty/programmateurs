package programmateurs.beans;

import java.util.ArrayList;

public class User {
	private long userID;
	private String username;
	private String passhash;
	private String first;
	private String last;
	private String email;
	private ArrayList<Category> categories;

	public User(long userID, String username, String passhash, String first,
			String last, String email) {
		super();
		this.userID = userID;
		this.username = username;
		this.passhash = passhash;
		this.first = first;
		this.last = last;
		this.email = email;
		categories = new ArrayList<Category>();
	}

	public long getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public void setPasshash(String passhash) {
		this.passhash = passhash;
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

	public void addCategory(Category toAdd) {
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
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", passhash=" + passhash + ", first=" + first + ", last="
				+ last + ", email=" + email + "]";
	}

}
