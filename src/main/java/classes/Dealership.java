package classes;

import classes.staff.*;
import classes.vehicles.*;
import enums.*;
import main.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Dealership {
    /**
     * list of days
     */
    static String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    /**
     * all staff members, current
     */
    ArrayList<Staff> staffMembers;
    /**
     * former staff members
     */
    ArrayList<Staff> formerStaff;
    /**
     * vehicles currently in stock
     */
    ArrayList<Vehicle> vehicleInventory;
    /**
     * sold vehicles
     */
    ArrayList<Vehicle> soldVehicles;
    /**
     * current remaining budget
     */
    double budget;
    /**
     * total loans taken
     */
    double totalLoan;
    /**
     * sales for the day
     */
    double dailySales;
    
    Random rng;
    
    /**
     * creates a new Dealership. Instantiates 3 staff of each type, instantiates lists, sets initial budget value
     */
    public Dealership() {
        staffMembers = new ArrayList<Staff>();
        for (int i = 0; i < 3; i++) {
            staffMembers.add(new Salesperson());
            staffMembers.add(new Mechanic());
            staffMembers.add(new Intern());
        }
        vehicleInventory = new ArrayList<Vehicle>();
        soldVehicles = new ArrayList<Vehicle>();
        budget = 500000;
        totalLoan = 0;
        dailySales = 0;
        rng = new Random();
    }
    
    public void day(int day_){
        Main.log(String.format("It is %s.", days[day_%7]));
        if (day_%7==6){ // sunday
            Main.log("The FNCD is closed.");
        }else{
            // open day
            open();
            work((day_%7==4) || (day_%7==5));
            end();
        }
    }
    
    public void open(){
        Main.log("Opening...");
        while (staffMembers.size() < 9){
            hire();
        }
        
        restock(VehicleType.PERFORMANCE_CAR);
        restock(VehicleType.REGULAR_CAR);
        restock(VehicleType.PICKUP);
    }
    
    private void restock(VehicleType type_){
        int numVe = 0;
        for (Vehicle v_ : vehicleInventory){
            if (v_.getClass() == type_.getClassType()){
                numVe++;
            }
        }
        
        while (numVe < 4){
            buyCar(type_);
            numVe++;
        }
    }
    
    /**
     * buy a new car and decrement the budget
     * @param type_ type of car to buy
     */
    private void buyCar(VehicleType type_){
        Vehicle newCar;
        switch (type_){
            case PERFORMANCE_CAR:
                newCar = new PerformanceCar();
                break;
            case REGULAR_CAR:
                newCar = new RegularCar();
                break;
            case PICKUP:
                newCar = new Pickup();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type_);
        }
        vehicleInventory.add(newCar);
        budget -= newCar.getCost();
    }
    
    /**
     * hire a new intern
     */
    public void hire(){
        staffMembers.add(new Intern());
    }
    
    public void work(boolean extraBuyers_){
        ArrayList<Salesperson> salespeople = new ArrayList<>();
        ArrayList<Vehicle> dirtyVehicleList = new ArrayList<>();
        ArrayList<Vehicle> cleanVehicleList = new ArrayList<>();
        for (Vehicle v_ : vehicleInventory){
            if (v_.getCleanliness() == Cleanliness.DIRTY){
                dirtyVehicleList.add(v_);
            }else if (v_.getCleanliness() == Cleanliness.CLEAN){
                cleanVehicleList.add(v_);
            }
        }
        Vehicle toWash;
        for (int sIndex=0; sIndex < staffMembers.size()*2; sIndex++){
            Staff s_ = staffMembers.get(sIndex%staffMembers.size());
            if (s_.getClass() == Intern.class){
                if (dirtyVehicleList.size() > 0) {
                    toWash = dirtyVehicleList.get(rng.nextInt(dirtyVehicleList.size()));
                    ((Intern) s_).wash(toWash);
                    switch (toWash.getCleanliness()){
                        case DIRTY:
                            // do nothing
                            break;
                        case CLEAN:
                            dirtyVehicleList.remove(toWash);
                            cleanVehicleList.add(toWash);
                            break;
                        case SPARKLING:
                            dirtyVehicleList.remove(toWash);
                            break;
                    }
                }else{
                    toWash = cleanVehicleList.get(rng.nextInt(cleanVehicleList.size()));
                    ((Intern) s_).wash(toWash);
                    switch (toWash.getCleanliness()) {
                        case DIRTY:
                            cleanVehicleList.remove(toWash);
                            dirtyVehicleList.add(toWash);
                            break;
                        case CLEAN:
                            // do nothing
                            break;
                        case SPARKLING:
                            cleanVehicleList.remove(toWash);
                            break;
                    }
                }
                
            }else if (s_.getClass() == Mechanic.class){
                ((Mechanic) s_).repair();
            }else{
                salespeople.add(((Salesperson)s_));
            }
        }
        
        
        
    }
    
    
    public void end(){
    
    }
    
    public void setBudget(){
    
    }
    
    public ArrayList<Staff> getStaffMembers(){
        return staffMembers;
    }
    
    public ArrayList<Vehicle> getVehicleInventory(){
        return vehicleInventory;
    }
    
    public ArrayList<Vehicle> getSoldVehicles(){
        return soldVehicles;
    }
    
    public double getBudget(){
        return budget;
    }
    
    public double getTotalLoan(){
        return totalLoan;
    }
    
    public double getDailySales(){
        return dailySales;
    }
}
