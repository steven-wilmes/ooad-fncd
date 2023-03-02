package classes.observerData;

import enums.Cleanliness;

public class WashOutcome {
    /**
     * whether or not the was resulted in a sparkling vehicle
     */
    Boolean washed;
    /**
     * the intern's name
     */
    String internName;
    /**
     * the wash strategy employed
     */
    String washType;
    /**
     * the cleanliness before washing
     */
    Cleanliness initCleanliness;
    /**
     * the cleanliness after washing
     */
    Cleanliness resultCleanliness;
    /**
     * the type of vehicle
     */
    String vehicleType;
    /**
     * the vehicle's identifying number
     */
    int vehicleNum;
    /**
     * the bonus associated with the vehicle
     */
    double bonusAmt;
    /**
     * any extra messages from the cleaning strategy
     */
    String extraMsg;
    
    public WashOutcome(Boolean washed_, String internName_, String washType_, Cleanliness initCleanliness_, Cleanliness resultCleanliness_, String vehicleType_, int vehicleNum_, double bonusAmt_, String extraMsg_) {
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
    
    public Boolean getWashed() {
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
