package classes.vehicles;

import java.time.LocalDate;

public class Van extends Vehicle{
    
    /**
     * the length of the van, in inches
     */
    private int length;
    
    public Van() {
        super();
        length = rng.nextInt(51)+98; // lenght between 98 and 149 inches
        int initialCost = rng.nextInt(15000) + 10000; //generated cost between $10000 and $25000
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
        return "Van";
    }
    
    public int getLength() {
        return length;
    }
}
