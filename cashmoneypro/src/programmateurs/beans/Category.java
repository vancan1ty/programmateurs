package programmateurs.beans;

/**
 * Categories are custom-made "envelopes" that the User uses to separate his/her
 * money according to how he/she plans on spending it. Each category has an ID,
 * a reference to the ID of the user who made the category, and a name.
 * 
 * @author Currell
 * @version 0.0
 */
public class Category {
    // CHECKSTYLE:OFF Variables defined in class javadoc.
    private long categoryID;
    private long userID;
    private String category_name;

    // CHECKSTYLE:ON
    /**
     * Constructor for Category.
     * 
     * @param categoryID
     *            ID of Category
     * @param userID
     *            ID of owner
     * @param category_name
     *            User-assigned name of category
     */
    // CHECKSTYLE:OFF category_name is a part of JQueries, too late to change
    public Category(long categoryID, long userID, String category_name) {
        // CHECKSTYLE:ON
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
    //CHECKSTYLE:OFF    Category has its own method for this, as it should.
    public long getCategoryID() {
        return categoryID;
    }
    //CHECKSTYLE:ON

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
    // CHECKSTYLE:OFF category_name is a part of JQueries, too late to change
    public String getCategory_name() {
        // CHECKSTYLE:ON
        return category_name;
    }

    /**
     * 
     * @return String representation of Category
     */
    public String toString() {
        return "Category [userID=" + userID + " categoryID=" + categoryID
                + ", category_name=" + category_name + "]";
    }

}
