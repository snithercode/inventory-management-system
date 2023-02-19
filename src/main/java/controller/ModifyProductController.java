package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;
import utils.SceneSwap;
import utils.ValidateInput;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ModifyProduct Controller class is a controller for the Modify Part form that contains methods to receive the selected
 * product to modify and initialize the form fields with the product's data, including a list of it's associated parts.
 * It validates user-entered changes to the product data and saves the modified product to Inventory.
 */
public class ModifyProductController implements Initializable {

    // Declare Fields
    /**
     * TextField for searching and filtering parts.
     */
    public TextField searchBoxParts;

    /**
     * Button to add a selected part to the associated parts list for a product.
     */
    @FXML
    private Button addSelectedPartButton;

    /**
     * TableView for displaying available parts.
     */
    @FXML
    private TableView<Part> partsTableView;

    /**
     * TableColumn for displaying the ID of a part.
     */
    @FXML
    private TableColumn<Part, Integer> partId;

    /**
     * TableColumn for displaying the name of a part.
     */
    @FXML
    private TableColumn<Part, String> partName;

    /**
     * TableColumn for displaying the inventory level of a part.
     */
    @FXML
    private TableColumn<Part, Integer> partInventory;

    /**
     * TableColumn for displaying the price of a part.
     */
    @FXML
    private TableColumn<Part, Double> partPrice;

    /**
     * TableView for displaying associated parts of a product.
     */
    @FXML
    private TableView<Part> associatedPartsTableView;

    /**
     * TableColumn for displaying the ID of an associated part.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartId;

    /**
     * TableColumn for displaying the name of an associated part.
     */
    @FXML
    private TableColumn<Part, String> associatedPartName;

    /**
     * TableColumn for displaying the inventory level of an associated part.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventory;

    /**
     * TableColumn for displaying the price of an associated part.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;

    /**
     * Button to cancel creating or modifying a product and return to the main form.
     */
    @FXML
    private Button cancelButton;

    /**
     * TextField for entering the ID of a product.
     */
    @FXML
    private TextField idField;

    /**
     * TextField for entering the inventory level of a product.
     */
    @FXML
    private TextField invField;

    /**
     * TextField for entering the maximum inventory level of a product.
     */
    @FXML
    private TextField maxField;

    /**
     * TextField for entering the minimum inventory level of a product.
     */
    @FXML
    private TextField minField;

    /**
     * TextField for entering the name of a product.
     */
    @FXML
    private TextField nameField;

    /**
     * TextField for entering the price of a product.
     */
    @FXML
    private TextField priceField;

    /**
     * Button to remove an associated part from a product's list of associated parts.
     */
    @FXML
    private Button removeAssociatedPartButton;

    /**
     * Button to save changes made to a product or create a new product.
     */
    @FXML
    private Button saveProductButton;

    /**
     * Index of the currently selected product in the TableView.
     */
    private int selectedIndex = 0;

    /**
     * ObservableList of associated parts for the product being modified.
     */
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    // Declare Methods

    /**
     * Initializes the partsTableView with data from the allParts list and updates the table view if the list is not empty.
     * @param url The location of the FXML file.
     * @param resourceBundle The resources required for this view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bind the items property of the partsTableView to the allParts list
        partsTableView.setItems(Inventory.getAllParts());
        // If the allParts list is not empty, update partsTableView
        if (!Inventory.getAllParts().isEmpty()) {
            updatePartsTableView(Inventory.getAllParts());
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
     * Update the associated parts table view with the given list of associated parts.
     * @param associatedParts the list of associated parts to display in the table view
     */
    private void updateAssociatedPartsTableView(ObservableList<Part> associatedParts) {
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * This method receives the selected product from the MainForm and sets the text field values with
     * the selected product's assigned values.
     * @param index the index of the selected product in the list.
     * @param selectedProduct the product that was selected from the list.
     */
    public void receiveSelectedProduct(int index, Product selectedProduct) {
        selectedIndex = index;

        // Set text in fields
        idField.setText(String.valueOf(selectedProduct.getId()));
        nameField.setText(selectedProduct.getName());
        invField.setText(String.valueOf(selectedProduct.getStock()));
        priceField.setText(String.valueOf(selectedProduct.getPrice()));
        maxField.setText(String.valueOf(selectedProduct.getMax()));
        minField.setText(String.valueOf(selectedProduct.getMin()));
        // Get the associated parts for the selected product and update the table view accordingly
        tempAssociatedParts = selectedProduct.getAllAssociatedParts();
        associatedPartsTableView.setItems(tempAssociatedParts);
        updateAssociatedPartsTableView(tempAssociatedParts);

    }
    /**
     * Returns to the Main Form screen when the cancel button is clicked
     * @param actionEvent the click event on the "Cancel" button
     */
    @FXML
    void cancelButtonClick(ActionEvent actionEvent) {
        SceneSwap.swapScene("/view/MainForm.fxml", cancelButton);
    }

    /**
     * Adds the selected part to the associated parts list when the add selected part button is clicked
     * @param actionEvent the click event on the "Add" button
     */
    @FXML
    void onAddSelectedPartButtonClick(ActionEvent actionEvent) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        tempAssociatedParts.add(selectedPart);
        updateAssociatedPartsTableView(tempAssociatedParts);
        removeAssociatedPartButton.setDisable(false);
    }
    /**
     * Removes the selected associated part when the remove associated part button is clicked
     * @param actionEvent the click event on the "Remove Associated Part" button
     */
    @FXML
    void onRemoveAssociatedPartButtonClick(ActionEvent actionEvent) {

        // Create a confirmation alert to check if the user really wants to remove the associated part
        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove the associated part?");

        // Show the confirmation dialog and get the user's choice
        Optional<ButtonType> result = confirmDeletion.showAndWait();

        // If the user confirms the deletion and a part is selected in the associatedPartsTableView, remove the part from the temporary list of associated parts
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
            tempAssociatedParts.remove(selectedPart);
        }

        // Disable the Remove Associated Part button if the temporary list of associated parts is empty
        if (tempAssociatedParts.isEmpty()){
            removeAssociatedPartButton.setDisable(true);
        }

    }


    /**
     * Saves a new product with user inputs after checking for errors and validating user input. If input is valid, creates a
     * new product with given data, updates the associated parts list and updates the inventory with the modified
     * product. Finally, swaps back to the Main screen.
     * @param actionEvent the click event on the "Save" button
     */
    @FXML
    void onSaveProductButtonClick(ActionEvent actionEvent) {

        /*
        // Can be uncommented to deny saving a product with no associated parts.
        // Check the tempAssociatedParts list. If it is empty, alert the user to add a part and try again.
        if(tempAssociatedParts.isEmpty()) {
            Alert requireAssociatedParts = new Alert(AlertType.INFORMATION, "Each product requires at least one associated part. Please add an associated part and try saving again.");
            requireAssociatedParts.showAndWait();
            return;
        }
         */

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
            // Create the new product with the extracted data and update the associated parts list for the new product.
            Product newProduct = new Product(extractedPartId, extractedName, extractedPrice, extractedInv, extractedMin, extractedMax);
            newProduct.getAllAssociatedParts().addAll(tempAssociatedParts);
            // Update the product at the selectedIndex with the modified product data. Then update the associated parts table view with the modified associated parts list.
            Inventory.updateProduct(selectedIndex, newProduct);

            updateAssociatedPartsTableView(tempAssociatedParts);
            // Go back to the Main screen.
            SceneSwap.swapScene("/view/MainForm.fxml", saveProductButton);
        } else {
            // If input is invalid, display a detailed error message to the user
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields contain invalid values.\n" + inputErrorMessage);
            inputErrorAlert.showAndWait();
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
