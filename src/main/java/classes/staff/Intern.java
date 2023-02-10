package classes.staff;

import classes.vehicles.Vehicle;

public class Intern extends Staff {
    /**
     * creates a new intern with a random name
     */
    public Intern(){
        super();
        dailyPay = 12; // TODO pick pay amounts
    }
    
    /**
     * Washes a vehicle
     * @param vehicle_ vehicle to be washed
     */
    public void wash(Vehicle vehicle_){
        // TODO implement wash function once Vehicle has been implemented
    }
    
    /**
     * Promotes the intern to either {@link Salesperson} or {@link Mechanic}. Sets {@link #employed} to false.
     * @param isMechanic whether to create a mechanic or salesperson
     * @return the new {@link Staff}
     */
    public Staff promote(boolean isMechanic_){
        employed = false;
        if (isMechanic_){
            return new Mechanic(name);
        }else{
            return new Salesperson(name);
        }
    }
}
