package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import utils.SceneSwap;
import utils.ValidateInput;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ModifyPartController class is a controller for the Modify Part form that contains methods to receive the selected
 * part to modify and initialize the form fields with the part's data. It validates user-entered changes to the part's data
 * and saves the modified part to Inventory.
 * <p></p>
 * <p><b>Runtime Error:</b></p>
 * I encountered a NumberFormatException while testing inputs for my Add/Modify part forms.
 * The issue was being caused by non-numeric inputs in fields that were not expecting non-numeric inputs. (Inventory, Price, Max, Min, MachineId)
 * I added some try-catch blocks to handle the exceptions and an error message to display to the user.
 * Once I made these changes, I no longer received this runtime error.
 */

public class ModifyPartController implements Initializable {

    // Declare Fields
    /**
     * Text field for the ID of the part being edited or added.
     */
    public TextField idField;

    /**
     * Text field for the name of the part being edited or added.
     */
    public TextField nameField;

    /**
     * Text field for the inventory level of the part being edited or added.
     */
    public TextField invField;

    /**
     * Text field for the price of the part being edited or added.
     */
    public TextField priceField;

    /**
     * Text field for the maximum number of the part being edited or added.
     */
    public TextField maxField;

    /**
     * Text field for the company name or machine ID of the part being edited or added, depending on whether it is an Outsourced or InHouse part.
     */
    public TextField companyNameOrMachineIDField;

    /**
     * Text field for the minimum number of the part being edited or added.
     */
    public TextField minField;

    /**
     * Button for saving changes to the part being edited or adding the new part.
     */
    public Button savePartButton;

    /**
     * Label for the company name or machine ID text field, depending on whether the part being edited or added is an Outsourced or InHouse part.
     */
    @FXML
    private Text labelCompanyNameOrMachineID;

    /**
     * Radio button for selecting that the part being edited or added is an Outsourced part.
     */
    @FXML
    private RadioButton radioOutsourced;

    /**
     * Radio button for selecting that the part being edited or added is an InHouse part.
     */
    @FXML
    private RadioButton radioInHouse;

    /**
     * Button for canceling changes to the part being edited or cancelling the addition of the new part.
     */
    @FXML
    private Button cancelButton;

    /**
     * The index of the part being edited or added in the list of all parts.
     */
    private int selectedIndex = 0;

    // Declare Methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * This method receives the selected part from the MainForm and sets the radio button, labels and text fields according to the
     * type of part, and it's attribute values.
     * @param index the index of the selected part in the list.
     * @param selectedPart the part that was selected from the list.
     */
    public void receiveSelectedPart(int index, Part selectedPart) {
        selectedIndex = index;

        // Set text in fields
        idField.setText(String.valueOf(selectedPart.getId()));
        nameField.setText(selectedPart.getName());
        invField.setText(String.valueOf(selectedPart.getStock()));
        priceField.setText(String.valueOf(selectedPart.getPrice()));
        maxField.setText(String.valueOf(selectedPart.getMax()));
        minField.setText(String.valueOf(selectedPart.getMin()));

        // Check which part class the object is an instance of. Adjust the label, text field, and radio button appropriately.
        if (selectedPart instanceof InHouse) {
            radioInHouse.setSelected(true);
            labelCompanyNameOrMachineID.setText("Machine ID");
            companyNameOrMachineIDField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced) {
            radioOutsourced.setSelected(true);
            labelCompanyNameOrMachineID.setText("Company Name");
            companyNameOrMachineIDField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }
    }

    /**
     * This method is triggered by the selection of the InHouse radio button. When the radio button is selected,
     * it de-selects the Outsourced radio button if needed, to ensure that only one radio button is selected at a time.
     * @param actionEvent the InHouse radio button is selected
     */
    public void selectedInHouse(ActionEvent actionEvent) {
        if(radioOutsourced.isSelected())
            radioOutsourced.setSelected(false);
        labelCompanyNameOrMachineID.setText("Machine ID");

    }
    /**
     * This method is triggered by the selection of the Outsourced radio button. When the radio button is selected,
     * it de-selects the InHouse radio button if needed, to ensure that only one radio button is selected at a time.
     * @param actionEvent the Outsourced radio button is selected
     */
    public void selectedOutsourced(ActionEvent actionEvent) {
        if(radioInHouse.isSelected())
            radioInHouse.setSelected(false);
        labelCompanyNameOrMachineID.setText("Company Name");
    }

    /**
     * This method changes the view from the ModifyPart view to the MainForm view.
     * @param actionEvent the click event on the "Cancel" button.
     */
    public void cancelButtonClick(ActionEvent actionEvent) {
        SceneSwap.swapScene("/view/MainForm.fxml", cancelButton);
    }

    /**
     * Saves modified part data after validating user input for empty fields, correct data format and part type selection.
     * If input is valid, replaces the selected part with a new Part object of the selected type using Inventory.updatePart().
     * If input is invalid, displays an alert to the user with instructions on how to correct their input.
     * @param actionEvent the click event on the "Save" button.
     */
    public void onSavePartButtonClick(ActionEvent actionEvent) {

        // Check that the user has selected a part type (InHouse or Outsourced)
        if(!(radioOutsourced.isSelected() || radioInHouse.isSelected())){
            // If not, display an error message to the user
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR,"Please select a radio button for an InHouse part or an Outsourced part.");
            inputErrorAlert.showAndWait();
            return;
        }

        // Check for any empty text fields.
        if(this.nameField.getText().isEmpty() || this.invField.getText().isEmpty() || this.priceField.getText().isEmpty() || this.maxField.getText().isEmpty() || this.minField.getText().isEmpty() || this.companyNameOrMachineIDField.getText().isEmpty()) {
            // If any empty fields exist, display an error message to the user
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields are empty.\nPlease fill the empty fields and try again.");
            inputErrorAlert.showAndWait();
            return;
        }

        // Parse and store input values. Check that appropriate data format has been provided by the user.
        int extractedPartId = Integer.parseInt(this.idField.getText());
        String extractedName = this.nameField.getText();
        int extractedInv;
        double extractedPrice;
        int extractedMax;
        int extractedMin;
        int extractedMachineID;
        String extractedCompanyName;

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


        if(radioOutsourced.isSelected()) {
            // Parse and store the company name for an outsourced part
            extractedCompanyName = this.companyNameOrMachineIDField.getText();
            // Validate the user input for an outsourced part and create a new outsourced part if input is valid
            String inputErrorMessage = ValidateInput.validateInputOutsourced(extractedName, extractedInv, extractedPrice, extractedMin, extractedMax, extractedCompanyName);
            // If input is valid, create a new Outsourced object, update the part in the Inventory, and switch back to the main form
            if (inputErrorMessage.isEmpty()){
                Part outsourcedPart = new Outsourced(extractedPartId,extractedName,extractedPrice,extractedInv,extractedMin, extractedMax, extractedCompanyName);
                Inventory.updatePart(selectedIndex,outsourcedPart);
                SceneSwap.swapScene("/view/MainForm.fxml", savePartButton);
            } else {
                // If input is invalid, display a detailed error message to the user
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields contain invalid values.\n" + inputErrorMessage);
                inputErrorAlert.showAndWait();
            }

        }

        if(radioInHouse.isSelected()) {
            try {
                // Parse and store the machine ID
                extractedMachineID = Integer.parseInt(companyNameOrMachineIDField.getText());
            } catch (NumberFormatException e) {
                // Display an error message to the user if the machine ID value is not a valid integer
                String errorMessage = "The Machine ID value entered must be an integer.";
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
                inputErrorAlert.showAndWait();
                return;
            }
            // Validate the user input for an in-house part and create a new in-house part if input is valid
            String inputErrorMessage = ValidateInput.validateInputInHouse(extractedName, extractedInv, extractedPrice, extractedMin, extractedMax, extractedMachineID);
            // If input is valid, create a new InHouse object, update the part in the Inventory, and switch back to the main form
            if (inputErrorMessage.isEmpty()) {
                Part inHousePart = new InHouse(extractedPartId, extractedName, extractedPrice, extractedInv, extractedMin, extractedMax, extractedMachineID);
                Inventory.updatePart(selectedIndex, inHousePart);
                SceneSwap.swapScene("/view/MainForm.fxml", savePartButton);
            } else {
                // If input is invalid, display an error message to the user
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields contain invalid values.\n" + inputErrorMessage);
                inputErrorAlert.showAndWait();
            }
        }
    }
}


