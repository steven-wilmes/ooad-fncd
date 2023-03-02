package classes.observerData;

import classes.staff.Driver;
import classes.vehicles.Vehicle;

import java.util.ArrayList;

public class RaceOutcome {
    
    int place;
    String driverName;
    String mtStageName;
    String vehicleType;
    int vehicleNum;
    double bonusAmt;
    Boolean driverInjured;
    
    public RaceOutcome(int place_, String driverName_, String vehicleType_, int vehicleNum_, double bonusAmt_, Boolean driverInjured_) {
        place = place_;
        driverName = driverName_;
        vehicleType = vehicleType_;
        vehicleNum = vehicleNum_;
        bonusAmt = bonusAmt_;
        driverInjured = driverInjured_;
    }
    
    public void addMTStageName(String mtStageName_){
        mtStageName = mtStageName_;
    }
    
    public int getPlace() {
        return place;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public String getMtStageName() {
        if (mtStageName != null) {
            return mtStageName;
        }else{
            return "No";
        }
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
    
    public Boolean getDriverInjured() {
        return driverInjured;
    }
}
