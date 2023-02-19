package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import utils.SceneSwap;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainController class is responsible for managing the main form of the application, which displays the tables for
 * parts and products, as well as buttons for adding, modifying, and deleting parts and products. The class also includes
 * methods for searching the tables and exiting the application.
 * <p></p>
 *
 * <p><b>Javadoc Location:</b></p>
 * InventoryApp/src/javadoc
 *
 * <p><b>Future Enhancement:</b></p>
 * To extend the functionality of the application in a future update, I would expand the search functionality.
 * I'd add support for locating all products that include a specified part. This would be beneficial for many reasons,
 * for example, helping identify parts that may be affected by a parts recall, or helping determine how many of a part
 * will need to be re-ordered to manage inventory levels based on the number of products it is a part of.
 *
 * <p><b>Runtime Error:</b></p>
 * Runtime error description located in comment for ModifyPartController class.
 */

public class MainController implements Initializable {
    /**
     * TableColumn to display the ID of the parts in the partsTableView.
     */
    public TableColumn<Part, Integer> partId;

    /**
     * TableColumn to display the name of the parts in the partsTableView.
     */
    public TableColumn<Part, String> partName;

    /**
     * TableColumn to display the current inventory level of the parts in the partsTableView.
     */
    public TableColumn<Part, Integer> partInventory;

    /**
     * TableColumn to display the price of the parts in the partsTableView.
     */
    public TableColumn<Part, Double> partPrice;

    /**
     * TableColumn to display the ID of the products in the productsTableView.
     */
    public TableColumn<Product, Integer> productId;

    /**
     * TableColumn to display the name of the products in the productsTableView.
     */
    public TableColumn<Product, String> productName;

    /**
     * TableColumn to display the current inventory level of the products in the productsTableView.
     */
    public TableColumn<Product, Integer> productInventory;

    /**
     * TableColumn to display the price of the products in the productsTableView.
     */
    public TableColumn<Product, Double> productPrice;

    /**
     * Button to close the application.
     */
    public Button exitButton;

    /**
     * TextField for user input to search for parts in the partsTableView.
     */
    public TextField searchBoxParts;

    /**
     * TextField for user input to search for products in the productsTableView.
     */
    public TextField searchBoxProducts;

    /**
     * TableView to display the parts in the inventory.
     */
    @FXML
    public TableView<Part> partsTableView;

    /**
     * TableView to display the products in the inventory.
     */
    @FXML
    public TableView<Product> productsTableView;

    /**
     * Button to navigate to the Add Part screen.
     */
    @FXML
    private Button addPartButton;

    /**
     * Button to navigate to the Add Product screen.
     */
    @FXML
    private Button addProductButton;

    /**
     * Button to delete the selected part from the inventory.
     */
    @FXML
    private Button deletePartButton;

    /**
     * Button to delete the selected product from the inventory.
     */
    @FXML
    private Button deleteProductButton;

    /**
     * Button to navigate to the Modify Part screen.
     */
    @FXML
    private Button modifyPartButton;

    /**
     * Button to navigate to the Modify Product screen.
     */
    @FXML
    private Button modifyProductButton;

    /**
     * Initializes the MainForm by setting the items property of the partsTableView and productsTableView to the allParts and allProducts list, respectively.
     * If either list is empty, the Modify and Delete buttons for that table are disabled. If the lists are not empty, the Modify and Delete buttons are enabled,
     * and the table views are updated.
     * @param url the URL location of the fxml file used to create the MainForm
     * @param resourceBundle the resource bundle used for localization in the MainForm
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bind the items property of the partsTableView to the allParts list
        partsTableView.setItems(Inventory.getAllParts());

        // Check if list is empty. If it is, disable Modify and Delete buttons. If not, enabled them and update the table view.
        if(Inventory.getAllParts().isEmpty()) {
            modifyPartButton.setDisable(true);
            deletePartButton.setDisable(true);
        } else {
            modifyPartButton.setDisable(false);
            deletePartButton.setDisable(false);
            updatePartsTableView(Inventory.getAllParts());
        }

        // Bind the items property of the productsTableView to the allProducts list
        productsTableView.setItems(Inventory.getAllProducts());

        // Check if list is empty. If it is, disable Modify and Delete buttons. If not, enabled them and update the table view.
        if(Inventory.getAllProducts().isEmpty()) {
            modifyProductButton.setDisable(true);
            deleteProductButton.setDisable(true);
        } else {
            modifyProductButton.setDisable(false);
            deleteProductButton.setDisable(false);
            updateProductsTableView(Inventory.getAllProducts());
        }
    }

    /**
     * Update the parts table view with the given list of parts.
     * @param allParts the list of parts to display in the table view
     */
    private void updatePartsTableView(ObservableList<Part> allParts) {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Update the products table view with the given list of products.
     * @param allProducts the list of products to display in the table view
     */
    private void updateProductsTableView(ObservableList<Product> allProducts) {

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method switches the fxml view from MainForm to AddPart.
     * @param actionEvent the click event on the "Add" button
     */
    public void onAddPartButtonClick(ActionEvent actionEvent) {
        SceneSwap.swapScene("/view/AddPart.fxml", addPartButton);
    }

    /**
     * This method retrieves the user-selected Part object and passes it to the ModifyPart.fxml view.
     * @param actionEvent the click event on the "Modify" button.
     * @throws IOException An IOException may occur if the FXML file is not located.
     */
    public void onModifyPartButtonClick(ActionEvent actionEvent) throws IOException {
        // Get the user-selected Part
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        int index = Inventory.getAllParts().indexOf(selectedPart);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Get the controller of the target FXML file
        ModifyPartController modifyPartController = fxmlLoader.getController();
        modifyPartController.receiveSelectedPart(index, selectedPart);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method retrieves the user-selected Part object and deletes it from the allParts ObservableList.
     * @param actionEvent the click event on the "Delete" button.
     */
    public void onDeletePartButtonClick(ActionEvent actionEvent) {
        // Check if a part has been selected in the partsTableView
        if (partsTableView.getSelectionModel().isEmpty()) {
            Alert noPartsSelected = new Alert(Alert.AlertType.ERROR, "No part has been selected for deletion.");
            noPartsSelected.showAndWait();
            return;
        }
        // Confirm the deletion before proceeding
        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete the selected part?");
        Optional<ButtonType> result = confirmDeletion.showAndWait();
        // If confirmed, delete the part
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * This method switches the fxml view from MainForm to AddProduct.
     * @param actionEvent the click event on the "Add" button.
     */
    public void onAddProductButtonClick(ActionEvent actionEvent) {
        SceneSwap.swapScene("/view/AddProduct.fxml", addProductButton);
    }

    /**
     * This method retrieves the user-selected Product object and passes it to the ModifyProduct.fxml view.
     * @param actionEvent the click event on the "Modify" button.
     * @throws IOException An IOException may occur if the FXML file is not located.
     */
    public void onModifyProductButtonClick(ActionEvent actionEvent) throws IOException {

        // Get the user-selected Product
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        int index = Inventory.getAllProducts().indexOf(selectedProduct);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Get the controller of the target FXML file
        ModifyProductController modifyProductController = fxmlLoader.getController();
        modifyProductController.receiveSelectedProduct(index, selectedProduct);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes the selected Product from the allProducts list and the Products TableView.
     * @param actionEvent the click event on the "Delete" button.
     */
    public void onDeleteProductButtonClick(ActionEvent actionEvent) {
        // Check if a product has been selected in the productsTableView
        if (productsTableView.getSelectionModel().isEmpty()) {
            Alert noProductSelected = new Alert(Alert.AlertType.ERROR, "No product has been selected for deletion.");
            noProductSelected.showAndWait();
            return;
        }
        // Show an alert to confirm deletion of the selected product
        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete the selected product?");
        Optional<ButtonType> result = confirmDeletion.showAndWait();
        // If the user confirmed the deletion and the selected product has no associated parts, delete the product from the inventory
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                Inventory.deleteProduct(selectedProduct);
            }
            // If the selected product has associated parts, deny the deletion and show an error message
            else {
                Alert deletionDenied = new Alert(Alert.AlertType.ERROR, "The product cannot be deleted because there is at least one part associated with it.");
                deletionDenied.showAndWait();
            }
        }
    }


    /**
     * This method terminates the application.
     * @param actionEvent the click event on the "Exit" button.
     */
    public void onExitButtonClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Search for a part based on user input in the search box. If the input is an integer, the search will be performed by product ID.
     * If the input is a string, the search will be performed by part name. Displays a message if no matching parts are found.
     * @param actionEvent when the user hits enter after entering their search query
     */
    @FXML
    public void onPartsSearch(ActionEvent actionEvent) {
        // Get the input from the search box
        String userSearchInput = searchBoxParts.getText();

        // Variables for storing the search result
        int searchedPartId;
        String searchedPartName;
        Part searchResult;

        // ObservableList to store the search results
        ObservableList<Part> allPartsSearchResults = FXCollections.observableArrayList();

        // If the search input is empty, set all parts in the table view
        if (userSearchInput.isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        } else {
            // Try to parse the search input as an integer
            try {
                searchedPartId = Integer.parseInt(userSearchInput);
                // Look up the part by id
                searchResult = Inventory.lookupPart(searchedPartId);
                if (searchResult == null) {
                    // Display an error message if the search result is null
                    Alert searchAlert = new Alert(Alert.AlertType.INFORMATION);
                    searchAlert.setContentText("No part with the ID " + searchedPartId + " was located.");
                    searchAlert.showAndWait();
                } else {
                    // Add the search result to the observable list
                    allPartsSearchResults.add(searchResult);
                    // Set the search results in the table view
                    partsTableView.setItems(allPartsSearchResults);
                }
            } catch (NumberFormatException e) {
                // If the search input is not an integer, it is a name
                searchedPartName = userSearchInput;
                // Look up the part by name
                allPartsSearchResults = Inventory.lookupPart(searchedPartName);
                if (allPartsSearchResults.isEmpty()) {
                    // Display an error message if the search result is empty
                    Alert searchAlert = new Alert(Alert.AlertType.INFORMATION);
                    searchAlert.setContentText("No part with the name " + searchedPartName + " was located.");
                    searchAlert.showAndWait();
                } else {
                    // Set the search results in the table view
                    partsTableView.setItems(allPartsSearchResults);
                }
            }
        }
    }

    /**
     * Search for a product based on user input in the search box. If the input is an integer, the search will be performed by product ID.
     * If the input is a string, the search will be performed by product name. Displays a message if no matching products are found.
     * @param actionEvent the event that triggers the search, i.e. when the user hits enter after entering their search query
     */
    @FXML
    public void onProductsSearch(ActionEvent actionEvent) {
        // Get the input from the search box
        String userSearchInput = searchBoxProducts.getText();

        // Variables for storing the search result
        int searchedProductId;
        String searchedProductName;
        Product searchResult;

        // ObservableList to store the search results
        ObservableList<Product> allProductsSearchResults = FXCollections.observableArrayList();

        // If the search input is empty, set all parts in the table view
        if (userSearchInput.isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        } else {
            // Try to parse the search input as an integer
            try {
                searchedProductId = Integer.parseInt(userSearchInput);
                // Look up the part by id
                searchResult = Inventory.lookupProduct(searchedProductId);
                if (searchResult == null) {
                    // Display an error message if the search result is null
                    Alert searchAlert = new Alert(Alert.AlertType.INFORMATION);
                    searchAlert.setContentText("No product with the ID " + searchedProductId + " was located.");
                    searchAlert.showAndWait();
                } else {
                    // Add the search result to the observable list
                    allProductsSearchResults.add(searchResult);
                    // Set the search results in the table view
                    productsTableView.setItems(allProductsSearchResults);
                }
            } catch (NumberFormatException e) {
                // If the search input is not an integer, it is a name
                searchedProductName = userSearchInput;
                // Look up the part by name
                allProductsSearchResults = Inventory.lookupProduct(searchedProductName);
                if (allProductsSearchResults.isEmpty()) {
                    // Display an error message if the search result is empty
                    Alert searchAlert = new Alert(Alert.AlertType.INFORMATION);
                    searchAlert.setContentText("No product with the name " + searchedProductName + " was located.");
                    searchAlert.showAndWait();
                } else {
                    // Set the search results in the table view
                    productsTableView.setItems(allProductsSearchResults);
                }
            }
        }
    }
}
