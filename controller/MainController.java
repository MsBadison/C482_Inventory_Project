package c482.inventory.controller;

import c482.inventory.main.InventoryApp;
import c482.inventory.model.Part;
import c482.inventory.model.Product;
import c482.inventory.model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Madison Maguire
 */


public class MainController implements Initializable {
    public Button addPartButton;
    public Button addProductButton;
    public Button modifyPartButton;
    public Button modifyProductButton;
    public TableView<Part> partsTable = new TableView<>();
    public TableColumn<Part, Integer> partIdColumn = new TableColumn<>();
    public TableColumn<Part, String> partNameColumn = new TableColumn<>();
    public TableColumn<Part, Integer> partInvColumn = new TableColumn<>();
    public TableColumn<Part, Double> partPriceColumn = new TableColumn<>();
    public TableView<Product> productsTable = new TableView<>();
    public TableColumn<Product, Integer> productIdColumn = new TableColumn<>();
    public TableColumn<Product, String> productNameColumn = new TableColumn<>();
    public TableColumn<Product, Integer> productInvColumn = new TableColumn<>();
    public TableColumn<Product, Double> productPriceColumn = new TableColumn<>();
    public Button deletePartButton;
    public TextField partSearchField;
    public TextField productSearchField;
    public Button exitButton;
    public Button deleteProductButton;
    public Text noPartsFoundText;
    public Text noProductsFoundText;


    @FXML
    private void addPartButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("/addPart-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Stage addPart = new Stage();
        addPart.initModality(Modality.APPLICATION_MODAL);
        addPart.setTitle("Add Part");
        addPart.setScene(scene);
        addPart.showAndWait();
    }

    @FXML
    private void addProductButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("/addProduct-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage addProduct = new Stage();
        addProduct.initModality(Modality.APPLICATION_MODAL);
        addProduct.setTitle("Add Product");
        addProduct.setScene(scene);
        addProduct.showAndWait();
    }

    @FXML
    private void modifyPartButtonClick() throws IOException {
        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            alertBox("Please select a part to modify.");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("/modifyPart-view.fxml"));
            Part selModPart = partsTable.getSelectionModel().getSelectedItem();
            ModifyPartController.modifyPart(selModPart);
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage modifyPart = new Stage();
            modifyPart.initModality(Modality.APPLICATION_MODAL);
            modifyPart.setTitle("Modify Part");
            modifyPart.setScene(scene);
            modifyPart.show();
        }
    }

    @FXML
    private void modifyProductButtonClick() throws IOException {
        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            alertBox("Please select a product to modify.");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("/modifyProduct-view.fxml"));
            Product selModProduct = productsTable.getSelectionModel().getSelectedItem();
            ModifyProductController.modifyProduct(selModProduct);
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage modifyProduct = new Stage();
            modifyProduct.initModality(Modality.APPLICATION_MODAL);
            modifyProduct.setTitle("Modify Product");
            modifyProduct.setScene(scene);
            modifyProduct.show();
        }
    }


    @FXML
    private void deletePartButtonClick() {
        Part selDelPart = partsTable.getSelectionModel().getSelectedItem();

        if (selDelPart == null) {
            alertBox("Please select a part to delete.");
            return;
        }
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if(Inventory.deletePart(selDelPart)) {
                    partsTable.setItems(Inventory.getAllParts());
                    partsTable.refresh();
                    partSearchField.setText("");
                }
            } else {
                alert.close();
            }
        }
    }

    /**
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
        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private ObservableList<Part> searchParts(String partSearchTerm) {
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
    void searchParts() {
        String query = partSearchField.getText().toLowerCase();
        noPartsFoundText.setText("");
        ObservableList<Part> partSearchResult = searchParts(query);
        partsTable.setItems(partSearchResult);
        if (partSearchResult.isEmpty()) {
            noPartsFoundText.setText("No parts found");
        }
    }

    private ObservableList<Product> searchProducts(String productSearchTerm) {
        ObservableList<Product> productSearchResults = FXCollections.observableArrayList();
        ObservableList<Product> productList = Inventory.getAllProducts();
        for (Product searchProduct : productList) {

            if (searchProduct.getName().toLowerCase().contains(productSearchTerm) || (String.valueOf(searchProduct.getId()).equals(productSearchTerm))) {
                productSearchResults.add(searchProduct);
            }
        }
        return productSearchResults;
    }

    @FXML
    void searchProducts() {
        noProductsFoundText.setText("");
        String query = productSearchField.getText().toLowerCase();
        ObservableList<Product> productSearchResult = searchProducts(query);
        productsTable.setItems(productSearchResult);
        if (productSearchResult.isEmpty()) {
            noProductsFoundText.setText("No products found");
        }
    }

    public void exitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Quit application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            alert.close();
        }
    }

    /**
     *
     */
    public void deleteProductButtonClick() {
        Product selDelProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selDelProduct == null) {
            alertBox("Please select a product to delete.");
            return;
        }

        if (!selDelProduct.getAllAssociatedParts().isEmpty()){
            alertBox("You must remove associated parts before deleting this product.");
        return;
        }

        {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
             {
                if(Inventory.deleteProduct(selDelProduct)) {
                    productsTable.setItems(Inventory.getAllProducts());
                    productsTable.refresh();
                    productSearchField.setText("");
                }
            }
        } else {
            alert.close();
        }
        }
    }
}


