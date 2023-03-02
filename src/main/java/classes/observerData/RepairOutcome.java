package classes.observerData;

import enums.Condition;

public class RepairOutcome {
    Boolean repaired;
    String mechanicName;
    Condition initCondition;
    Condition resultCondition;
    String vehicleType;
    int vehicleNum;
    double bonusAmt;
    
    public RepairOutcome(Boolean repaired_, String mechanicName_, Condition initCondition_, Condition resultCondition_, String vehicleType_, int vehicleNum_, double bonusAmt_) {
        this.repaired = repaired_;
        this.mechanicName = mechanicName_;
        this.initCondition = initCondition_;
        this.resultCondition = resultCondition_;
        this.vehicleType = vehicleType_;
        this.vehicleNum = vehicleNum_;
        this.bonusAmt = bonusAmt_;
    }
    
    public Boolean getRepaired() {
        return repaired;
    }
    
    public String getMechanicName() {
        return mechanicName;
    }
    
    public Condition getInitCondition() {
        return initCondition;
    }
    
    public Condition getResultCondition() {
        return resultCondition;
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
}
