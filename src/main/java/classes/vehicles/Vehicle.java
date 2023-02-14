package classes.vehicles;
import java.util.Random;
import enums.*;

public abstract class Vehicle {
     /**
     * current cleanliness of the vehicle
     */
    Cleanliness cleanliness;
    
     /**
     * current condition of the vehicle
     */
    Condition condition;
    
    /**
     * cost of the vehicle to the dealership
     */
    double cost;
    
     /**
     * sales price of the vehicle to a buyer
     */
    double salesPrice;
    
     /**
     * base bonus amount for selling, repairing, and washing
     */
    int bonusAmount;
    
     /**
     * random seed for this class
     */
    Random rng;
    
    /**
     * unique number identifiers for this instance of vehicle
     */
    int vehicleNo;
    
    /**
     * counter of vehicles to assign unique number identifiers
     */
    static int inventoryNo = 0;
    
    /**
     * Superclass constructor,  creates {@link #rng}. Other attributes handled in subclass
     */
    public Vehicle(){
        rng = new Random();
        
        //assign vehicle number and increment inventory counter
        this.vehicleNo = inventoryNo;
        inventoryNo++;
        
        //randomly determine initial vehicle cleanliness
        int cleanlinessChance = rng.nextInt(100);
        if (cleanlinessChance < 5){ //5% chance
            this.cleanliness = Cleanliness.SPARKLING;
        } else if(cleanlinessChance < 40) { //35% chance
            this.cleanliness = Cleanliness.CLEAN;
        } else { //60% chance
            this.cleanliness = Cleanliness.DIRTY;
        }
        
        //randomly determine condition of the car (equal chance of each)
        int conditionChance = rng.nextInt(3);
        switch(conditionChance){
            case 0:
                this.condition = Condition.LIKE_NEW;
                break;
            case 1:
                this.condition = Condition.USED;
                break;
            case 2:
                this.condition = Condition.BROKEN;
                break;
        }
        
    }
    
    /**
     *  wash method to be used by Interns
     * Boolean return value will indicate whether the vehicle is SPARKLING or not for intern bonus
     */
    public Boolean wash(){
        switch(this.cleanliness){
            case SPARKLING:
                System.out.println("Error: tried to wash an already SPARKLING vehicle");
                break;
            case CLEAN:
                if(rng.nextInt(100) < 5){ //5% chance
                    this.cleanliness = Cleanliness.DIRTY;
                } else if(rng.nextInt(100) < 30) { //30% chance
                    this.cleanliness = Cleanliness.SPARKLING;
                }
                break;
            case DIRTY:
                if(rng.nextInt(100) < 80){ //80% chance
                    this.cleanliness = Cleanliness.CLEAN;
                } else if(rng.nextInt(100) < 10) { //10% chance
                    this.cleanliness = Cleanliness.SPARKLING;
                }
                break;
        }
        
        if(this.cleanliness == Cleanliness.SPARKLING){
            return true;
        }
        return false;
    }
    
    /**
     * repair method to be used by mechanics
     * Boolean return value indicates whether vehicle was repaired or not for mechanic bonus
     */
    public Boolean repair(){
        //whether fixed or not, any vehicle worked on will go down on class of cleanliness if not already DIRTY
        switch(this.cleanliness){
            case SPARKLING:
                this.cleanliness = Cleanliness.CLEAN;
                break;
            case CLEAN:
                this.cleanliness = Cleanliness.DIRTY;
                break;
        }
        
        if(rng.nextInt(100) < 20){ //20% of not fixing the vehicle
               return false;
        } else {
            switch(this.condition) {
                case BROKEN:
                    this.condition = Condition.USED;
                    this.salesPrice *= 1.5; //sales price increased by 50% when it becomes USED
                    break;
                case USED:
                    this.condition = Condition.LIKE_NEW;
                    this.salesPrice *= 1.25; //sales price increased by 25% when it becomes LIKE_NEW
                    break;
                case LIKE_NEW:
                    System.out.println("Error: tried to repair LIKE_NEW vehicle");
                    break;
            }
            return true; 
        }
    }
    
    public void sell(){
        //TODO
    }
    
    /**
     * Gets cost of vehicle
     *
     * @return {@link #cost}
     */
    public double getCost(){
        return this.cost;
    }

    /**
     * Gets bonus amount for vehicle
     *
     * @return {@link #bonusAmount}
     */
    public int getBonusAmount(){
        return this.bonusAmount;
    }
    /**
     * Gets sales price for vehicle
     *
     * @return {@link #salesPrice}
     */
    public double getSalesPrice(){
        return this.salesPrice;
    }
    
    /**
     * Gets the cleanliness of the vehicle
     * @return {@link #cleanliness}
     */
    public Cleanliness getCleanliness(){
        return this.cleanliness;
    }
    
    /**
     * Gets the condition of the vehicle
     * @return {@link #condition}
     */
    public Condition getCondition(){
        return this.condition;
    }
            
}
