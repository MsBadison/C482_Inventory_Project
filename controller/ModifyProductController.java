package c482.inventory.controller;

import c482.inventory.model.Inventory;
import c482.inventory.model.Part;
import c482.inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Madison Maguire
 */


public class ModifyProductController implements Initializable{

    private static Product modifiedProduct = null;
    public TableView<Part> modifyProductAllPartsTable = new TableView<>();
    public TableColumn<Part, Integer> modifyProductAllPartIdColumn = new TableColumn<>();
    public TableColumn<Part, String> modifyProductAllPartNameColumn = new TableColumn<>();
    public TableColumn<Part, Integer>  modifyProductAllPartInvColumn = new TableColumn<>();
    public TableColumn<Part, Double>  modifyProductAllPartPriceColumn = new TableColumn<>();
    public TextField modifyProductSearchField;
    public Button modifyProductAddPartButton;
    public TableView<Part> modifyProductPartTable = new TableView<>();
    public TableColumn<Part, Integer> modifyProductPartIdColumn = new TableColumn<>();
    public TableColumn<Part, String> modifyProductPartNameColumn = new TableColumn<>();
    public TableColumn<Part, Integer>  modifyProductPartInvColumn = new TableColumn<>();
    public TableColumn<Part, Double>  modifyProductPartPriceColumn = new TableColumn<>();
    public Button modifyProductRemovePartButton;
    public Button modifyProductCancelButton;
    public Button modifyProductSaveButton;
    public TextField modifyProductId;
    public TextField modifyProductName;
    public TextField modifyProductInv;
    public TextField modifyProductPrice;
    public TextField modifyProductMax;
    public TextField modifyProductMin;
    public Text modifyProductNoPartsFoundText;
    ObservableList<Part> associatedParts;

    public static void modifyProduct(Product product) {
        modifiedProduct = product;
    }

    public void setModifiedProductInfo(Product product) {
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
    }

    public void modifyProductAddPartButtonClick() {
        Part selectedPart = modifyProductAllPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            alertBox("Please select a part to add.");
            return;
        }
        associatedParts.add(selectedPart);
        modifyProductPartTable.setItems(associatedParts);
    }

    public void modifyProductRemovePartButtonClick() {
        Part selectedPart = modifyProductPartTable.getSelectionModel().getSelectedItem();

        if(associatedParts.isEmpty()){
            alertBox("There are no parts associated with this product.");
            return;
        }

        if(selectedPart == null){
            alertBox("Please select a part to remove.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Remove part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
            modifyProductPartTable.setItems(associatedParts);
        } else {
            alert.close();
        }
    }

    public void modifyProductCancelButton() {
        Stage stage = (Stage) modifyProductCancelButton.getScene().getWindow();
        stage.close();
    }

    public void modifyProductSaveButtonClick() {
        int newId;
        String newName;
        int newInv;
        double newPrice;
        int newMax;
        int newMin;

        if(Objects.equals(modifyProductName.getText(), "")){
            alertBox("Please enter a product name.");
            return;
        }
        newName = modifyProductName.getText();

        if(Objects.equals(modifyProductInv.getText(), "")){
            alertBox("Please enter an inventory value.");
            return;
        }
        else{
            try {
                Integer.parseInt(modifyProductInv.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid inventory number.");
                return;
            }
        }
        newInv = Integer.parseInt(modifyProductInv.getText());

        if(Objects.equals(modifyProductPrice.getText(), "")){
            alertBox("Please enter a price value.");
            return;
        }
        else{
            try {
                Double.parseDouble(modifyProductPrice.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid price value.");
                return;
            }
        }
        newPrice = Double.parseDouble(modifyProductPrice.getText());

        if(Objects.equals(modifyProductMax.getText(), "")){
            alertBox("Please enter a maximum value.");
            return;
        }
        else{
            try {
                Integer.parseInt(modifyProductMax.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid maximum value.");
                return;
            }
        }
        newMax = Integer.parseInt(modifyProductMax.getText());

        if(Objects.equals(modifyProductMin.getText(), "")){
            alertBox("Please enter a minimum value.");
            return;
        }
        else{
            try {
                Integer.parseInt(modifyProductMin.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid minimum value.");
                return;
            }
        }
        newMin = Integer.parseInt(modifyProductMin.getText());

        newId = Integer.parseInt(modifyProductId.getText());
        Inventory.deleteProduct(modifiedProduct);

        Product newProduct = new Product(newId, newName, newPrice, newInv, newMin, newMax);
        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }
        Inventory.addProduct(newProduct);

        Stage stage = (Stage) modifyProductSaveButton.getScene().getWindow();
        stage.close();
    }

    private ObservableList<Part> modifyProductSearch(String partSearchTerm) {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        ObservableList<Part> partsList = Inventory.getAllParts();
        for (Part searchPart : partsList) {

            if (searchPart.getName().toLowerCase().contains(partSearchTerm) || (String.valueOf(searchPart.getId()).equals(partSearchTerm))) {
                partSearchResults.add(searchPart);
            }
        }
        return partSearchResults;
    }

    public void modifyProductSearch() {
        modifyProductNoPartsFoundText.setText("");
        String query = modifyProductSearchField.getText().toLowerCase();
        ObservableList<Part> partSearchResult = modifyProductSearch(query);
        modifyProductAllPartsTable.setItems(partSearchResult);
        if (partSearchResult.isEmpty()) {
            modifyProductNoPartsFoundText.setText("No parts found");
        }
    }

    private void copyParts(ObservableList<Part> parts){
        this.associatedParts = FXCollections.observableArrayList(parts);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setModifiedProductInfo(modifiedProduct);

        modifyProductAllPartsTable.setItems(Inventory.getAllParts());
        modifyProductAllPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAllPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAllPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAllPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        copyParts(modifiedProduct.getAllAssociatedParts());

        modifyProductPartTable.setItems(associatedParts);
        modifyProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
