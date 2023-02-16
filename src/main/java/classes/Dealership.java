package classes;

import classes.staff.Intern;
import classes.staff.Mechanic;
import classes.staff.Salesperson;
import classes.staff.Staff;
import classes.vehicles.PerformanceCar;
import classes.vehicles.Pickup;
import classes.vehicles.RegularCar;
import classes.vehicles.Vehicle;
import enums.Cleanliness;
import enums.Condition;
import enums.VehicleType;
import main.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
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
            Main.log(String.format("Hired %s as a new salesperson.", staffMembers.get(staffMembers.size()-1).getName()));
            staffMembers.add(new Mechanic());
            Main.log(String.format("Hired %s as a new mechanic.", staffMembers.get(staffMembers.size()-1).getName()));
            staffMembers.add(new Intern());
            Main.log(String.format("Hired %s as a new intern.", staffMembers.get(staffMembers.size()-1).getName()));
        }
        formerStaff = new ArrayList<>();
        vehicleInventory = new ArrayList<Vehicle>();
        soldVehicles = new ArrayList<Vehicle>();
        budget = 500000;
        totalLoan = 0;
        dailySales = 0;
        rng = new Random();
    }
    
    public void day(int day_) {
        Main.log("\n============================\n");
        Main.log(String.format("It is %s (Day %d)", days[day_ % 7], day_+1));
        if (day_ % 7 == 6) { // sunday
            Main.log("The FNCD is closed.");
        } else {
            // open day
            open();
            work((day_ % 7 == 4) || (day_ % 7 == 5));
            end();
        }
    }
    
    private void open() {
        dailySales = 0;
        Main.log("\nOpening...");
        while (staffMembers.size() < 9) {
            hire();
        }
        
        restock(VehicleType.PERFORMANCE_CAR);
        restock(VehicleType.REGULAR_CAR);
        restock(VehicleType.PICKUP);
    }
    
    private void restock(VehicleType type_) {
        int numVe = 0;
        for (Vehicle v_ : vehicleInventory) {
            if (v_.getClass() == type_.getClassType()) {
                numVe++;
            }
        }
        
        while (numVe < 4) {
            buyCar(type_);
            numVe++;
        }
    }
    
    /**
     * buy a new car and decrement the budget
     *
     * @param type_ type of car to buy
     */
    private void buyCar(VehicleType type_) {
        Vehicle newCar;
        switch (type_) {
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
        Main.log(String.format("Bought a %s %s %s, assigned Vehicle Number %s",
                newCar.getCleanliness().getStr(), newCar.getCondition().getStr(), type_.getStr(), newCar.getVehicleNo()));
        vehicleInventory.add(newCar);
        modifyBudget(-1 * newCar.getCost());
    }
    
    /**
     * hire a new intern
     */
    private void hire() {
        Intern hiree = new Intern();
        Main.log(String.format("Hired %s as a new intern.", hiree.getName()));
        staffMembers.add(hiree);
    }
    
    private void work(boolean extraBuyers_) {
        ArrayList<Salesperson> salespeople = new ArrayList<>();
        ArrayList<Vehicle> dirtyVehicleList = new ArrayList<>();
        ArrayList<Vehicle> cleanVehicleList = new ArrayList<>();
        ArrayList<Vehicle> unFixedVehicleList = new ArrayList<>();
        for (Vehicle v_ : vehicleInventory) {
            if (v_.getCleanliness() == Cleanliness.DIRTY) {
                dirtyVehicleList.add(v_);
            } else if (v_.getCleanliness() == Cleanliness.CLEAN) {
                cleanVehicleList.add(v_);
            }
            if (v_.getCondition() != Condition.LIKE_NEW) {
                // vehicle can be fixed
                unFixedVehicleList.add(v_);
            }
        }
        
        Vehicle toWash;
        Vehicle toFix;
        Main.log("\nWorking...");
        for (int sIndex = 0; sIndex < staffMembers.size() * 2; sIndex++) {
            Staff s_ = staffMembers.get(sIndex % staffMembers.size());
            if (s_.getClass() == Intern.class) {
                if (dirtyVehicleList.size() > 0) {
                    toWash = dirtyVehicleList.get(rng.nextInt(dirtyVehicleList.size()));
                    ((Intern) s_).wash(toWash);
                    switch (toWash.getCleanliness()) {
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
                } else if (cleanVehicleList.size() >0){
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
                
            } else if (s_.getClass() == Mechanic.class) {
                if (unFixedVehicleList.size() > 0) {
                    toFix = unFixedVehicleList.get(rng.nextInt(unFixedVehicleList.size()));
                    ((Mechanic) s_).repair(toFix);
                    if (toFix.getCondition() == Condition.LIKE_NEW) {
                        unFixedVehicleList.remove(toFix);
                    }
                }
            } else {
                if (!salespeople.contains(s_)) {
                    salespeople.add(((Salesperson) s_));
                }
            }
        }
        
        // create buyers
        int numBuyers = (extraBuyers_ ? rng.nextInt(7) + 2 : rng.nextInt(6));
        Main.log(String.format("\nSelling to %d...", numBuyers));
        for (int i = 0; i < numBuyers; i++) {
            Buyer buyer = new Buyer();
            Salesperson seller = salespeople.get(rng.nextInt(salespeople.size()));
            Vehicle sold = seller.sell(buyer, vehicleInventory);
            if (!Objects.isNull(sold)) {
                // vehicle successfully sold
                modifyBudget(sold.getSalesPrice());
                dailySales += sold.getSalesPrice();
                vehicleInventory.remove(sold);
                soldVehicles.add(sold);
            }
        }
        System.out.println("Done working");
    }
    
    
    private void end() {
        
        ArrayList<Intern> interns = new ArrayList<Intern>();
        ArrayList<Mechanic> mechanics = new ArrayList<Mechanic>();
        ArrayList<Salesperson> salespersons = new ArrayList<Salesperson>();
        
        Main.log("\nPaying workers...");
        double totalPaid = 0;
        for (Staff s_ : staffMembers) {
            //Pay all the workers
            double amtPaid = s_.workDay();
            modifyBudget(-1 * amtPaid);
            totalPaid += amtPaid;
            //separate out worker types into arrays
            if (s_.getClass() == Intern.class) {
                interns.add((Intern) s_);
            } else if (s_.getClass() == Mechanic.class) {
                mechanics.add((Mechanic) s_);
            } else if (s_.getClass() == Salesperson.class) {
                salespersons.add((Salesperson) s_);
            }
        }
        Main.log(String.format("Daily pay:      $%10.2f", totalPaid));
        Main.log(String.format("Daily Sales:    $%10.2f", dailySales));
        Main.log(String.format("Current Budget: $%10.2f", budget));
        
        Boolean internQuit = (rng.nextInt(10) == 0); // 10% chance of each type quitting
        Boolean mechanicQuit = (rng.nextInt(10) == 0);
        Boolean salespersonQuit = (rng.nextInt(10) == 0);
        
        /*
         * This is an example of the OO principle of Identity
         * When an intern quits, we want to make sure to remove him from the interns array here,
         * because if a mechanic or salesperson also quits, we need to promote an intern, but it cannot
         * be the intern that just quit. Each intern is a separate entity from the other interns, and has
         * a separate identity
         */
        if (internQuit) {
            Staff quitter = interns.get(rng.nextInt(interns.size()));
            interns.remove(quitter);
            staffMembers.remove(quitter);
            formerStaff.add(quitter);
            Main.log(String.format("Intern %s has quit the FNCD.", quitter.getName()));
        }
        
        if (mechanicQuit) {
            Staff quitter = mechanics.get(rng.nextInt(interns.size()));
            staffMembers.remove(quitter);
            formerStaff.add(quitter);
            Main.log(String.format("Mechanic %s has quit the FNCD.", quitter.getName()));
            
            Intern promotee = interns.get(rng.nextInt(interns.size()));
            interns.remove(promotee);
            staffMembers.remove(promotee);
            staffMembers.add(promotee.promote(true));
        }
        
        if (salespersonQuit) {
            Staff quitter = salespersons.get(rng.nextInt(interns.size()));
            staffMembers.remove(quitter);
            formerStaff.add(quitter);
            Main.log(String.format("Salesperson %s has quit the FNCD.", quitter.getName()));
            
            Intern promotee = interns.get(rng.nextInt(interns.size()));
            interns.remove(promotee);
            staffMembers.remove(promotee);
            staffMembers.add(promotee.promote(false));
        }
    }
    
    public void report() {
        Main.log("\nGenerating Report...");
        
        Main.log("\nCurrent Staff Members:");
        Main.log(String.format("%12s | %-11s | %4s | %11s | %10s ",
                "Position", "Name", "Days", "Total Pay", "Total Bonus"));
        for (Staff s_ : staffMembers) {
            Main.log(String.format("%12s | %-11s | %4d | $%10.2f | $%10.2f",
                    s_.getPosition(),
                    s_.getName(),
                    s_.getDaysWorked(),
                    s_.getTotalSalary(),
                    s_.getTotalBonusEarned()));
        }
        
        Main.log("\nFormer Staff Members:");
        Main.log(String.format("%12s | %-11s | %4s | %11s | %10s ",
                "Position", "Name", "Days", "Total Pay", "Total Bonus"));
        for (Staff s_ : formerStaff) {
            Main.log(String.format("%12s | %-11s | %4d | $%10.2f | $%10.2f",
                    s_.getPosition(),
                    s_.getName(),
                    s_.getDaysWorked(),
                    s_.getTotalSalary(),
                    s_.getTotalBonusEarned()));
        }
        
        Main.log("\nCurrent Vehicles in Stock:");
        vehicleInventory.sort(Comparator.comparing(Vehicle::getVehicleNo));
        Main.log(String.format("%3s | %15s | %9s | %9s | %9s | %11s",
                "VIN", "Type", "Cost", "Price", "Condition", "Cleanliness"));
        for (Vehicle v_ : vehicleInventory) {
            Main.log(String.format("%3d | %15s | $%8.2f | $%8.2f | %9s | %11s",
                    v_.getVehicleNo(),
                    VehicleType.match(v_.getClass()).getStr(),
                    v_.getCost(),
                    v_.getSalesPrice(),
                    v_.getCondition().getStr(),
                    v_.getCleanliness().getStr()));
        }
        
        Main.log("\nSold Vehicles:");
        soldVehicles.sort(Comparator.comparing(Vehicle::getVehicleNo));
        Main.log(String.format("%3s | %15s | %9s | %9s | %9s | %11s",
                "VIN", "Type", "Cost", "Price", "Condition", "Cleanliness"));
        for (Vehicle v_ : soldVehicles) {
            Main.log(String.format("%3d | %15s | $%8.2f | $%8.2f | %9s | %11s",
                    v_.getVehicleNo(),
                    VehicleType.match(v_.getClass()).getStr(),
                    v_.getCost(),
                    v_.getSalesPrice(),
                    v_.getCondition().getStr(),
                    v_.getCleanliness().getStr()));
        }
        
        Main.log(String.format("Operating Budget: $%.2f", this.budget));
        Main.log(String.format("Loans taken: $%.2f", this.totalLoan));
    }
    
    private void modifyBudget(double budgetChange_) {
        budget += budgetChange_;
        if (budget <= 0) {
            // out of money
            totalLoan += 250000;
            budget += 250000;
            Main.log(String.format("The FNCD has run out of money. Loan of $250000 added to the budget. Current budget: $%f", budget));
        }
    }
}
