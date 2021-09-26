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
public class OutSourced extends Part {
    
    private String companyName;

    public OutSourced(int partID, String name, double price, int numInStock, int min, int max, String company){
        setPartID(partID);
        setPartName(name);
        setPartPrice(price);
        setPartInStock(numInStock);
        setMin(min);
        setMax(max);
        setCompanyName(company);     
    }

    public OutSourced() {
    }
    
    
    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    


    
}
