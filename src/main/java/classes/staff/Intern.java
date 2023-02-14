package classes.staff;

import classes.vehicles.Vehicle;
import enums.VehicleType;
import main.Main;

public class Intern extends Staff {
    /**
     * creates a new intern with a random name
     */
    public Intern(){
        super();
        dailyPay = 12; // TODO pick pay amounts
    }
    
    /**
     * Washes a vehicle, and gives the intern a bonus if the vehicle becomes sparkling
     * @param vehicle_ vehicle to be washed
     */
    public void wash(Vehicle vehicle_){
        if(vehicle_.wash()){ // if the vehicle becomes sparkling
            this.giveBonus(vehicle_.getBonusAmount());
            Main.log(String.format("Intern %s washed %s %s %d and made it Sparkling (earned %d bonus).",
                    this.name,
                    vehicle_.getCondition().getStr(),
                    VehicleType.match(vehicle_.getClass()).getStr(),
                    vehicle_.getVehicleNo(),
                    vehicle_.getBonusAmount()));
        }else{
            Main.log(String.format("Intern %s washed %s %s %d and made it %s.",
                    this.name,
                    vehicle_.getCondition().getStr(),
                    VehicleType.match(vehicle_.getClass()).getStr(),
                    vehicle_.getVehicleNo(),
                    vehicle_.getCleanliness().getStr()));
        }
        
    }
    
    /**
     * Promotes the intern to either {@link Salesperson} or {@link Mechanic}. Sets {@link #employed} to false.
     * @param isMechanic_ whether to create a mechanic or salesperson
     * @return the new {@link Staff}
     */
    public Staff promote(boolean isMechanic_){
        employed = false;
        if (isMechanic_){
            Main.log(String.format("Intern %s has been promoted to Mechanic", this.name));
            return new Mechanic(name);
        }else{
            Main.log(String.format("Intern %s has been promoted to Salesperson", this.name));
            return new Salesperson(name);
        }
    }
}
