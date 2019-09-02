/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yonij
 */
public class ModifyPartController implements Initializable {

    Inventory inventory;
    Part partSelected;

    @FXML
    private RadioButton inHouseRbtn;

    @FXML
    private RadioButton outSourcedRbtn;

    @FXML
    private Label companyNameLbl;

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
    private TextField companyNameTxt;

    // for validating inputs
    private int min, max, inv, machID, id;
    private double price;

    // cancel and go back to main screen after popup confirmation
    @FXML
    void cancelModifiedParts(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Cancel modify part");
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

    // change the the label to "Macine ID" or "Company Name"
    // depending on user selection
    @FXML
    void radioSelected(ActionEvent event) {
        if (inHouseRbtn.isSelected()) {
            companyNameLbl.setText("Machine ID");
        } else {
            companyNameLbl.setText("Company Name");
        }

    }

    // after validating all fields, the part is modified, inventory is updated,
    // and window return to main screen
    @FXML
    void saveModifiedParts(MouseEvent event) {
        if (nameTxt.getText().isEmpty()) {
            errorWindow(2);
            return;
        }
        if (invTxt.getText().isEmpty()) {
            errorWindow(10);
            return;
        }
        if (maxTxt.getText().isEmpty()) {
            errorWindow(4);
            return;
        }
        if (minTxt.getText().isEmpty()) {
            errorWindow(5);
            return;
        }
        if (costTxt.getText().isEmpty()) {
            errorWindow(3);
            return;
        }
        if (companyNameTxt.getText().isEmpty()) {
            errorWindow(9);
            return;
        }
        // trying to parce the entered values into an int or double for price
        try {
            min = Integer.parseInt(minTxt.getText());
            max = Integer.parseInt(maxTxt.getText());
            inv = Integer.parseInt(invTxt.getText());
            id = Integer.parseInt(idTxt.getText());
            price = Double.parseDouble(costTxt.getText());
        } catch (NumberFormatException e) {
            errorWindow(11);
            return;
        }
        if (min >= max) {
            errorWindow(6);
            return;
        }
        if (inv < min || inv > max) {
            errorWindow(7);
            return;
        }
        if (min < 0) {
            errorWindow(12);
        }
        // if the in-house radio is selected 
        if (inHouseRbtn.isSelected()) {
            try {
                machID = Integer.parseInt(companyNameTxt.getText());
            } catch (NumberFormatException e) {
                errorWindow(11);
                return;
            }

            Part l = new InHouse(id, nameTxt.getText(), price, inv, min, max, machID);
            inventory.updatePart(l);

        }// if out sourced radio button is selected   
        if (outSourcedRbtn.isSelected()) {
            Part t = new OutSourced(id, nameTxt.getText(), price, inv, min, max, companyNameTxt.getText());
            inventory.updatePart(t);
        }
        // going back to main screen once part is added
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

    // constructor
    public ModifyPartController(Part p, Inventory i) {
        this.partSelected = p;
        this.inventory = i;

    }

    // used to notify user of invalid inputs
    public void errorWindow(int i) {
        Alert alert = new Alert(AlertType.INFORMATION);
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
            alert.setContentText("Min cant be more then max.");
            alert.showAndWait();
        }
        if (i == 7) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Inv cant be more then max or less than min.");
            alert.showAndWait();
        }
        if (i == 8) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Inv cant be less then min.");
            alert.showAndWait();
        }
        if (i == 9) {
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Machine ID and Company name cant be empty.");
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
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Min cannot be less then 0!");
            alert.showAndWait();
        }
    }

    // populating the fields with selected part information
    public void setItems() {
        if (partSelected instanceof OutSourced) { // determines if part is OutSourced
            outSourcedRbtn.setSelected(true);
            companyNameLbl.setText("Company Name");
            OutSourced item = (OutSourced) partSelected;
            idTxt.setText(Integer.toString(item.getPartID()));
            idTxt.setEditable(false);
            nameTxt.setText(item.getPartName());
            invTxt.setText(Integer.toString(item.getPartInStock()));
            costTxt.setText(Double.toString(item.getPartPrice()));
            maxTxt.setText(Integer.toString(item.getMax()));
            minTxt.setText(Integer.toString(item.getMin()));
            idTxt.setText(Integer.toString(item.getPartID()));
            companyNameTxt.setText(item.getCompanyName());
        }
        if (partSelected instanceof InHouse) { // determines if part Is InHouse
            inHouseRbtn.setSelected(true);
            companyNameLbl.setText("Machine ID");
            InHouse itemA = (InHouse) partSelected;
            idTxt.setText(Integer.toString(itemA.getPartID()));
            idTxt.setEditable(false);
            nameTxt.setText(itemA.getPartName());
            invTxt.setText(Integer.toString(itemA.getPartInStock()));
            costTxt.setText(Double.toString(itemA.getPartPrice()));
            maxTxt.setText(Integer.toString(itemA.getMax()));
            minTxt.setText(Integer.toString(itemA.getMin()));
            idTxt.setText(Integer.toString(itemA.getPartID()));
            companyNameTxt.setText(Integer.toString(itemA.getMachineID()));

        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setItems();

    }

}
