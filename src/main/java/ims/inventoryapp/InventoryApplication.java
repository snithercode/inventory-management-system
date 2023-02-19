package ims.inventoryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;


import java.io.IOException;

/**
 * The InventoryApplication class is the entry point of the inventory management application. It includes methods for
 * loading the main screen and launching the application.
 */
public class InventoryApplication extends Application {

    /**
     * Loads the FXML file for the main screen of the application and creates the primary stage, then launches the application.
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Launches the application and populates inventory lists with sample parts, products, and associated parts.
     * @param args
     */
    public static void main(String[] args) {

        // Below is some sample data I added and used for testing purposes.

        // Parts
        InHouse part1 = new InHouse(1,"Dagger Hilt",3.44,6,4,16,107);
        InHouse part2 = new InHouse(2, "Spearhead", 18.99, 10, 5, 20, 101);
        InHouse part3 = new InHouse(3, "Spear Shaft", 12.99, 15, 10, 25, 102);
        Outsourced part4 = new Outsourced(4,"Iron Dagger Blade",38.87,3,2,10,"Precision Edge Co.");
        Outsourced part5 = new Outsourced(5, "Crossbow Limb", 28.99, 5, 2, 10, "Pilfercraft");
        Outsourced part6 = new Outsourced(6, "Crossbow String", 6.99, 20, 10, 30, "Sentry Inc.");
        InHouse part7 = new InHouse(7, "Shield Boss", 9.99, 25, 15, 30, 103);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);

        // Products
        Product product1 = new Product(1, "Iron Dagger", 54.50, 4, 2, 10);
        Product product2 = new Product(2, "Spear",110.20, 2, 1,4);
        Product product3 = new Product(3, "Windlass Crossbow", 82.80,3,2,8);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        // Associated Parts
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part4);

        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);

        product3.addAssociatedPart(part5);
        product3.addAssociatedPart(part6);
        product3.addAssociatedPart(part7);

        launch();
    }
}