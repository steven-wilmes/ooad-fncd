package classes.observerData;

import enums.Cleanliness;
import enums.Condition;

public class SaleOutcome {
    Boolean sold;
    String salespersonName;
    Cleanliness cleanliness;
    Condition condition;
    String vehicleType;
    int vehicleNum;
    double salesPrice;
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
