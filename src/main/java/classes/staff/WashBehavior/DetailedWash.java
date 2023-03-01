package classes.staff.WashBehavior;

import classes.vehicles.Vehicle;
import enums.Cleanliness;
import enums.Condition;

public class DetailedWash extends WashBehavior {
    public String getStr() {return "washed and detailed";}
    public String wash(Vehicle vehicle_){
        String specialString = "";
        switch (vehicle_.getCleanliness()) {
            case SPARKLING:
                main.Main.log("Error: tried to wash an already SPARKLING vehicle");
                break;
            case CLEAN:
                if (rng.nextInt(100) < 5) { //5% chance
                    vehicle_.wash(Cleanliness.DIRTY);
                } else if (rng.nextInt(100) < 40) { //40% chance
                    vehicle_.wash(Cleanliness.SPARKLING);
                }
                break;
            case DIRTY:
                if (rng.nextInt(100) < 60) { //60% chance
                    vehicle_.wash(Cleanliness.CLEAN);
                } else if (rng.nextInt(100) < 20) { //20% chance
                    vehicle_.wash(Cleanliness.SPARKLING);
                }
                break;
        }
        return specialString;
    }
}
