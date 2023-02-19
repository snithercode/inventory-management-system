package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The Inventory class maintains a collection of Parts and Products.
 * It includes methods to add, look up, update, and delete Parts and Products, as well as methods to get the list of all Parts and Products.
 */
public class Inventory {

    // Declare Fields

    /**
     * An observable list containing all Parts in the Inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * An observable list containing all Products in the Inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Used to generate unique IDs for Parts.
     */
    private static int partId = 0;
    /**
     * Used to generate unique IDs for Products.
     */
    private static int productId = 0;

    // Declare Methods

    /**
     * Gets the current part ID.
     * @return The current ID.
     */
    public static int getPartId(){
        return partId;
    }


    /**
     * Adds a Part object to the allParts list.
     * @param newPart The Part object to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a Product object to the allProducts list.
     * @param newProduct The Product object to add.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Searches for a Part in the allParts list by partId.
     * @param partId The id of the Part to look up.
     * @return The Part object that matches the partId.
     */
    public static Part lookupPart(int partId){
        for(Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a Product in the allProducts list by productId.
     * @param productId The id of the Product to look up.
     * @return The Product object that matches the productId.
     */
    public static Product lookupProduct(int productId){
        for(Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches for a Part in the allParts list by partName.
     * @param partName The name of the Part to look up.
     * @return The list of all Parts that match the name.
     */
    public static ObservableList<Part> lookupPart(String partName){
        // Create an empty list to hold search results.
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        // Iterate through each part in the allParts list and add any parts that match the provided name to the search results list.
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partSearchResults.add(part);
            }
        }
        // Return the list of parts that match the provided name.
        return partSearchResults;
    }

    /**
     * Searches for a Product in the allProducts list by productName.
     * @param productName The name of the Product to look up.
     * @return The list of all Products that match the name.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        // Create an empty list to hold search results.
        ObservableList<Product> productSearchResults = FXCollections.observableArrayList();
        // Iterate through each product in the allProducts list and add any products that match the provided name to the search results list.
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productSearchResults.add(product);
            }
        }
        // Return the list of products that match the provided name.
        return productSearchResults;
    }

    /**
     * Updates a Part in the allParts list.
     * @param index The index of the Part in the allParts list to update.
     * @param selectedPart The Part object that replaces the original Part.
     */
    public static void updatePart(int index, Part selectedPart){
            Inventory.getAllParts().set(index, selectedPart);
    }

    /**
     * Updates a Product in the allProducts list.
     * @param index The index of the Product in the allProducts list to update.
     * @param selectedProduct The Product object that replaces the original Product.
     */
    public static void updateProduct(int index, Product selectedProduct){
        Inventory.getAllProducts().set(index, selectedProduct);
    }

    /**
     * Deletes a Part from the allParts list.
     * @param selectedPart The Part to delete.
     * @return A boolean indicating whether the Part was successfully deleted.
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a Product from the allProducts list.
     * @param selectedProduct The Product to delete.
     * @return A boolean indicating whether the Product was successfully deleted.
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * Retrieves a list of all parts in the inventory.
     * @return allParts  an ObservableList of all parts in the inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves a list of all products in the inventory.
     * @return allProducts an ObservableList of all products in the inventory.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * Gets the product ID for this product.
     * @return the product ID.
     */
    public static int getProductId() {
        return productId;
    }
}

