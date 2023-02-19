package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.InHouse;
import model.Outsourced;
import model.Inventory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import model.Part;
import utils.SceneSwap;
import utils.ValidateInput;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddPartController class is responsible for controlling the add part form, performing input validation, and
 * creating new part objects based on user input to add to inventory.
 */
public class AddPartController implements Initializable {

    // Declare Fields

    /**
     * The part ID text field.
     */
    @FXML
    private TextField idField;

    /**
     * The name text field.
     */
    @FXML
    private TextField nameField;

    /**
     * The inventory level text field.
     */
    @FXML
    private TextField invField;

    /**
     * The price text field.
     */
    @FXML
    private TextField priceField;

    /**
     * The maximum inventory level text field.
     */
    @FXML
    private TextField maxField;

    /**
     * The minimum inventory level text field.
     */
    @FXML
    private TextField minField;

    /**
     * The company name or machine ID text field.
     */
    @FXML
    private TextField companyNameOrMachineIDField;

    /**
     * The save part button.
     */
    @FXML
    private Button savePartButton;

    /**
     * The label for the company name or machine ID text field.
     */
    @FXML
    private Text labelCompanyNameOrMachineID;

    /**
     * The radio button for outsourced parts.
     */
    @FXML
    private RadioButton radioOutsourced;

    /**
     * The radio button for in-house parts.
     */
    @FXML
    private RadioButton radioInHouse;

    /**
     * The cancel button.
     */
    @FXML
    private Button cancelButton;

    /**
     * A temporary part ID for the current part being added.
     */
    private int tempPartId;

    // Declare Methods

    /**
     * Initializes the class and any required resources.
     * @param url The location of the FXML file.
     * @param resourceBundle The resources required for this view.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int lastIndex = Inventory.getAllParts().size() - 1;
        tempPartId = Inventory.getAllParts().get(lastIndex).getId() + 1;
    }

    /**
     * This method sets the fields and state of the form when the outsourced radio button is selected.
     * @param actionEvent The selection of the outsourced radio button.
     */
    public void selectedOutsourced(ActionEvent actionEvent) {
        if(radioInHouse.isSelected())
            radioInHouse.setSelected(false);
        labelCompanyNameOrMachineID.setText("Company Name");
        idField.setText(String.valueOf(tempPartId));
    }

    /**
     * This method sets the fields and state of the form when the in-house radio button is selected.
     * @param actionEvent The selection of the in-house radio button.
     */
    public void selectedInHouse(ActionEvent actionEvent) {
        if(radioOutsourced.isSelected())
            radioOutsourced.setSelected(false);
        labelCompanyNameOrMachineID.setText("Machine ID");
        idField.setText(String.valueOf(tempPartId));
    }

    /**
     * This method cancels the form and switches to the main form.
     * @param actionEvent The click event of the cancel button.
     */
    public void cancelButtonClick(ActionEvent actionEvent) {
        //if (tempPartId > Inventory.getPartId())
        //    tempPartId = tempPartId - 1;
        SceneSwap.swapScene("/view/MainForm.fxml", cancelButton);
    }

    /**
     * Handles the event triggered by the user clicking the "Save" button on the AddPart form.
     * This method validates the user's input for the new part, including checking for any empty fields,
     * ensuring that appropriate data formats have been provided by the user, and verifying that one of the radio buttons
     * (InHouse or Outsourced) has been selected for the type of part. If the input is valid, a new Part object of the selected type is created
     * and added to the Inventory's allParts list. If the input is invalid, an alert is displayed to the user with information on how to correct their input.
     * @param actionEvent the click event on the "Save" button.
     */
    public void onSavePartButtonClick(ActionEvent actionEvent) {

        // Check that the type of part has been selected
        if(!(radioOutsourced.isSelected() || radioInHouse.isSelected())){
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR,"Please select a radio button for an InHouse part or an Outsourced part.");
            inputErrorAlert.showAndWait();
            return;
        }

        // Check for any empty text fields prior to parsing input. If any empty fields exist, ask the user to fill in the missing input and try again.
        if(this.nameField.getText().isEmpty() || this.invField.getText().isEmpty() || this.priceField.getText().isEmpty() || this.maxField.getText().isEmpty() || this.minField.getText().isEmpty() || this.companyNameOrMachineIDField.getText().isEmpty()) {
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
        int extractedMachineID;
        String extractedCompanyName;

        try {
            extractedInv = Integer.parseInt(this.invField.getText());
        } catch (NumberFormatException e) {
            String errorMessage = "The inventory value entered must be an integer.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        try {
            extractedPrice = Double.parseDouble(this.priceField.getText());
        } catch (NumberFormatException e) {
            String errorMessage = "The price value entered must be an integer or decimal value.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        try {
            extractedMax = Integer.parseInt(this.maxField.getText());
        } catch (NumberFormatException e) {
            String errorMessage = "The max value entered must be an integer.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        try {
            extractedMin = Integer.parseInt(this.minField.getText());
        } catch (NumberFormatException e) {
            String errorMessage = "The min value entered must be an integer.";
            Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
            inputErrorAlert.showAndWait();
            return;
        }

        if(radioOutsourced.isSelected()) {
            extractedCompanyName = this.companyNameOrMachineIDField.getText();
            String outsourcedInputErrorMessage = ValidateInput.validateInputOutsourced(extractedName, extractedInv, extractedPrice, extractedMin, extractedMax, extractedCompanyName);
            if (outsourcedInputErrorMessage.isEmpty()) {
                Inventory.addPart(new Outsourced(extractedPartId, extractedName, extractedPrice, extractedInv, extractedMin, extractedMax, extractedCompanyName));
                SceneSwap.swapScene("/view/MainForm.fxml", savePartButton);
            } else {
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields contain invalid values:\n" + outsourcedInputErrorMessage);
                inputErrorAlert.showAndWait();
                return;
            }
        }

        if(radioInHouse.isSelected()) {
            try {
                extractedMachineID = Integer.parseInt(companyNameOrMachineIDField.getText());
            } catch (NumberFormatException e) {
                String errorMessage = "The Machine ID value entered must be an integer.";
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, errorMessage);
                inputErrorAlert.showAndWait();
                return;
            }
            String inHouseInputErrorMessage = ValidateInput.validateInputInHouse(extractedName, extractedInv, extractedPrice, extractedMin, extractedMax, extractedMachineID);
            if (inHouseInputErrorMessage.isEmpty()) {
                Inventory.addPart(new InHouse(extractedPartId, extractedName, extractedPrice, extractedInv, extractedMin, extractedMax, extractedMachineID));
                SceneSwap.swapScene("/view/MainForm.fxml", savePartButton);
            } else {
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR, "One or more fields contain invalid values:\n" + inHouseInputErrorMessage);
                inputErrorAlert.showAndWait();
                return;
            }
        }
    }
}
