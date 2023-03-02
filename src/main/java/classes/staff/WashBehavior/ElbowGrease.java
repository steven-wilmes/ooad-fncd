package classes.staff.WashBehavior;

import classes.vehicles.Vehicle;
import enums.Cleanliness;
import enums.Condition;

public class ElbowGrease extends WashBehavior {
    public String getStr() {
        return "used some elbow grease on";
    }
    
    public String wash(Vehicle vehicle_) {
        String specialString = "";
        if (rng.nextInt(100) < 10) { //10% chance to make a vehicle LIKE NEW with Elbow Grease
            specialString = String.format("%s %d became LIKE NEW from the elbow grease.",
                    vehicle_.getStr(),
                    vehicle_.getVehicleNo()
            );
            vehicle_.setCondition(Condition.LIKE_NEW);
        }
        
        switch (vehicle_.getCleanliness()) {
            case SPARKLING:
                main.Main.log("Error: tried to wash an already SPARKLING vehicle");
                break;
            case CLEAN:
                if (rng.nextInt(100) < 15) { //15% chance
                    vehicle_.wash(Cleanliness.DIRTY);
                } else if (rng.nextInt(100) < 15) { //15% chance
                    vehicle_.wash(Cleanliness.SPARKLING);
                }
                break;
            case DIRTY:
                if (rng.nextInt(100) < 70) { //70% chance
                    vehicle_.wash(Cleanliness.CLEAN);
                } else if (rng.nextInt(100) < 5) { //5% chance
                    vehicle_.wash(Cleanliness.SPARKLING);
                }
                break;
        }
        return specialString;
    }
    
}
