package classes.vehicles;

import enums.VehicleType;

public class RegularCar extends Vehicle {
    
    public RegularCar() {
        super();
        type = VehicleType.REGULAR_CAR;
        int initialCost = rng.nextInt(10000) + 10000; //generated cost between $10000 and $20000
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
    }
    
    @Override
    public String getStr() {
        return "Regular Car";
    }
}
