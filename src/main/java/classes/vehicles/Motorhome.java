package classes.vehicles;

import enums.VehicleType;

public class Motorhome extends Vehicle{
    
    private char classType;
    
    public Motorhome() {
        super();
        type = VehicleType.MOTORHOME;
        int classChoice = rng.nextInt(3);
        switch (classChoice){
            case 0:
                classType = 'A';
                break;
            case 1:
                classType = 'B';
                break;
            default:
                classType = 'C';
                break;
        }
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
    
    public char getClassType(){
        return classType;
    }
    
    @Override
    public String getStr() {
        return "Motorhome";
    }
}
