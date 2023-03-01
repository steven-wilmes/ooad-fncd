package classes.staff;

import classes.vehicles.Vehicle;
import enums.Condition;

public class Mechanic extends Staff {
    /**
     * creates a new mechanic with a random name and assigns their pay
     */
    public Mechanic() {
        super();
        dailyPay = rng.nextInt(45) + 320;
    }
    
    /**
     * creates a new mechanic with a specific name
     *
     * @param name_ name for mechanic
     */
    public Mechanic(String name_) {
        this();
        name = name_;
    }
    
    public String getPosition() {
        return "Mechanic";
    }
    
    /**
     * repairs a vehicle, gives the mechanic a bonus if necessary
     *
     * @param vehicle_ vehicle to be repaired
     */
    public void repair(Vehicle vehicle_) {
        Condition prevCondition = vehicle_.getCondition();
        if (vehicle_.repair()) { // if repaired
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
    }
}
