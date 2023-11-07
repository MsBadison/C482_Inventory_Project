package c482.inventory.controller;

import c482.inventory.model.InHouse;
import c482.inventory.model.Inventory;
import c482.inventory.model.Outsourced;
import c482.inventory.model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 * @author Madison Maguire
 */

public class ModifyPartController implements Initializable {

    private static Part modifiedPart = null;
    public ToggleGroup modifyPartToggle;
    public RadioButton modifyInHouseButton;
    public RadioButton modifyOutsourcedButton;
    public TextField modifyIDField;
    public TextField modifyNameField;
    public TextField modifyInvField;
    public TextField modifyPriceField;
    public TextField modifyMaxField;
    public TextField modifyMachineIDField;
    public TextField modifyMinField;
    public Text machineIDLabel;
    public Button modifyCancelButton;
    public Button modifySaveButton;
    public int partID;


    public static void modifyPart(Part part){
        modifiedPart = part;
    }

    public void setInHouseModify(Part part){
        modifyIDField.setText(String.valueOf(part.getId()));
        modifyNameField.setText(part.getName());
        modifyInvField.setText(String.valueOf(part.getStock()));
        modifyPriceField.setText(String.valueOf(part.getPrice()));
        modifyMaxField.setText(String.valueOf(part.getMax()));
        modifyMinField.setText(String.valueOf(part.getMin()));
        modifyMachineIDField.setText(String.valueOf(((InHouse) part).getMachineId()));
        partID = Inventory.getAllParts().indexOf(part);
    }

    public void setOutsourcedModify(Part part){
        modifyIDField.setText(String.valueOf(part.getId()));
        modifyNameField.setText(part.getName());
        modifyInvField.setText(String.valueOf(part.getStock()));
        modifyPriceField.setText(String.valueOf(part.getPrice()));
        modifyMaxField.setText(String.valueOf(part.getMax()));
        modifyMinField.setText(String.valueOf(part.getMin()));
        modifyMachineIDField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        partID = Inventory.getAllParts().indexOf(part);
    }

    @FXML
    public void onModInHouse() {
        modifyPartToggle.selectToggle(modifyInHouseButton);
        machineIDLabel.setText("Machine ID");
    }

    @FXML
    public void onModOutSourced() {
        modifyPartToggle.selectToggle(modifyOutsourcedButton);
        machineIDLabel.setText("Company Name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (modifiedPart instanceof InHouse){
            onModInHouse();
            setInHouseModify(modifiedPart);
        }
        else {
            onModOutSourced();
            setOutsourcedModify(modifiedPart);
        }

    }

    public void modifyPartSave() {
        int partId;
        String newName = modifyNameField.getText();
        int newInv;
        double newPrice;
        int newMax;
        int newMin;
        int newMachineID;

        if (Objects.equals(modifyNameField.getText(), "")){
            alertBox("Please Enter a part name.");
            return;
        }

        if (Objects.equals(modifyInvField.getText(), "")){
            alertBox("Please enter an inventory value.");
            return;
        }

        if (Objects.equals(modifyPriceField.getText(), "")){
            alertBox("Please enter a price.");
            return;
        }

        if (Objects.equals(modifyMaxField.getText(), "")){
            alertBox("Please enter a maximum inventory value.");
            return;
        }

        if (Objects.equals(modifyMinField.getText(), "")){
            alertBox("Please Enter a minimum inventory value.");
            return;
        }

        if (Objects.equals(modifyMachineIDField.getText(), "") && (modifyPartToggle.getSelectedToggle() == modifyInHouseButton)){
            alertBox("Please enter a Machine ID value.");
            return;
        }

        if (Objects.equals(modifyMachineIDField.getText(), "") && (modifyPartToggle.getSelectedToggle() == modifyOutsourcedButton)){
            alertBox("Please enter a company name.");
            return;
        }

        try {
            newInv = Integer.parseInt(modifyInvField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid inventory number.");
            return;
        }

        try {
            newPrice = Double.parseDouble(modifyPriceField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid price.");
            return;
        }

        try {
            newMax = Integer.parseInt(modifyMaxField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid maximum inventory value.");
            return;
        }

        try {
            newMin = Integer.parseInt(modifyMinField.getText());
        } catch (NumberFormatException nfe) {
            alertBox("Please enter a valid minimum inventory value.");
            return;
        }

        if(newInv < newMin || newInv > newMax){
            alertBox("Inventory value must be between the minimum and maximum inventory values.");
            return;
        }

        if(modifyPartToggle.getSelectedToggle() == modifyInHouseButton) {
            try {
                newMachineID = Integer.parseInt(modifyNameField.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid machine ID number");
                return;
            }
            partId = Inventory.getNextPartId();
            Part addPart = new InHouse(partId, newName, newPrice, newInv, newMin, newMax, newMachineID);
            Inventory.addPart(addPart);
        }
        else {
            partId = Inventory.getNextPartId();
            String addCompanyName = modifyMachineIDField.getText();
            Part addPart = new Outsourced(partId, newName, newPrice, newInv, newMin, newMax, addCompanyName);
            Inventory.addPart(addPart);
        }
        Stage stage = (Stage) modifySaveButton.getScene().getWindow();
        stage.close();
    }

    public void cancelModifyPart() {
        Stage stage = (Stage) modifyCancelButton.getScene().getWindow();
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

