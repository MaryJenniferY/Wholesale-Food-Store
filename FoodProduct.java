/**
 * This class represents a food product with various attributes such as
 * product ID, SKU, description, category, and price. It provides methods to retrieve and set
 * these attributes.
 */
public class FoodProduct {
    private int productID;
    private String SKU;
    private String description;
    private String category;
    private int price;

    /**
    * Constructor for the FoodProduct class.
    *
    * @param productID   the ID for the product
    * @param SKU         the stock keeping unit of the product
    * @param description the description of the product
    * @param category    the category of the product
    * @param price       the price of the product
    */
    public FoodProduct(int productID, String SKU, String description, String category, int price){

        this.productID = productID;
        this.SKU = SKU;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    /**
    * Getter for the product ID.
    *
    * @return the product ID
    */
    public int getID(){
        return productID;
    }

    /**
    * Getter for the Stock Keeping Unit (SKU).
    *
    * @return the SKU
    */
    public String getSKU(){
        return SKU;
    }

    /**
    * Getter for the description.
    *
    * @return the description
    */
    public String getDescription(){
        return description;
    }

    /**
    * Getter for the category.
    *
    * @return the category
    */
    public String getCategory(){
        return category;
    }

    /**
    * Getter for the price.
    *
    * @return the price
    */
    public int getPrice(){
        return price;
    }

    /**
    * Returns a string representation of the food product.
    *
    * @return a string representation of the food product
    */
    @Override
    public String toString() {

        return "Food Product [Product ID=" + productID + ", SKU=" + SKU + ", Description=" + description + ", Category=" + category + ", Price=" + price + "]";
    }


    /**
    * Setter for the price.
    *
    * @return the price
    */
    public void setPrice(int price) {
        this.price = price;
    }

}
