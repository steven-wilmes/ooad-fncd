package classes.staff;

import classes.Buyer;
import classes.observerData.Tuple;
import classes.vehicles.*;
import classes.vehicles.addon.ExtendedWarranty;
import classes.vehicles.addon.RoadRescueCoverage;
import classes.vehicles.addon.SatelliteRadio;
import classes.vehicles.addon.Undercoating;
import enums.Cleanliness;
import enums.Condition;
import main.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Salesperson extends Staff {
    /**
     * creates a new salesperson with a random name and pay amount
     */
    public Salesperson(String name_) {
        super(name_);
        dailyPay = rng.nextInt(45) + 400;
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
    public Tuple sell(Buyer buyer_, ArrayList<Vehicle> vehicleInventory_) {
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
            //try to upsell them baby
            //fixme should these be logged?
            if (rng.nextInt(100) < 25) {
                toSell = new ExtendedWarranty(toSell);
            }
            if (rng.nextInt(100) < 10) {
                toSell = new Undercoating(toSell);
            }
            if (rng.nextInt(100) < 2) {
                toSell = new RoadRescueCoverage(toSell);
            }
            if (rng.nextInt(100) < 40) {
                toSell = new SatelliteRadio(toSell);
            }
            this.giveBonus(toSell.getBonusAmount());
            Main.log(String.format("Salesperson %s sold %s %s %s (VIN #%d) to Buyer for $%.2f (earned $%.2f bonus)",
                    this.name,
                    toSell.getCleanliness().getStr(),
                    toSell.getCondition().getStr(),
                    toSell.getStr(),
                    toSell.getVehicleNo(),
                    toSell.getSalesPrice(),
                    toSell.getBonusAmount()));
            return new Tuple(toSell, true);
        } else {
            Main.log(String.format("Salesperson %s attempted to sell %s %s %s (VIN #%d) to Buyer for $%.2f and did not succeed.",
                    this.name,
                    toSell.getCleanliness().getStr(),
                    toSell.getCondition().getStr(),
                    toSell.getStr(),
                    toSell.getVehicleNo(),
                    toSell.getSalesPrice()));
            return new Tuple(toSell, false);
        }
    }
    
    public boolean UISell(Vehicle toSell_, Scanner scan_) {
        Main.log(String.format("Vehicle %s Proceed with purchase? (Y/N)", toSell_.getDetails()));
        String usrResponse = scan_.nextLine();
        if (usrResponse.equalsIgnoreCase("N")) {
            return false;
        }
        Main.log("Would you like to add an extended warranty? (Y/N)");
        usrResponse = scan_.nextLine();
        if (usrResponse.equalsIgnoreCase("Y")) {
            toSell_ = new ExtendedWarranty(toSell_);
        }
        Main.log("Would you like to add an undercoating? (Y/N)");
        usrResponse = scan_.nextLine();
        if (usrResponse.equalsIgnoreCase("Y")) {
            toSell_ = new Undercoating(toSell_);
        }
        Main.log("Would you like to add road rescue coverage? (Y/N)");
        usrResponse = scan_.nextLine();
        if (usrResponse.equalsIgnoreCase("Y")) {
            toSell_ = new RoadRescueCoverage(toSell_);
        }
        Main.log("Would you like to add satellite radio? (Y/N)");
        usrResponse = scan_.nextLine();
        if (usrResponse.equalsIgnoreCase("Y")) {
            toSell_ = new SatelliteRadio(toSell_);
        }
        
        this.giveBonus(toSell_.getBonusAmount());
        Main.log(String.format("Salesperson %s sold %s %s %s (VIN #%d) to User for $%.2f (earned $%.2f bonus)",
                this.name,
                toSell_.getCleanliness().getStr(),
                toSell_.getCondition().getStr(),
                toSell_.getStr(),
                toSell_.getVehicleNo(),
                toSell_.getSalesPrice(),
                toSell_.getBonusAmount()));
        return true;
    }
}
