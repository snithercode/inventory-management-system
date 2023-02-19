package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import utils.SceneSwap;
import utils.ValidateInput;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddProductController class is responsible for controlling the Add Product form view.
 * This class handles user input validation and updates to the product list and associated part list.
 */
public class AddProductController implements Initializable {

    // Declare Fields

    /**
     * A TextField for searching through parts when adding them to a product.
     */
    public TextField searchBoxParts;

    /**
     * A Button for removing an associated part from a product.
     */
    @FXML
    private Button removeAssociatedPartButton;

    /**
     * A Button for adding a selected part to a product.
     */
    @FXML
    private Button addSelectedPartButton;

    /**
     * A TextField for the ID of the new product.
     */
    @FXML
    private TextField idField;

    /**
     * A TextField for the name of the new product.
     */
    @FXML
    private TextField nameField;

    /**
     * A TextField for the inventory level of the new product.
     */
    @FXML
    private TextField invField;

    /**
     * A TextField for the price of the new product.
     */
    @FXML
    private TextField priceField;

    /**
     * A TextField for the maximum inventory level of the new product.
     */
    @FXML
    private TextField maxField;

    /**
     * A TextField for the minimum inventory level of the new product.
     */
    @FXML
    private TextField minField;

    /**
     * A Button for saving the new product.
     */
    @FXML
    private Button saveProductButton;

    /**
     * A Button for canceling the Add Product form and returning to the main screen.
     */
    @FXML
    private Button cancelButton;

    /**
     * A TableView for displaying all parts.
     */
    @FXML
    private TableView<Part> partsTableView;

    /**
     * A TableColumn for displaying the ID of a part.
     */
    @FXML
    private TableColumn<Part, Integer> partId;

    /**
     * A TableColumn for displaying the name of a part.
     */
    @FXML
    private TableColumn<Part, String> partName;

    /**
     * A TableColumn for displaying the inventory level of a part.
     */
    @FXML
    private TableColumn<Part, Integer> partInventory;

    /**
     * A TableColumn for displaying the price of a part.
     */
    @FXML
    private TableColumn<Part, Double> partPrice;

    /**
     * A TableView for displaying all associated parts of a product.
     */
    @FXML
    private TableView<Part> associatedPartsTableView;

    /**
     * A TableColumn for displaying the ID of an associated part.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartId;

    /**
     * A TableColumn for displaying the name of an associated part.
     */
    @FXML
    private TableColumn<Part, String> associatedPartName;

    /**
     * A TableColumn for displaying the inventory level of an associated part.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventory;

    /**
     * A TableColumn for displaying the price of an associated part.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;

    /**
     * A Product object to hold the current new product being created.
     */
    private Product product = new Product(0,"",0.0,0,0,0);


    // Declare Methods

    /**
     * Initializes the Add Product form, populating the fields and tables with appropriate data. This method also adds a
     * listener to the allAssociatedParts list of the product to detect changes, using a lambda expression as a ListChangeListener.
     * The lambda expression is used to provide an inline implementation of ListChangeListener's onChanged() method.
     * When a change event is detected, it updates the associatedPartsTableView to display the new list of associated parts,
     * and disables the Remove Associated Part button if the list is empty.
     @param url The location of the FXML file.
     @param resourceBundle The resources required for this view.
     @see javafx.collections.ListChangeListener
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int lastIndex = Inventory.getAllProducts().size() - 1;
        int tempProductId = Inventory.getAllProducts().get(lastIndex).getId() + 1;
        idField.setText(String.valueOf(tempProductId));

        // Bind the items property of the partsTableView to the allParts list
        partsTableView.setItems(Inventory.getAllParts());

        if (!Inventory.getAllParts().isEmpty()) {
            updatePartsTableView(Inventory.getAllParts());
        }

        // Initialize a new product instance and bind the associatedPartsTableView's items property to the allAssociatedParts list from the product.
        associatedPartsTableView.setItems(product.getAllAssociatedParts());

        // Check if the allAssociatedParts list is not empty and update the associatedPartsTableView if it is not
        if (!product.getAllAssociatedParts().isEmpty()) {
            updateAssociatedPartsTableView(product.getAllAssociatedParts());
        }

        // Add a listener to the allAssociatedParts list to detect changes using a lambda expression as a ListChangeListener
        product.getAllAssociatedParts().addListener((ListChangeListener<Part>) change -> {
            // Update the associatedPartsTableView when a change occurs to the associatedParts list
            associatedPartsTableView.setItems(product.getAllAssociatedParts());
            // Check if list is empty. If it is, disable the Remove Associated Part button.
            if (product.getAllAssociatedParts().isEmpty()) {
                removeAssociatedPartButton.setDisable(true);
            } else {
                removeAssociatedPartButton.setDisable(false);
            }
        });
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
     * Update the associated parts table view with the given list of parts.
     * @param associatedParts the list of associated parts to display in the table view
     */
    private void updateAssociatedPartsTableView(ObservableList<Part> associatedParts) {
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method is an event handler for the "Add" button in the AddProduct form.
     * It gets the selected part from the partsTableView and adds it to the associated parts list for the product.
     * Then, it updates the UI by refreshing the associated parts table view with the new list of associated parts.
     * @param actionEvent the click event on the "Add" button.
     */
    @FXML
    private void onAddSelectedPartButtonClick(ActionEvent actionEvent) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(selectedPart);
        updateAssociatedPartsTableView(product.getAllAssociatedParts());
    }

    /**
     * Handles the event triggered by the user clicking the "Save" button on the AddProduct form.
     * This method validates the user's input for the new product, including checking for any empty fields,
     * ensuring that appropriate data formats have been provided by the user, and verifying that at least one part
     * has been associated with the new product. If the input is valid, a new Product object is created and added
     * to the allProducts list, with any associated parts also being linked to the new product. If the
     * input is invalid, an alert is displayed to the user with information on how to correct their input.
     * @param actionEvent the click event on the "Save" button
     */
    @FXML
    private void onSaveProductButtonClick(ActionEvent actionEvent) {
        // Check the associatedParts list. If it is empty, alert the user to add a part and try again.
        if(product.getAllAssociatedParts().isEmpty()){
            Alert requireAssociatedParts = new Alert(Alert.AlertType.INFORMATION,"Each product requires at least one associated part. Please add an associated part and try saving again.");
            requireAssociatedParts.showAndWait();
            return;
        }

        // Check for any empty text fields prior to parsing input. If any empty fields exist, ask the user to fill in the missing input and try again.
        if(this.nameField.getText().isEmpty() || this.invField.getText().isEmpty() || this.priceField.getText().isEmpty() || this.maxField.getText().isEmpty() || this.minField.getText().isEmpty()) {
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields are empty.\nPlease fill the empty fields and try again.");
            inputErrorAlert.showAndWait();
            return;
        }

        // Parse and store inputs. Check that appropriate data format has been provided by the user.
        int extractedPartId = Integer.parseInt(this.idField.getText());
        String extractedName = this.nameField.getText();
        int extractedInv;
        double extractedPrice;
        int extractedMax;
        int extractedMin;

        try {
            extractedInv = Integer.parseInt(this.invField.getText());
        } catch (NumberFormatException e) {
            // Display an error message to the user if the inventory value is not a valid integer
            String errorMessage = "The inventory value entered must be an integer.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        try {
            extractedPrice = Double.parseDouble(this.priceField.getText());
        } catch (NumberFormatException e) {
            // Display an error message to the user if the price value is not a valid decimal or integer
            String errorMessage = "The price value entered must be an integer or decimal value.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        try {
            extractedMax = Integer.parseInt(this.maxField.getText());
        } catch (NumberFormatException e) {
            // Display an error message to the user if the max value is not a valid integer
            String errorMessage = "The max value entered must be an integer.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        try {
            extractedMin = Integer.parseInt(this.minField.getText());
        } catch (NumberFormatException e) {
            // Display an error message to the user if the min value is not a valid integer
            String errorMessage = "The min value entered must be an integer.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        String inputErrorMessage = ValidateInput.validateInputProduct(extractedName, extractedInv, extractedPrice, extractedMin, extractedMax);
        if (inputErrorMessage.isEmpty()) {
            // Create the new product with the extracted data.
            Product newProduct = new Product(extractedPartId, extractedName, extractedPrice, extractedInv, extractedMin, extractedMax);
            // Connect the new product to it's associated parts list.
            newProduct.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
            // Add the newly created product to Inventory's allProducts list.
            Inventory.addProduct(newProduct);
            // Go back to the Main screen.
            SceneSwap.swapScene("/view/MainForm.fxml", saveProductButton);
        } else {
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields contain invalid values.\n" + inputErrorMessage);
            inputErrorAlert.showAndWait();
            return;
        }
    }

    /**
     * Exits the Add Product form view and returns to the Main Form view.
     * @param actionEvent The click event to the "Cancel" button.
     */
    public void cancelButtonClick(ActionEvent actionEvent) {
        SceneSwap.swapScene("/view/MainForm.fxml", cancelButton);
    }

    /**
     * Retrieves the selected part from the associatedPartsTableView and removes it from the associatedParts list.
     * After removing the part, updates the associatedPartsTableView with the current, updated list of associated parts.
     * @param actionEvent the Remove Associated Part button is clicked.
     */
    public void onRemoveAssociatedPartButtonClick(ActionEvent actionEvent) {
        // Get the selected associated part from the table view
        Part selectedAssociatedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();

        // Try to delete the selected associated part from the product
        if (product.deleteAssociatedPart(selectedAssociatedPart)) {
            // If the deletion was successful, update the table view
            updateAssociatedPartsTableView(product.getAllAssociatedParts());
        } else {
            // If the deletion was not successful, show an error message
            System.out.println("Failed to remove the selected associated part.");
        }
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
}
