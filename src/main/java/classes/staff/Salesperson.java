package classes.staff;

import classes.Buyer;
import classes.vehicles.Vehicle;
import enums.Cleanliness;
import enums.Condition;
import main.Main;

import java.util.ArrayList;
import java.util.Comparator;

public class Salesperson extends Staff {
    /**
     * creates a new salesperson with a random name and pay amount
     */
    public Salesperson() {
        super();
        dailyPay = rng.nextInt(45) + 400;
    }
    
    /**
     * creates a new salesperson with a specific name
     *
     * @param name_ the name for the salesperson
     */
    public Salesperson(String name_) {
        this();
        name = name_;
    }
    
    public String getPosition() {
        return "Salesperson";
    }
    
    /**
     * sells a vehicle from the inventory
     *
     * @param buyer_            the buyer to sell to
     * @param vehicleInventory_ the current vehicle inventory
     * @return the sold vehicle if the sale is successful, else null
     */
    public Vehicle sell(Buyer buyer_, ArrayList<Vehicle> vehicleInventory_) {
        ArrayList<Vehicle> desiredVehicleList = new ArrayList<>();
        ArrayList<Vehicle> sellableVehicles = new ArrayList<>();
        double saleChance = buyer_.getBuyingChance();
        Class<? extends Vehicle> desiredType = buyer_.getVehicleType(); // get the desired class
        for (Vehicle v_ : vehicleInventory_) { // check if each vehicle is of the desired class and not broken
            if (v_.getCondition() != Condition.BROKEN) {
                sellableVehicles.add(v_); // broken vehicles cannot be sold
            } else if (v_.getClass() == desiredType) {
                desiredVehicleList.add(v_); // matches the desired type of car
            }
        }
        
        // determine vehicle to sell
        Vehicle toSell;
        if (desiredVehicleList.size() == 0) { // no desired vehicles, sell most expensive
            saleChance -= .2;
            toSell = sellableVehicles.stream().max(Comparator.comparing(v -> v.getSalesPrice())).get(); // max implementation from https://stackoverflow.com/questions/19338686/getting-max-value-from-an-arraylist-of-objects
        } else {
            // desired vehicles exist, sell most expensive
            toSell = desiredVehicleList.stream().max(Comparator.comparing(Vehicle::getSalesPrice)).get();
        }
        
        // determine sale chance based on status
        if (toSell.getCondition() == Condition.LIKE_NEW) {
            saleChance += .1;
        }
        
        if (toSell.getCleanliness() == Cleanliness.SPARKLING) {
            saleChance += .1;
        }
        
        // attempt sale
        if (rng.nextDouble() <= saleChance) { // random double between 0 and 1, if it's below the sale chance the sale succeeds
            this.giveBonus(toSell.getBonusAmount());
            Main.log(String.format("Salesperson %s sold %s %s %s %d to Buyer for $%.2f (earned $%.2f bonus)",
                    this.name,
                    toSell.getCleanliness().getStr(),
                    toSell.getCondition().getStr(),
                    toSell.getStr(),
                    toSell.getVehicleNo(),
                    toSell.getSalesPrice(),
                    toSell.getBonusAmount()));
            return toSell;
        } else {
            Main.log(String.format("Salesperson %s attempted to sell %s %s %s %d to Buyer for $%.2f and did not succeed.",
                    this.name,
                    toSell.getCleanliness().getStr(),
                    toSell.getCondition().getStr(),
                    toSell.getStr(),
                    toSell.getVehicleNo(),
                    toSell.getSalesPrice()));
            return null;
        }
        
    }
}
