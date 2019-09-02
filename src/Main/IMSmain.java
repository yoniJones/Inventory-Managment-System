package Main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jonathan Jones
 * Course: Software 1 - C842
 * Purpose: to create an inventory for a small manufacturing business
 * Date: 8/27/2019
 */
public class IMSmain extends Application {
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addDataForTesting(inv);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLmain.fxml"));
        View.MainScreenController controller = new View.MainScreenController(inv);
     
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    // adding data
    void addDataForTesting(Inventory inv){
             // inHouse data 
        Part pa1 = new InHouse(1, "Part 1", 3.23, 12, 3, 92, 120);
        Part pa2 = new InHouse(2, "Part 2", 3.26, 15, 1, 92, 112);
        Part pa3 = new InHouse(3, "Part 3", 2.34, 11, 2, 83, 114);
        Part pa4 = new InHouse(4, "Part 4", 2.87, 16, 5, 90, 112);
        Part pa5 = new InHouse(5, "Part 5", 6.99, 10, 5, 101, 105);
        inv.addPart(pa1);
        inv.addPart(pa2);
        inv.addPart(pa3);
        inv.addPart(pa4);
        inv.addPart(pa5);
    
        
        // adding outsource parts
        Part pa6 = new OutSourced(6, "Part 01", 4, 10, 5, 100, "ABC Parts");
        Part pa7 = new OutSourced(7, "Part p", 6.25, 9, 5, 100, "Parts and Stuff.");
        Part pa8 = new OutSourced(8, "Part Q", 5.9, 10, 5, 100, "Parts and stuff");
        Part pa9 = new OutSourced(9, "Part R", 3.45, 13, 2, 92, "24/7 Parts");
        Part pa10 = new OutSourced(10, "Part 02", 5.46, 19, 5, 111, "Awsome Parts");
        inv.addPart(pa6);
        inv.addPart(pa7);
        inv.addPart(pa8);
        inv.addPart(pa9);
        inv.addPart(pa10);
        
      
        
        // adding products
        Product pr1 = new Product(1, "Product 1", 23.43, 20, 3, 434);
        pr1.addAssociatedPart(pa1);
        pr1.addAssociatedPart(pa6);
        inv.addProduct(pr1);
        
        Product pr2 = new Product(2, "Product 2", 52.4, 29, 5, 24);
        pr2.addAssociatedPart(pa2);
        pr2.addAssociatedPart(pa7);
        inv.addProduct(pr2);
        
        Product pr3 = new Product(3, "Product 3", 25.6, 30, 2, 300);
        pr3.addAssociatedPart(pa3);
        pr3.addAssociatedPart(pa8);
        inv.addProduct(pr3);
        
        Product pr4 = new Product(4, "Product 4", 27.4, 24, 2, 23);
        pr4.addAssociatedPart(pa4);
        pr4.addAssociatedPart(pa8);
        inv.addProduct(pr4);
           
   
        
    }
    
    
}
