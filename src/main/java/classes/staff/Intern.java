package classes.staff;

import classes.staff.WashBehavior.*;
import classes.vehicles.Vehicle;
import enums.Cleanliness;
import main.Main;

public class Intern extends Staff {
    WashBehavior washBehavior;
    /**
     * creates a new intern with a random name and assigns their pay
     */
    public Intern() {
        super();
        dailyPay = rng.nextInt(45) + 148;

        // OO Patterns: Strategy Pattern
        switch(rng.nextInt(3)){
            case 0:
                this.washBehavior = new ChemicalWash();
                break;
            case 1:
                this.washBehavior = new ElbowGrease();
                break;
            case 2:
                this.washBehavior = new DetailedWash();
                break;
        }
    }
    
    public String getPosition() {
        return "Intern";
    }
    
    /**
     * Washes a vehicle, and gives the intern a bonus if the vehicle becomes sparkling
     *
     * @param vehicle_ vehicle to be washed
     */
    public void wash(Vehicle vehicle_) {
        Cleanliness beforeWash = vehicle_.getCleanliness();
        String specialString = washBehavior.wash(vehicle_);
        if (vehicle_.getCleanliness() == Cleanliness.SPARKLING) { // if the vehicle becomes sparkling
            this.giveBonus(vehicle_.getBonusAmount());
            main.Main.log(String.format("Intern %s %s %s %s %d and made it Sparkling (earned $%.2f bonus).",
                    this.name,
                    this.washBehavior.getStr(),
                    beforeWash.getStr(),
                    vehicle_.getStr(),
                    vehicle_.getVehicleNo(),
                    vehicle_.getBonusAmount()));
        } else {
            main.Main.log(String.format("Intern %s %s %s %s %d and made it %s.",
                    this.name,
                    this.washBehavior.getStr(),
                    beforeWash.getStr(),
                    vehicle_.getStr(),
                    vehicle_.getVehicleNo(),
                    vehicle_.getCleanliness().getStr()));
        }
        if(specialString != "") {
            main.Main.log(specialString);
        }
    }
    
    /**
     * Promotes the intern to either {@link Salesperson} or {@link Mechanic}.
     *
     * @param isMechanic_ whether to create a mechanic or salesperson
     * @return the new {@link Staff}
     */
    public Staff promote(boolean isMechanic_) {
        if (isMechanic_) {
            Main.log(String.format("Intern %s has been promoted to Mechanic", this.name));
            return new Mechanic(name);
        } else {
            Main.log(String.format("Intern %s has been promoted to Salesperson", this.name));
            return new Salesperson(name);
        }
    }
}
