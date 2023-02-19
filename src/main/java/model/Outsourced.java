package model;

/**
 * This class extends the Part class and represents an outsourced part.
 */
public class Outsourced extends Part {
    // Declare Fields
    private String companyName;

    // Declare Constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    // Declare Methods
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return companyName;
    }
}
