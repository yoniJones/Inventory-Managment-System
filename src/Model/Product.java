/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author yonij
 */
public class Product {
    private ArrayList<Part> p = new ArrayList<Part>();
    private ObservableList <Part> associatedParts;
    private int productID;
    private String name;
    private double price = 0.0;
    private int inStock = 0;
    private int min;
    private int max;

   // associatedParts = FXCollections.observableList(p);
    public Product(int productID, String name, double price, int inStock, int min, int max) {
        this.associatedParts = FXCollections.observableList(p);
        setName(name);
        setMin(min);
        setMax(max);
        setProductID(productID);
        setInStock(inStock);
        setPrice(price);
    }

    public Product() {
        this.associatedParts = FXCollections.observableList(p);
    }

    
    
    public int getProductID() {
        return this.productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return this.inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public void addAssociatedPart(Part partToAdd){
        associatedParts.add(partToAdd);
    }
    

    
    public void deleteAssociatedPart(Part p){
        associatedParts.remove(p);
    }
  


    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
 

}
