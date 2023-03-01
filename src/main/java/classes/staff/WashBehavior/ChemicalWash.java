package classes.staff.WashBehavior;

import classes.vehicles.Vehicle;
import enums.Cleanliness;
import enums.Condition;

public class ChemicalWash extends WashBehavior {

    public String getStr() {return "chemically washed";}

    public String wash(Vehicle vehicle_) {
        String specialString = "";
        if(rng.nextInt(100) < 10){ //10% chance to break any vehicle using chemical wash
            specialString = String.format("%s %d became BROKEN from the chemical wash.",
                    vehicle_.getStr(),
                    vehicle_.getVehicleNo()
            );

            vehicle_.setCondition(Condition.BROKEN);
        }

        switch (vehicle_.getCleanliness()) {
            case SPARKLING:
                main.Main.log("Error: tried to wash an already SPARKLING vehicle");
                break;
            case CLEAN:
                if (rng.nextInt(100) < 10) { //10% chance
                    vehicle_.wash(Cleanliness.DIRTY);
                } else if (rng.nextInt(100) < 20) { //20% chance
                    vehicle_.wash(Cleanliness.SPARKLING);
                }
                break;
            case DIRTY:
                if (rng.nextInt(100) < 80) { //80% chance
                    vehicle_.wash(Cleanliness.CLEAN);
                } else if (rng.nextInt(100) < 10) { //10% chance
                    vehicle_.wash(Cleanliness.SPARKLING);
                }
                break;
        }
        return specialString;
    }


}
