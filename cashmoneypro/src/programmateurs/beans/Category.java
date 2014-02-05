package programmateurs.beans;

public class Category {

	private int categoryID;
	private int userID; //user who owns this category
	private String category_name;

	public Category(int categoryID, int userID, String category_name) {
		super();
		this.userID = userID;
		this.categoryID = categoryID;
		this.category_name = category_name;
	}

	public int getCategoryID() {

		return categoryID;
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
