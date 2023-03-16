package classes.vehicles;

import enums.Condition;
import enums.VehicleType;

public class ElectricCar extends Vehicle {
    
    /**
     * the range of the electric car, in miles
     */
    int range;
    
    /**
     * initialize a new electric car. set initial cost and range
     */
    public ElectricCar() {
        super();
        type = VehicleType.ELECTRIC_CAR;
        int initialCost = rng.nextInt(20000) + 20000; //generated cost between $20000 and $40000
        range = rng.nextInt(340) + 60;
        switch (condition) {
            case LIKE_NEW:
                cost = initialCost;
                range += 100;
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
        
    }
    
    /**
     * repair the electric car. does {@link Vehicle.repair()} and then increases the range by 100 if it becomes like
     * new
     *
     * @return the result of Vehicle.repair()
     */
    @Override
    public Boolean repair() {
        Boolean superRepair = super.repair(); // TODO 99% sure this is how this works
        if (condition == Condition.LIKE_NEW) {
            this.range += 100;
        }
        return superRepair;
    }
    
    /**
     * @return "Electric Car"
     */
    @Override
    public String getStr() {
        return "Electric Car";
    }
    
    public int getRange() {
        return range;
    }
}
