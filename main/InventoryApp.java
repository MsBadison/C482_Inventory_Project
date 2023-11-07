package c482.inventory.main;

import c482.inventory.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Madison Maguire
 */

public class InventoryApp extends Application {
    /**
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     */
    public static void main(String[] args) {
        int partId = Inventory.getNextPartId();
        Part tire = new InHouse(partId, "Tire", 49.99, 10, 1, 20, 4);
        partId = Inventory.getNextPartId();
        Part chain = new Outsourced(partId, "Chain", 19.99, 30, 1, 50, "Acme");
        partId = Inventory.getNextPartId();
        Part oil = new Outsourced(partId, "Oil", 4.99, 100, 1, 100, "Mobil");
        Inventory.addPart(tire);
        Inventory.addPart(chain);
        Inventory.addPart(oil);

        int productID = Inventory.getNextProductId();
        Product bike = new Product(productID, "Bike", 199.95, 5, 1, 10);
        productID = Inventory.getNextProductId();
        Product motor = new Product(productID, "Motor", 89.99, 2, 1, 5);
        Inventory.addProduct(bike);
        Inventory.addProduct(motor);

        launch();
    }
}