/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author yonij
 */
public abstract class Part {
    protected int partID;
    protected String partName;
    protected double partPrice;
    protected int partInStock;
    protected int min;
    protected int max;

    public int getPartID() {
        return this.partID;
    }

    public String getPartName() {
        return this.partName;
    }

    public double getPartPrice() {
        return this.partPrice;
    }

    public int getPartInStock() {
        return this.partInStock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    public void setPartInStock(int partInStock) {
        this.partInStock = partInStock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    
    
}
