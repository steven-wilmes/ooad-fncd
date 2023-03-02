package classes.observerData;

import classes.staff.Intern;
import classes.vehicles.Vehicle;
import enums.Cleanliness;

public class WashOutcome {
    Boolean washed;
    String internName;
    String washType;
    Cleanliness initCleanliness;
    Cleanliness resultCleanliness;
    String vehicleType;
    int vehicleNum;
    double bonusAmt;
    String extraMsg;
    
    public WashOutcome(Boolean washed_, String internName_, String washType_, Cleanliness initCleanliness_, Cleanliness resultCleanliness_, String vehicleType_, int vehicleNum_, double bonusAmt_, String extraMsg_){
        washed = washed_;
        internName = internName_;
        washType = washType_;
        initCleanliness = initCleanliness_;
        resultCleanliness = resultCleanliness_;
        vehicleType = vehicleType_;
        vehicleNum = vehicleNum_;
        bonusAmt = bonusAmt_;
        extraMsg = extraMsg_;
    }
    
    public Boolean getWashed(){
        return washed;
    }
    
    public String getInternName() {
        return internName;
    }
    
    public String getWashType() {
        return washType;
    }
    
    public Cleanliness getInitCleanliness() {
        return initCleanliness;
    }
    
    public Cleanliness getResultCleanliness() {
        return resultCleanliness;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public int getVehicleNum() {
        return vehicleNum;
    }
    
    public double getBonusAmt() {
        return bonusAmt;
    }
    
    public String getExtraMsg() {
        return extraMsg;
    }
}
