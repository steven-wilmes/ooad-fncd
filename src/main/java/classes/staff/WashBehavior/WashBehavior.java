package classes.staff.WashBehavior;

import classes.vehicles.Vehicle;

import java.util.Random;

/**
 * Strategy pattern
 */
public abstract class WashBehavior {
    Random rng = new Random();
    
    public abstract String wash(Vehicle vehicle_);
    
    public abstract String getStr();
}

