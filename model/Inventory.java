package c482.inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Madison Maguire
 */

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 0;

    /**
     * @param part the min to add
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     * @param product the product to add
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     *
     * @param partId the ID of the part to search
     * @return the parts found
     */
    public static Part lookupPart(int partId){
        Part searchResult = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                searchResult = part;
            }
        }
        return searchResult;
    }

    /**
     *
     * @param partName the name of the part to search
     * @return the parts found
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().equals(partName)) {
                searchResults.add(part);
            }
        }
        return searchResults;
    }

    /**
     *
     * @param productId the product ID of the searched for product
     * @return the search results
     */
    public static Product lookupProduct(int productId){
        Product searchResults = null;

        for (Product product : allProducts){
            if (product.getId() == productId){
                searchResults = product;
            }
        }
        return searchResults;
    }

    /**
     * @param index, the selected part index
     * @param selectedPart, the part to be updated
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * @param index, the selected product index
     * @param selectedProduct, the product to be updated
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     *
     * @param part the part to be deleted
     * @return boolean
     */
    public static boolean deletePart(Part part) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == part.getId()){
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param product the product to be deleted
     * @return boolean
     */
    public static boolean deleteProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == product.getId()){
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return the list of all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return the list of all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     *
     * @return the next available part ID
     */
    public static int getNextPartId(){
        return ++partId;
    }

    /**
     * @return the next available product ID
     */
    public static int getNextProductId(){
        return ++productId;
    }
}

