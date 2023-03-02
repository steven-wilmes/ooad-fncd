package classes.observerData;

public class RaceOutcome {
    
    /**
     * the place of the racing pair
     */
    int place;
    /**
     * the driver's name
     */
    String driverName;
    /**
     * the stage name for the monster truck. only used for monster trucks
     */
    String mtStageName;
    /**
     * the string vehicle type
     */
    String vehicleType;
    /**
     * the vehicle number
     */
    int vehicleNum;
    /**
     * the bonus amount associated with the vehicle
     */
    double bonusAmt;
    /**
     * whether or not the driver was injured in the race
     */
    Boolean driverInjured;
    
    public RaceOutcome(int place_, String driverName_, String vehicleType_, int vehicleNum_, double bonusAmt_, Boolean driverInjured_) {
        place = place_;
        driverName = driverName_;
        vehicleType = vehicleType_;
        vehicleNum = vehicleNum_;
        bonusAmt = bonusAmt_;
        driverInjured = driverInjured_;
    }
    
    public void addMTStageName(String mtStageName_) {
        mtStageName = mtStageName_;
    }
    
    public int getPlace() {
        return place;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    /**
     *
     * @return the stage name if instantiated, otherwise "No"
     */
    public String getMtStageName() {
        if (mtStageName != null) {
            return mtStageName;
        } else {
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
