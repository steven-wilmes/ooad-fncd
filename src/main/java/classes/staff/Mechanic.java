package classes.staff;

import classes.vehicles.Vehicle;

public class Mechanic extends Staff {
    /**
     * creates a new mechanic with a random name
     */
    public Mechanic(){
        super();
        dailyPay = 20; // TODO pick pay amounts
    }
    
    /**
     * creates a new mechanic with a specific name
     * @param name_ name for mechanic
     */
    public Mechanic(String name_){
        this();
        name = name_;
    }
    
    /**
     * repairs a vehicle, gives the mechanic a bonus if necessary
     * @param vehicle_ vehicle to be repaired
     */
    public void repair(Vehicle vehicle_){
        if(vehicle_.repair()){ // if repaired
            this.giveBonus(vehicle_.getBonusAmount());
        }
    }
}
