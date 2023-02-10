package classes.staff;

import classes.vehicles.Vehicle;
import classes.Buyer;

import java.util.ArrayList;

public class Salesperson extends Staff {
    /**
     * creates a new salesperson with a random name
     */
    public Salesperson(){
        super();
        dailyPay = 25; // TODO pick pay amounts
    }
    
    /**
     * creates a new salesperson with a specific name
     * @param name_ the name for the salesperson
     */
    public Salesperson(String name_){
        this();
        name = name_;
    }
    
    /**
     * sells a vehicle from the inventory
     * @param buyer_ the buyer to sell to
     * @param vehicleInventory_ the current vehicle inventory
     * @return the sold vehicle if the sale is successful, else null
     */
    public Vehicle sell(Buyer buyer_, ArrayList<Vehicle> vehicleInventory_){
        Vehicle soldVehicle = vehicleInventory_.get(0);
        // TODO implement
        return soldVehicle;
    }
}
