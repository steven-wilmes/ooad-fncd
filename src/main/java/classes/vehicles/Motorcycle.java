package classes.vehicles;

public class Motorcycle extends Vehicle {
    
    /**
     * the motorcycle's engine size rating, in cubic centimeters
     */
    double engSizeRating;
    
    public Motorcycle() {
        super();
        int initialCost = rng.nextInt(20000) + 5000; //generated cost between $20000 and $40000
        engSizeRating = 0;
        while (engSizeRating < 50) {
            // nextGaussian implementation: https://stackoverflow.com/questions/6011943/java-normal-distribution
            engSizeRating = rng.nextGaussian() * 300 + 700; //Standard Normal Distribution with mean 700, std. dev. 300
        }
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
        return "Motorcycle";
    }
    
    public double getEngSizeRating() {
        return engSizeRating;
    }
}
