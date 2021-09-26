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
public class InHouse extends Part {
    private int machineID;

    public InHouse(int partID, String name, double price, int numInStock, int min, int max, int machId){
        setPartID(partID);
        setPartName(name);
        setPartPrice(price);
        setPartInStock(numInStock);
        setMin(min);
        setMax(max);
        setMachineID(machId);
    }

    public InHouse() {
    }
    
  public void setMachineID(int id){
      this.machineID = id;
  }
  public int getMachineID(){
      return machineID;
  }

    
   
    
}
