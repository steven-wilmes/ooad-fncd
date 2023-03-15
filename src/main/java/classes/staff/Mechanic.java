package classes.staff;

import classes.observerData.RepairOutcome;
import classes.vehicles.Vehicle;
import enums.Condition;

public class Mechanic extends Staff {
    /**
     * creates a new mechanic  and assigns their pay
     */
    public Mechanic(String name_) {
        super(name_);
        dailyPay = rng.nextInt(45) + 320;
    }
    
    public String getPosition() {
        return "Mechanic";
    }
    
    /**
     * repairs a vehicle, gives the mechanic a bonus if necessary
     *
     * @param vehicle_ vehicle to be repaired
     */
    public RepairOutcome repair(Vehicle vehicle_) {
        Condition prevCondition = vehicle_.getCondition();
        Boolean repairSuccessful = vehicle_.repair();
        if (repairSuccessful) { // if repaired
            this.giveBonus(vehicle_.getBonusAmount());
            main.Main.log(String.format("Mechanic %s fixed %s %s %d and made it %s (earned $%.2f bonus).",
                    this.name,
                    prevCondition.getStr(),
                    vehicle_.getStr(),
                    vehicle_.getVehicleNo(),
                    vehicle_.getCondition().getStr(),
                    vehicle_.getBonusAmount()));
        } else {
            main.Main.log(String.format("Mechanic %s tried to fix %s %s %d and did not succeed.",
                    this.name,
                    prevCondition.getStr(),
                    vehicle_.getStr(),
                    vehicle_.getVehicleNo()));
        }
        return new RepairOutcome(repairSuccessful, this.name, prevCondition, vehicle_.getCondition(), vehicle_.getStr(), vehicle_.getVehicleNo(), vehicle_.getBonusAmount());
    }
}
