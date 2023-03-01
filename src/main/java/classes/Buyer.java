package classes;

import classes.vehicles.*;

import java.util.Random;

public class Buyer {
    double buyingChance;
    Class<? extends Vehicle> vehicleType;
    
    Random rng = new Random();
    
    public Buyer() {
        int buyingTypeChance = rng.nextInt(3);
        switch (buyingTypeChance) {
            case 0:
                this.buyingChance = 0.1; //Just Looking
                break;
            case 1:
                this.buyingChance = 0.4; //Wants One
                break;
            case 2:
                this.buyingChance = 0.7; //Needs One
                break;
        }
        
        int vehicleTypeChance = rng.nextInt(3);
        switch (vehicleTypeChance) {
            case 0:
                this.vehicleType = PerformanceCar.class;
                break;
            case 1:
                this.vehicleType = RegularCar.class;
                break;
            case 2:
                this.vehicleType = Pickup.class;
                break;
        }
    }
    
    public double getBuyingChance() {
        return this.buyingChance;
    }
    
    public Class<? extends Vehicle> getVehicleType() {
        return this.vehicleType;
    }
}
