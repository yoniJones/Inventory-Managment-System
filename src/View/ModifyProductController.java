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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * FXML Controller class
 *
 * @author yonij
 */
public class ModifyProductController implements Initializable {

    Inventory inventory;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField costTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField searchBoxTxt;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableView<Part> associatedPartTable;

    Product product;
    //ArrayList<Parts>   associatedList;

    // used for the populating the tables with the search results
    private ObservableList<Part> tempPartOBSList = FXCollections.observableArrayList();

    // used for input varification 
    private int min, max, inv, id;
    private double price;

    // constructor
    public ModifyProductController(Product selected, Inventory i) {
        this.product = selected;
        this.inventory = i;
    }

    // setting the fields with data from selected product
    public void setFields() {
        idTxt.setText(Integer.toString(product.getProductID()));
        idTxt.setEditable(false);
        nameTxt.setText(product.getName());
        invTxt.setText(Integer.toString(product.getInStock()));
        costTxt.setText(Double.toString(product.getPrice()));
        maxTxt.setText(Integer.toString(product.getMax()));
        minTxt.setText(Integer.toString(product.getMin()));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFields(); // setting the textfields
        associatedPartTable.setItems(product.getAssociatedParts());// populating the associatePartTable if any
        partTable.setItems(inventory.getAllParts());// populating the parts table

    }

    //adding a part from part table to associated part table.
    // associating part with product
    @FXML
    void addAssociatedPart(MouseEvent event) {
        Part selected = partTable.getSelectionModel().getSelectedItem();
        if (inventory.getAllParts().isEmpty()) {
            notificationWindows(12);
            return;
        }
        if (inventory.getAllParts().isEmpty() && selected == null) {
            notificationWindows(12);
            return;
        }
        if (selected == null) {
            notificationWindows(13);
            return;
        }
        if (!associatedPartTable.getItems().contains(selected)) {
            associatedPartTable.getItems().add(selected);
        }
    }

    // after user confirmation, main screen is loaded
    @FXML
    void cancel(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Cancel product modification");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLmain.fxml"));
                View.MainScreenController controller = new MainScreenController(inventory);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {

            }
        } else {
            return;
        }

    }
// after user confirmation, associated part is deleted frome table and disassociated from product

    @FXML
    void deleteAssociatedPart(MouseEvent event) {
        if (associatedPartTable.getSelectionModel().isEmpty()) {
            return;
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Remove associated part");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part i = associatedPartTable.getSelectionModel().getSelectedItem();
            associatedPartTable.getItems().remove(i);
            associatedPartTable.refresh();
            product.deleteAssociatedPart(i);
        } else {
            return;
        }

    }

    // after validating user inputs, product is modified, inventory is updated,
    // and user is directed to main screen 
    @FXML
    void saveProduct(MouseEvent event) {
        if (nameTxt.getText().isEmpty()) {
            notificationWindows(2);
            return;
        }
        if (invTxt.getText().isEmpty()) {
            notificationWindows(10);
            return;
        }
        if (maxTxt.getText().isEmpty()) {
            notificationWindows(4);
            return;
        }
        if (minTxt.getText().isEmpty()) {
            notificationWindows(5);
            return;
        }
        if (costTxt.getText().isEmpty()) {
            notificationWindows(3);
            return;
        }
        if (associatedPartTable.getItems().isEmpty()) {// verifies at least one associated part
            notificationWindows(14);
            return;
        }
        try {   // trying to parce the entered values into an int or double for price
            min = Integer.parseInt(minTxt.getText());
            max = Integer.parseInt(maxTxt.getText());
            inv = Integer.parseInt(invTxt.getText());
            id = Integer.parseInt(idTxt.getText());
            price = Double.parseDouble(costTxt.getText());
        } catch (NumberFormatException e) {
            notificationWindows(11);
            return;
        }
        if (min >= max) {
            notificationWindows(6);
            return;
        }
        if (inv < min || inv > max) {
            notificationWindows(7);
            return;
        }
        if (price < getTotalPartsCost()) { // varifies that all associated parts combined cost less than product
            notificationWindows(15);
            return;
        }

        product.setProductID(id);
        product.setName(nameTxt.getText());
        product.setPrice(price);
        product.setInStock(inv);
        product.setMin(min);
        product.setMax(max);
        inventory.updateProduct(product);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLmain.fxml"));
            View.MainScreenController controller = new MainScreenController(inventory);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }

    }

    // searching for part
    @FXML
    void searchPart(MouseEvent event) {
        if (searchBoxTxt.getText().trim().isEmpty() == false) {
            tempPartOBSList.clear();
            for (Part p : inventory.getAllPartsList()) {
                if (p.getPartName().contains(searchBoxTxt.getText().trim())) {
                    tempPartOBSList.add(p);
                }
            }
            partTable.setItems(tempPartOBSList);
            partTable.refresh();
        }
    }

    //clears the text and resets the table
    @FXML
    void reset(MouseEvent event) {
        searchBoxTxt.setText("");
        if (inventory.getAllParts().size() != 0) {
            partTable.setItems(inventory.getAllParts());
        }
    }

    // notification windows for invalid user inputs
    public void notificationWindows(int i) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (i == 1) {
            alert.setTitle("Missing partID");
            alert.setHeaderText(null);
            alert.setContentText("Enter Part ID");
            alert.showAndWait();
        }
        if (i == 2) {
            alert.setTitle("Missing part name!");
            alert.setHeaderText(null);
            alert.setContentText("Enter part name");
            alert.showAndWait();
        }
        if (i == 3) {
            alert.setTitle("Enter Price!");
            alert.setHeaderText(null);
            alert.setContentText("Enter price of item");
            alert.showAndWait();
        }
        if (i == 4) {
            alert.setTitle("Max items not entered!");
            alert.setHeaderText(null);
            alert.setContentText("Enter max items");
            alert.showAndWait();
        }
        if (i == 5) {
            alert.setTitle("Min items not entered!");
            alert.setHeaderText(null);
            alert.setContentText("Enter min items");
            alert.showAndWait();
        }
        if (i == 6) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Min can't be more then max.");
            alert.showAndWait();
        }
        if (i == 7) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Inv can't be more then max or less than min.");
            alert.showAndWait();
        }
        if (i == 8) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Inv can't be less then min.");
            alert.showAndWait();
        }
        if (i == 9) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Machine ID and Company name can't be empty.");
            alert.showAndWait();
        }
        if (i == 10) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Must enter inv");
            alert.showAndWait();
        }
        if (i == 11) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Must enter Integer value for max, min, Machine ID, and inv.\nEnter a double for cost");
            alert.showAndWait();
        }
        if (i == 12) {
            alert.setTitle("No Parts");
            alert.setHeaderText(null);
            alert.setContentText("Parts inventory is empty!");
            alert.showAndWait();
        }
        if (i == 13) {
            alert.setTitle("No slection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part!");
            alert.showAndWait();
        }
        if (i == 14) {
            alert.setTitle("Missing associated part");
            alert.setHeaderText(null);
            alert.setContentText("Product must have at least 1 associated part!");
            alert.showAndWait();
        }
        if (i == 15) {
            alert.setTitle("Parts cost!");
            alert.setHeaderText(null);
            alert.setContentText("The cost of the combined parts cannot be more than the product!");
            alert.showAndWait();
        }
    }

    // return the total cost of combined associated parts
    private double getTotalPartsCost() {
        double totalParts = 0;
        for (Part p : product.getAssociatedParts()) {
            totalParts += p.getPartPrice();
        }
        return totalParts;
    }

}
