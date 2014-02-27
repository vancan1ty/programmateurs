package programmateurs.beans;


/**
 * Categories are custom-made "envelopes" that the
 * User uses to separate his/her money according to how
 * he/she plans on spending it.
 * 
 * @author Currell
 * @version 0.0
 */
public class Category {

	private long categoryID;
	private long userID; //user who owns this category
	private String category_name;

	/**
	 * Constructor for Category.
	 * 
	 * @param categoryID ID of Category
	 * @param userID ID of owner
	 * @param category_name User-assigned name of category
	 */
	public Category(long categoryID, long userID, String category_name) {
		super();
		this.categoryID = categoryID;
		this.userID = userID;
		this.category_name = category_name;
	}
	
	
	/**
	 * Getter for the category ID.
	 * 
	 * @return ID of category
	 */
	public long getCategoryID() {
		return categoryID;
	}

	/**
	 * Getter for the ID of the user who owns the account.
	 * 
	 * @return ID of owner
	 */
	public long getUserID() {
		return userID;
	}
	
	/**
	 * Getter for the user-assigned category name.
	 * 
	 * @return Category name
	 */
	public String getCategory_name() {
		return category_name;
	}
	


	/**
	 * 
	 * @return String representation of Category
	 */
	public String toString() {
		return "Category [userID=" + userID + " categoryID=" + categoryID + ", category_name="
				+ category_name + "]";
	}

	

}
