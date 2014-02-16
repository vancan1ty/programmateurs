package programmateurs.beans;

public class Category {

	private long categoryID;
	private long userID; //user who owns this category
	private String category_name;

	public Category(long categoryID, long userID, String category_name) {
		super();
		this.categoryID = categoryID;
		this.userID = userID;
		this.category_name = category_name;
	}

	public long getCategoryID() {
		return categoryID;
	}

	public long getUserID() {
		return userID;
	}

	public String getCategory_name() {
		return category_name;
	}
	


	@Override
	public String toString() {
		return "Category [userID=" + userID + " categoryID=" + categoryID + ", category_name="
				+ category_name + "]";
	}

	

}
