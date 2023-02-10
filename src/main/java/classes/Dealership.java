package classes;

import classes.staff.*;
import classes.vehicles.*;
import enums.*;

import java.util.ArrayList;
import java.util.Random;

public class Dealership {
    /**
     * all staff members, current and former
     */
    ArrayList<Staff> staffMembers;
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
        staffMembers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            staffMembers.add(new Salesperson());
            staffMembers.add(new Mechanic());
            staffMembers.add(new Intern());
        }
        vehicleInventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();
        budget = 500000;
        totalLoan = 0;
        dailySales = 0;
        rng = new Random();
    }
    
    public void open(){
    
    }
    
    /**
     * buy a new car and decrement the budget
     * @param type_ type of car to buy
     */
    public void buyCar(VehicleType type_){
        switch (type_){
            case PERFORMANCE_CAR:
                vehicleInventory.add(new PerformanceCar());
                break;
            case REGULAR_CAR:
                vehicleInventory.add(new RegularCar());
                break;
            case PICKUP:
                vehicleInventory.add(new Pickup());
                break;
        }
        // TODO add budget dec
    }
    
    /**
     * hire a new intern
     */
    public void hire(){
        staffMembers.add(new Intern());
    }
    
    public void wash(){
    
    }
    
    public void repair(){
    
    }
    
    public void sell(){
    
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
