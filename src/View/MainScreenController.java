/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author yonij
 */
public class MainScreenController implements Initializable {
    
    Inventory inv;
   
  
    @FXML
    private TextField partsTxt;

    @FXML
    private TextField productTxt;

    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableView<Product> productTable;
    

   // used for the populating the tables with the search results
    private ObservableList<Part> tempPartOBSList = FXCollections.observableArrayList();
    private ObservableList<Product> temProductOBSList = FXCollections.observableArrayList();
    

    
   
    //constructor 
    public MainScreenController(Inventory inv){
        this.inv = inv;
    }
    
    //Initializing the tables
    @Override
    public void initialize(URL url, ResourceBundle rb){
         
        // populating the parts table
        partsTable.setItems(inv.getAllParts());
        // populating the product table
        productTable.setItems(inv.getAllProducts());
       
    }
    
      
    
    @FXML // exits the program
    private void exitProgram(MouseEvent event){
        Platform.exit();
    }
    
    // gets the value from the search box and if found it is added to tempPartOBSList and then
    // loaded onto the part table
    @FXML
    private void searchForPart(MouseEvent event){
          if(partsTxt.getText().trim().isEmpty() == false){
              tempPartOBSList.clear();
              for(Part p: inv.getAllPartsList()){
                  if(p.getPartName().contains(partsTxt.getText().trim())){
                      tempPartOBSList.add(p);
                  }
              }
              partsTable.setItems(tempPartOBSList);
              partsTable.refresh();
          }
    }
    // gets the value from the search box and if found it is added to temProductOBSList and then
    // loaded onto the product table
    @FXML
    private void searchForProduct(MouseEvent event){
        if(productTxt.getText().trim().isEmpty() == false){
            temProductOBSList.clear();
            for(Product p: inv.getAllProductsList()){
                if(p.getName().contains(productTxt.getText().trim())){
                    temProductOBSList.add(p);
                }    
            }
            productTable.setItems(temProductOBSList);
            productTable.refresh();
        }
    }
    
    // both search box for part and for product are registered to this event.
    // clears the box after it is clicked and resets the table
    @FXML 
    void reset(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (partsTxt == field) {
            if (inv.getAllParts().size() != 0) {
                partsTable.setItems(inv.getAllParts());
            }
        }
        if (productTxt == field) {
            if (inv.getAllProducts().size() != 0) {
                productTable.setItems(inv.getAllProducts());
            }
        }

    }

    // loads the addPart screan.fxml and passes the inventory as a paramater for the controller
    @FXML 
    void AddParts(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
            View.AddPartController controller = new AddPartController(inv);
            
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            
        }

    }
    
    
    // loads the AddProduct.fxml and passes inventory as a paramater for the controller
    @FXML
    void AddProduct(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
            View.AddProductController controller = new AddProductController(inv);
            
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            
        }
    }
    
    // popup windows for notifying the user when clicking "Modify part" or "Modify product"
    // without clicking on an item
    public void notificationWindows(int i){
        Alert alert = new Alert(AlertType.INFORMATION);
        if(i == 1){
            alert.setTitle("Missing parts!");
            alert.setHeaderText(null);
            alert.setContentText("You need to add Items");
            alert.showAndWait();
        }if(i == 2){
            alert.setTitle("No selection!");
            alert.setHeaderText(null);
            alert.setContentText("Select an item!"); 
            alert.showAndWait();
        }
    }
    
    // loads the ModifyPart.fxml file and passes the selected part and current inventory as a parameter 
    // for the controller
    @FXML
    private void modifyPart(MouseEvent event){
        try{
            Part selected = partsTable.getSelectionModel().getSelectedItem();
            if(inv.getAllParts().isEmpty()){
                notificationWindows(1);
                return;
            }if(inv.getAllParts().isEmpty() && selected == null){
                notificationWindows(1);
                return;
            }if(selected == null){
                notificationWindows(2);
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            View.ModifyPartController controller = new ModifyPartController(selected, inv);
            
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            return;
        }
        
    }
    
    // loads the ModifyProduct.fxml file and passes the selected product and current inventory as a parameter 
    // for the controller
    @FXML
    private void modifyProduct(MouseEvent event){
        try{
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if(inv.getAllProducts().isEmpty()){
                notificationWindows(1);
                return;
            }if(inv.getAllProducts().isEmpty() && selected == null){
                notificationWindows(1);
                return;
            }if(selected == null){
                notificationWindows(2);
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            View.ModifyProductController controller = new ModifyProductController(selected, inv);
            
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            return;
        }
        
    }
    
    // deleting a row from part table
    @FXML
    private void deletePart(MouseEvent event) {
        if(inv.getAllParts().isEmpty()){
            notificationWindows(1);
            return;
        }
        if(partsTable.getSelectionModel().getSelectedItem() == null && inv.getAllParts().isEmpty() ){
            notificationWindows(1);
            return;
        }if(partsTable.getSelectionModel().getSelectedItem() == null){
            notificationWindows(2);
            return;
        }
        // confirming with the user
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        Part i = partsTable.getSelectionModel().getSelectedItem();
        
        
        if (result.get() == ButtonType.OK) {
            inv.deletePart(i);
            partsTable.refresh();
            return;
        } else {
            return;
        }
        


    }
    
    // deleting a row from product table
    @FXML
    private void deleteProduct(MouseEvent event) {
        if(inv.getAllProducts().isEmpty()){
            notificationWindows(1);
            return;
        }
        if(productTable.getSelectionModel().getSelectedItem() == null && inv.getAllProducts().isEmpty() ){
            notificationWindows(1);
            return;
        }if(productTable.getSelectionModel().getSelectedItem() == null){
            notificationWindows(2);
            return;
        }
        // confirming with the user
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Product i = productTable.getSelectionModel().getSelectedItem();
            inv.deleteProduct(i);
            productTable.getItems().remove(i);
            productTable.refresh();
        } else {
            return;
        }

    }
    
        
}

   
    

