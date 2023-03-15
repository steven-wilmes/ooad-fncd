package classes.vehicles;

import enums.VehicleType;

import java.time.LocalDate;

public class CollectorCar extends Vehicle {
    
    private int year;
    
    public CollectorCar() {
        super();
        type = VehicleType.COLLECTOR_CAR;
        year = 0;
        while (year < 1920 || year > 1988) {
            // nextGaussian implementation: https://stackoverflow.com/questions/6011943/java-normal-distribution
            year = (int) Math.floor(rng.nextGaussian() * 13 + 1975); //Standard Normal Distribution with mean 1975, std. dev. 13
        }
        int initialCost = rng.nextInt(10000) + 15000; //generated cost between $15000 and $25000
        initialCost += (LocalDate.now().getYear() - year) * 100; // increase cost based on car age
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
    
    public int getYear() {
        return year;
    }
    
    @Override
    public String getStr() {
        return "Collector Car";
    }
}
