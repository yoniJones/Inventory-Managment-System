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
public class Inventory {
    private ArrayList<Product> allProductsList;
    private ArrayList<Part> allPartsList;
    
    private ObservableList <Product> allProducts;
    private ObservableList <Part> allParts;

    public Inventory(){
        allProductsList = new ArrayList<>();
        allPartsList = new ArrayList<>();
        allProducts = FXCollections.observableList(allProductsList);
        allParts = FXCollections.observableList(allPartsList);
    }


    


    

    public ArrayList<Product> getAllProductsList() {
        return allProductsList;
    }

    public ArrayList<Part> getAllPartsList() {
        return allPartsList;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    
    
    
    public void addProduct(Product p){
        if(p != null){
            this.allProductsList.add(p);
        }
    }
    
    public boolean removeProduct(int p){
        for(int i = 0; i < allProductsList.size(); i++){
            if(allProductsList.get(i).getProductID() == p){
                allProductsList.remove(i);
                break;
            }else{
                return false;
            }    
        }
        return true;
    }
    
    public Product lookUpProduct(int p){
        if(!allProductsList.isEmpty()){
            for(int i = 0; i < allProductsList.size(); i++){
                if(allProductsList.get(i).getProductID() == p){
                    return allProductsList.get(i);
                }
            }
        }
        return null;
    }
    
    public Product lookUpProduct(String p){
        if(!allProductsList.isEmpty()){
            for(int i = 0; i < allProductsList.size(); i++){
                if(allProductsList.get(i).getName() == p){
                    return allProductsList.get(i);
                }
            }
        }
        return null;
    }
    
    public Part lookUpPart(int p){
        if(!allPartsList.isEmpty()){
            for(int i = 0; i < allPartsList.size(); i++){
                if(allPartsList.get(i).getPartID() == p){
                    return allPartsList.get(i);
                }
            }
        }
        return null;
    }
        
    public Part lookUpPart(String p){
        if(!allPartsList.isEmpty()){
            for(int i = 0; i < allPartsList.size(); i++){
                if(allPartsList.get(i).getPartName().toLowerCase() == p.toLowerCase()){
                    return allPartsList.get(i);
                }
            }
        }
        return null;
    }
    
  
    
    public void updateProduct(Product product){
        for(int i = 0; i < allProductsList.size(); i++){
            if(allProductsList.get(i).getProductID() == product.getProductID()){
                allProductsList.set(i, product);
                break;
            }
        }
        return;
    }
    
    public void updateProduct(int index, Product s){
        allProductsList.set(index, s);
    }
    
    public void updatePart(Part part){
        for(int i = 0; i < allPartsList.size(); i++){
            if(allPartsList.get(i).getPartID() == part.getPartID()){
                allPartsList.set(i, part);
                break;
            }
        }
        return;
    }
    
    public void updatePart(int index, Part p){
        allPartsList.set(index, p);
    }
    
    public void addPart(Part p){
        if(p != null){
            allPartsList.add(p);
        }
    }
   
    public boolean deletePart(Part p){
        for(int i = 0; i < allPartsList.size(); i++){
            if(allPartsList.get(i).getPartID() == p.getPartID()){
                allPartsList.remove(i);
                break;
            }
        }
        return true;
    }
    
    
    public boolean deleteProduct(Product p){
        for(int i = 0; i < allProductsList.size(); i++){
            if(allProductsList.get(i).getProductID() == p.getProductID()){
                allProductsList.remove(i);
                break;
            }
            
        }
        return true;
    }    
}
