package classes.vehicles;

import enums.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;

public class MonsterTruck extends Vehicle {
    
    
    
    /**
     * the monster truck's stage name
     */
    String stageName;
    
    public MonsterTruck(String stageName_) {
        super();
        type = VehicleType.MONSTER_TRUCK;
        int initialCost = rng.nextInt(20000) + 30000; //generated cost between $20000 and $40000
        switch (condition) {
            case LIKE_NEW:
                cost = initialCost;
                break;
            case USED:
                cost = initialCost * 0.8; //if USED, initial cost is reduced by 20%
                break;
            case BROKEN:
                cost = initialCost * 0.5; //if BROKEN, initial cost is reduced by 50%
                break;
        }
        salesPrice = 2 * cost;
        bonusAmount = salesPrice * .05;
        stageName = stageName_;
    }
    
    @Override
    public String getStr() {
        return "Monster Truck";
    }
    
    public String getStageName() {
        return this.stageName;
    }
}
