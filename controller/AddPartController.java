package c482.inventory.controller;

import c482.inventory.model.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Objects;

/**
 *
 * @author Madison Maguire
 */

public class AddPartController {
    public RadioButton inHouseAddToggle;
    public RadioButton outsourcedAddToggle;
    public ToggleGroup addPartToggle;
    public TextField addPartNameField;
    public TextField addPartInvField;
    public TextField addPartPriceField;
    public TextField addPartMaxField;
    public TextField addPartMachineIDField;
    public TextField addPartMinField;
    public Text machineIDLabel;
    public Button addPartSaveButton;
    public Button addPartCancelButton;


    @FXML
    private void onInHouse() {
        machineIDLabel.setText("Machine ID");
    }

    @FXML
    private void onOutSourced() {
        machineIDLabel.setText("Company Name");
    }

    @FXML
    private void addNewPart() {
        int addMax;
        double addPrice;
        int addInv;
        int addMin;
        int addMachineID;
        int partId;

        if (Objects.equals(addPartNameField.getText(), "")){
            alertBox("Please Enter a part name.");
            return;
        }

        if (Objects.equals(addPartInvField.getText(), "")){
            alertBox("Please enter an inventory value.");
            return;
        }

        if (Objects.equals(addPartPriceField.getText(), "")){
            alertBox("Please enter a price.");
            return;
        }

        if (Objects.equals(addPartMaxField.getText(), "")){
            alertBox("Please enter a maximum inventory value.");
            return;
        }

        if (Objects.equals(addPartMinField.getText(), "")){
            alertBox("Please Enter a minimum inventory value.");
            return;
        }

        if (Objects.equals(addPartMachineIDField.getText(), "") && addPartToggle.getSelectedToggle() == inHouseAddToggle){
            alertBox("Please enter a Machine ID value.");
            return;
        }

        if (Objects.equals(addPartMachineIDField.getText(), "") && addPartToggle.getSelectedToggle() == outsourcedAddToggle){
            alertBox("Please enter a company name.");
            return;
        }

        String addName = addPartNameField.getText();

        try {
            addInv = Integer.parseInt(addPartInvField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid inventory number.");
            return;
        }

        try {
            addPrice = Double.parseDouble(addPartPriceField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid price.");
            return;
        }

        try {
            addMax = Integer.parseInt(addPartMaxField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid maximum inventory value.");
            return;
        }

        try {
            addMin = Integer.parseInt(addPartMinField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid minimum inventory value.");
            return;
        }

        if(addInv < addMin || addInv > addMax){
            alertBox("Inventory value must be between the minimum and maximum inventory values.");
            return;
        }

        if(addPartToggle.getSelectedToggle() == inHouseAddToggle) {
            try {
                addMachineID = Integer.parseInt(addPartMachineIDField.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid machine ID number");
                return;
            }
            partId = Inventory.getNextPartId();
            Part addPart = new InHouse(partId, addName, addPrice, addInv, addMin, addMax, addMachineID);
            Inventory.addPart(addPart);
        }
        else{
            partId = Inventory.getNextPartId();
            String addCompanyName = addPartMachineIDField.getText();
            Part addPart = new Outsourced(partId, addName, addPrice, addInv, addMin, addMax, addCompanyName);
            Inventory.addPart(addPart);
        }
        Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addPartCancelClick(){
        Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param message the message to display
     */
    @FXML
    private void alertBox(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

