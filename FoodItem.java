/**
 * This class represents an individual food item with a unique identifier,
 * associated FoodProduct, and expiry date.
 */
public class FoodItem {
    private int foodItemID;
    private FoodProduct foodProduct;
    private String expiryDate;

    /**
    * Creates a new instance of FoodItem with given parameters.
    *
    * @param foodItemID  the ID of the food item
    * @param foodProduct the product details of the food item
    * @param expiryDate  the expiry date of the food item
    */
    public FoodItem(int foodItemID, FoodProduct foodProduct, String expiryDate) {
        this.foodItemID = foodItemID;
        this.foodProduct = foodProduct;
        this.expiryDate = expiryDate;
    }

    /**
    * Getter for the unique identifier of the food item.
    *
    * @return the unique identifier of the food item
    */
    public int getId() {
        return foodItemID;
    }

    /**
    * Getter for the food product details.
    *
    * @return the food product details
    */
    public FoodProduct getFoodProduct() {
        return foodProduct;
    }

    /**
    * Getter for the expiry date.
    *
    * @return the expiry date
    */
    public String getExpiryDate() {
        return expiryDate;
    }

     /**
    * Returns a string representation of the food item.
    *
    * @return a string representation of the food item
    */
    @Override
    public String toString() {
        return "Food Item [Food Item ID=" + foodItemID + ", " + foodProduct.toString() + ", Expiry Date=" + expiryDate + "]";
    }

}