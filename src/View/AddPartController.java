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
import java.util.ArrayList;
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
public class AddPartController implements Initializable {

    Inventory inventory;
    
    // initializing for radio buttons
    @FXML RadioButton inHouseRbtn;
    @FXML RadioButton outSourcedRbtn;
            
   
    
    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField invTxt;
    @FXML private TextField costTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField minTxt;
    @FXML private TextField MachIDtxt;
    @FXML private Label machOrCompLbl;
    
    // used for input validation in save method
    private int min, max, inv, machID, id;
    private double price;
    
    // changing the label depending on which radio is selected
    public void radioSelected(ActionEvent event){
        if(inHouseRbtn.isSelected()){
            machOrCompLbl.setText("Machine ID");
        }else{
            machOrCompLbl.setText("Company Name");
        }

    }
    
    // going back to the main screen if cancel button is selected
    @FXML
    void cancelAddParts(MouseEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Cancel and return to main page.");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait(); //conferming with user
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

    // checking and saving the added parts. once saved, it will go back to main screen
    @FXML
    void saveAddParts(MouseEvent event) {
        //if(idTxt.getText().isEmpty()){
           // notificationWindows(1);
           // return;
        if(nameTxt.getText().isEmpty()){
            errorWindow(2);
            return;
        }if(invTxt.getText().isEmpty()){
            errorWindow(10);
            return;
        }if(maxTxt.getText().isEmpty()){
            errorWindow(4);
            return;
        }if(minTxt.getText().isEmpty()){
            errorWindow(5);
            return;
        }if(costTxt.getText().isEmpty()){
            errorWindow(3);
            return;
        }if(MachIDtxt.getText().isEmpty()){
            errorWindow(9);
            return;
        }
        try{   // trying to parce the entered values into an int or double for price
            min = Integer.parseInt(minTxt.getText());
            max = Integer.parseInt(maxTxt.getText());
            inv = Integer.parseInt(invTxt.getText());
            id = Integer.parseInt(idTxt.getText());
            price = Double.parseDouble(costTxt.getText());
        }catch(NumberFormatException e){
            errorWindow(11);
            return;
        }
        if(min >= max){ 
            errorWindow(6);
            return;
        }if(inv < min || inv > max){
            errorWindow(7);
            return;
        }
        // if the in-house radio is selected 
        if(inHouseRbtn.isSelected()){
            try{
                machID = Integer.parseInt(MachIDtxt.getText());
            }catch(NumberFormatException e){
                errorWindow(11);
                return;
            }
            Part l = new InHouse(id, nameTxt.getText(), price, inv, min, max, machID);
            inventory.addPart(l);
         // if out sourced radio button is selected    
        }if(outSourcedRbtn.isSelected()){
            Part t = new OutSourced(id, nameTxt.getText(), price, inv, min, max, MachIDtxt.getText());
            inventory.addPart(t);
        }
        // going back to main screen once part is added
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLmain.fxml"));
            View.MainScreenController controller = new MainScreenController(inventory);
            
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
    // constructor 
    AddPartController(Inventory inv) {
        this.inventory = inv;
    }
    // error windows
    public void errorWindow(int i){
        Alert alert = new Alert(AlertType.INFORMATION);
        if(i == 1){
            alert.setTitle("Missing partID");
            alert.setHeaderText(null);
            alert.setContentText("Enter Part ID");
            alert.showAndWait();
        }if(i == 2){
            alert.setTitle("Missing part name!");
            alert.setHeaderText(null);
            alert.setContentText("Enter part name"); 
            alert.showAndWait();
        }if(i == 3){
            alert.setTitle("Enter Price!");
            alert.setHeaderText(null);
            alert.setContentText("Enter price of item"); 
            alert.showAndWait();  
        }if(i == 4){
            alert.setTitle("Max items not entered!");
            alert.setHeaderText(null);
            alert.setContentText("Enter max items"); 
            alert.showAndWait();
        }if(i == 5){
            alert.setTitle("Min items not entered!");
            alert.setHeaderText(null);
            alert.setContentText("Enter min items"); 
            alert.showAndWait();
        }if(i == 6){
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Min cant be more then max."); 
            alert.showAndWait();
        }if(i == 7){
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Inv cant be more then max or less than min."); 
            alert.showAndWait();  
        }if(i == 8){
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Inv cant be less then min."); 
            alert.showAndWait();
        }if(i == 9){
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Machine ID annd Company name cant be empty."); 
            alert.showAndWait();
        }if(i == 10){
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Must enter inv"); 
            alert.showAndWait();
        }if(i == 11){
            alert.setTitle("Invalid Entry!");
            alert.setHeaderText(null);
            alert.setContentText("Must enter Integer value for max, min, Machine ID, and inv.\nEnter a double for cost"); 
            alert.showAndWait();
        }
    }
    
  


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //creating the next id field
        idTxt.setEditable(false);
        idTxt.setText(String.valueOf(getNextId()));
        
    }
    // used for generating the next id for the part
    public int getNextId(){
        int i = 0;
        for(Part p: inventory.getAllPartsList()){
            if(p.getPartID() > i){
                i = p.getPartID();
            }
        }
        return i + 1;
    }
    
}
