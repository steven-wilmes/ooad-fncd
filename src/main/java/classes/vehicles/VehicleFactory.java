package classes.vehicles;

import enums.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// OO PATTERN: FACTORY
public class VehicleFactory {
    
    /**
     * a hashmap of monster truck names and the number of times they have been used. names from https://en.wikipedia.org/wiki/List_of_monster_trucks
     */
    private HashMap<String, Integer> truckNames = new HashMap<>() {{
        put("Air Force Afterburner", 0);
        put("Avenger", 0);
        put("Bad News Travels Fast", 0);
        put("Batman", 0);
        put("Backwards Bob", 0);
        put("Bear Foot", 0);
        put("Bigfoot", 0);
        put("Black Stallion", 0);
        put("Blacksmith", 0);
        put("Blue Thunder", 0);
        put("Bounty Hunter", 0);
        put("Brutus", 0);
        put("Bulldozer", 0);
        put("Captain's Curse", 0);
        put("Cyborg", 0);
        put("El Toro Loco", 0);
        put("Game Over", 0);
        put("Grave Digger", 0);
        put("Grider", 0);
        put("Gunslinger", 0);
        put("Jurassic Attack", 0);
        put("King Krunch", 0);
        put("Lucas Oil Crusader", 0);
        put("Madusa", 0);
        put("Maximum Destruction", 0);
        put("Mohawk Warrior", 0);
        put("Monster Mutt", 0);
        put("Monster Mutt Dalmatian", 0);
        put("Predator", 0);
        put("Shell Camino", 0);
        put("Raminator", 0);
        put("Snake Bite", 0);
        put("Stone Crusher", 0);
        put("Sudden Impact", 0);
        put("Swamp Thing", 0);
        put("The Destroyer", 0);
        put("The Felon", 0);
        put("USA-1", 0);
        put("War Wizard", 0);
        put("WCW Nitro Machine", 0);
    }};
    
    public VehicleFactory(){}
    
    public Vehicle purchaseVehicle(VehicleType vehicleType_){
        Vehicle newCar = null;
        
        switch(vehicleType_){
            case PICKUP:
                newCar = new Pickup();
                break;
            case REGULAR_CAR:
                newCar = new RegularCar();
                break;
            case VAN:
                newCar = new Van();
                break;
            case MOTORHOME:
                newCar = new Motorhome();
                break;
            case MOTORCYCLE:
                newCar = new Motorcycle();
                break;
            case ELECTRIC_CAR:
                newCar = new ElectricCar();
                break;
            case COLLECTOR_CAR:
                newCar = new CollectorCar();
                break;
            case MONSTER_TRUCK:
                String finalStageName;
                String stageName_ = (new ArrayList<String>(truckNames.keySet())).get((new Random()).nextInt(truckNames.keySet().size()));
                int timesUsed = truckNames.get(stageName_);
                if (timesUsed > 0) { // avoid duplicates
                    // name already used
                    finalStageName = stageName_.concat(" ").concat(Integer.toString(timesUsed));
                    truckNames.replace(stageName_, timesUsed + 1);
                } else {
                    finalStageName = stageName_;
                }
                
                newCar = new MonsterTruck(finalStageName);
                break;
            case PERFORMANCE_CAR:
                newCar = new PerformanceCar();
                break;
        }
        
        return newCar;
    }
}
