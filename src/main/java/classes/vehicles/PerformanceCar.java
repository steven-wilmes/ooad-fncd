package classes.vehicles;

public class PerformanceCar extends Vehicle {
    
    public PerformanceCar() {
        super();
        int initialCost = rng.nextInt(20000) + 20000; //generated cost between $20000 and $40000
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
}
