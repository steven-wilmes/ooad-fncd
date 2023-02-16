package classes;

import enums.VehicleType;

import java.util.Random;

public class Buyer {
    double buyingChance;
    VehicleType vehicleType;
    
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
                this.vehicleType = VehicleType.PERFORMANCE_CAR;
                break;
            case 1:
                this.vehicleType = VehicleType.REGULAR_CAR;
                break;
            case 2:
                this.vehicleType = VehicleType.PICKUP;
                break;
        }
    }
    
    public double getBuyingChance() {
        return this.buyingChance;
    }
    
    public VehicleType getVehicleType() {
        return this.vehicleType;
    }
}
