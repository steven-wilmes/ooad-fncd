package classes.observerData;

import enums.Cleanliness;
import enums.Condition;

public class SaleOutcome {
    /**
     * whether or not the sale was successful
     */
    Boolean sold;
    /**
     * the salesperson's name
     */
    String salespersonName;
    /**
     * the vehicle's cleanliness
     */
    Cleanliness cleanliness;
    /**
     * the vehicle's condition
     */
    Condition condition;
    /**
     * the type of vehicle
     */
    String vehicleType;
    /**
     * the vehicle's identifying number
     */
    int vehicleNum;
    /**
     * the sales price of the vehicle, with addons
     */
    double salesPrice;
    /**
     * the bonus associated with the vehicle
     */
    double bonusAmt;
    
    public SaleOutcome(Boolean sold_, String salespersonName_, Cleanliness cleanliness_, Condition condition_, String vehicleType_, int vehicleNum_, double salesPrice_, double bonusAmt_) {
        sold = sold_;
        salespersonName = salespersonName_;
        cleanliness = cleanliness_;
        condition = condition_;
        vehicleType = vehicleType_;
        vehicleNum = vehicleNum_;
        salesPrice = salesPrice_;
        bonusAmt = bonusAmt_;
    }
    
    public Boolean getSold() {
        return sold;
    }
    
    public String getSalespersonName() {
        return salespersonName;
    }
    
    public Cleanliness getCleanliness() {
        return cleanliness;
    }
    
    public Condition getCondition() {
        return condition;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public int getVehicleNum() {
        return vehicleNum;
    }
    
    public double getSalesPrice() {
        return salesPrice;
    }
    
    public double getBonusAmt() {
        return bonusAmt;
    }
}
