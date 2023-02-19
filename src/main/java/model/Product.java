package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The Product class represents a product in the inventory management system. Each product has an ID, name, price, stock level,
 * minimum and maximum stock levels, and a list of associated parts. The associated parts are parts that are used to make up the product.
 */
public class Product {

    // Declare Fields
    /**
     * The list of associated parts for the product.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The ID of the product.
     */
    private int id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The price of the product.
     */
    private double price;

    /**
     * The stock level of the product.
     */
    private int stock;

    /**
     * The minimum allowed stock level for the product.
     */
    private int min;

    /**
     * The maximum allowed stock level for the product.
     */
    private int max;


    // Declare Methods

    /**
     * Constructs a new Product object with the provided parameters.
     * @param id The unique ID for the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The current stock level of the product.
     * @param min The minimum stock level allowed for the product.
     * @param max The maximum stock level allowed for the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the product id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the product name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the product price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the stock level for the product.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock level for the product.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the minimum stock level for the product.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum stock level for the product.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Returns the maximum stock level for the product.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum stock level for the product.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds the selected Part to the list of associated parts for the Product.
     * @param selectedPart The Part to add to the list of associated parts.
     */
    public void addAssociatedPart(Part selectedPart){
       associatedParts.add(selectedPart);
    }

    /**
     * Deletes the selected Associated Part from the list of associated parts for the Product.
     * @param selectedAssociatedPart The Associated Part to delete from the list.
     * @return A boolean indicating whether the Associated Part was successfully deleted.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        // Find the index of the selected part in the associatedParts list
        int selectedAssociatedPartIndex = associatedParts.indexOf(selectedAssociatedPart);

        // Check if the selected part is present in the list
        if(selectedAssociatedPartIndex >= 0){
            // Remove the selected part from the associatedParts list
            associatedParts.remove(selectedAssociatedPartIndex);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of associated parts for the Product.
     * @return The list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


}
