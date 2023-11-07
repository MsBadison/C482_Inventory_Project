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

public class AddProductController implements Initializable {

    public TableView<Part> addProductAllParts = new TableView<>();
    public TableColumn<Part, Integer> addProductPartIdAll = new TableColumn<>();
    public TableColumn<Part, String> addProductPartNameAll = new TableColumn<>();
    public TableColumn<Part, Integer> addProductPartInvAll = new TableColumn<>();
    public TableColumn<Part, Double> addProductPartPriceAll = new TableColumn<>();
    public TableView<Part> addProductPart = new TableView<>();
    public TableColumn<Part, Integer> addProductPartId = new TableColumn<>();
    public TableColumn<Part, String> addProductPartName = new TableColumn<>();
    public TableColumn<Part, Integer> addProductPartInv = new TableColumn<>();
    public TableColumn<Part, Double> addProductPartPrice = new TableColumn<>();
    public TextField addProductSearchBox;
    public TextField addProductID;
    public TextField addProductName;
    public TextField addProductInv;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;
    public Button addProductAddPartButton;
    public Button addProductRemovePartButton;
    public Button addProductCancelButton;
    public Button addProductSaveButton;
    public Text addProductNoPartsFoundText;
    @FXML
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addProductAllParts.setItems(Inventory.getAllParts());
        addProductPartIdAll.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameAll.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartInvAll.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceAll.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     *
     * @param partSearchTerm the termed entered into the search box
     * @return partSearchResults
     */
    private ObservableList<Part> addProductPartSearch(String partSearchTerm) {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        ObservableList<Part> partsList = Inventory.getAllParts();
        for (Part searchPart : partsList) {

            if (searchPart.getName().toLowerCase().contains(partSearchTerm) || (String.valueOf(searchPart.getId()).equals(partSearchTerm))) {
                partSearchResults.add(searchPart);
            }
        }
        return partSearchResults;
    }

    @FXML
    private void addProductPartSearch() {
        addProductNoPartsFoundText.setText("");
        String query = addProductSearchBox.getText().toLowerCase();
        ObservableList<Part> partSearchResult = addProductPartSearch(query);
        addProductAllParts.setItems(partSearchResult);
        if (partSearchResult.isEmpty()) {
            addProductNoPartsFoundText.setText("No parts found");
        }
    }

    @FXML
    private void addProductAddPart() {
        Part selectedPart = addProductAllParts.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            alertBox("Please select a part to add.");
            return;
        }
        associatedParts.add(selectedPart);
        addProductPart.setItems(associatedParts);
    }

    @FXML
    private void addProductRemovePart() {
        Part selectedPart = addProductPart.getSelectionModel().getSelectedItem();

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
            addProductPart.setItems(associatedParts);
        } else {
            alert.close();
        }
    }

    @FXML
    private void addProductCancel() {
        Stage stage = (Stage) addProductCancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param message the message to be displayed
     */
    @FXML
    private void alertBox(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void addProductSave() {
        int addId;
        String addName;
        int addInv;
        double addPrice;
        int addMax;
        int addMin;

        if(Objects.equals(addProductName.getText(), "")){
            alertBox("Please enter a product name.");
            return;
        }
        addName = addProductName.getText();


        if(Objects.equals(addProductInv.getText(), "")){
            alertBox("Please enter an inventory value.");
            return;
        }
        else{
            try {
                Integer.parseInt(addProductInv.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid inventory number.");
                return;
            }
        }
        addInv = Integer.parseInt(addProductInv.getText());

        if(Objects.equals(addProductPrice.getText(), "")){
            alertBox("Please enter a price value.");
            return;
        }
        else{
            try {
                Double.parseDouble(addProductPrice.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid price value.");
                return;
            }
        }
        addPrice = Double.parseDouble(addProductPrice.getText());

        if(Objects.equals(addProductMax.getText(), "")){
            alertBox("Please enter a maximum value.");
            return;
        }
        else{
            try {
                Integer.parseInt(addProductMax.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid maximum value.");
                return;
            }
        }
        addMax = Integer.parseInt(addProductMax.getText());

        if(Objects.equals(addProductMin.getText(), "")){
            alertBox("Please enter a minimum value.");
            return;
        }
        else{
            try {
                Integer.parseInt(addProductMin.getText());
            } catch (NumberFormatException nfe) {
                alertBox("Please enter a valid minimum value.");
                return;
            }
        }
        addMin = Integer.parseInt(addProductMin.getText());

        addId = Inventory.getNextProductId();

        Product newProduct = new Product(addId, addName, addPrice, addInv, addMin, addMax);

        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }

        Inventory.addProduct(newProduct);

        Stage stage = (Stage) addProductSaveButton.getScene().getWindow();
        stage.close();
    }
}