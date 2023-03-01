package classes;

import classes.staff.Intern;
import classes.staff.Mechanic;
import classes.staff.Salesperson;
import classes.staff.Staff;
import classes.vehicles.*;
import enums.Cleanliness;
import enums.Condition;
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
            Main.log(String.format("Hired %s as a new salesperson.", staffMembers.get(staffMembers.size() - 1).getName()));
            staffMembers.add(new Mechanic());
            Main.log(String.format("Hired %s as a new mechanic.", staffMembers.get(staffMembers.size() - 1).getName()));
            staffMembers.add(new Intern());
            Main.log(String.format("Hired %s as a new intern.", staffMembers.get(staffMembers.size() - 1).getName()));
        }
        formerStaff = new ArrayList<>();
        vehicleInventory = new ArrayList<Vehicle>();
        soldVehicles = new ArrayList<Vehicle>();
        budget = 500000;
        totalLoan = 0;
        dailySales = 0;
        rng = new Random();
    }
    
    /**
     * Will perform all the dealership's daily activitites: opening, working, and ending
     *
     * @param day_ handles the day of the week, on sunday FNCD is closed and Friday/Saturday there will be more buyers
     *             than other days
     */
    public void day(int day_) {
        Main.log("\n============================\n");
        Main.log(String.format("It is %s (Day %d)", days[day_ % 7], day_ + 1));
        if (day_ % 7 == 6) { // sunday
            Main.log("The FNCD is closed.");
        } else {
            // open day
            open();
            work((day_ % 7 == 4) || (day_ % 7 == 5));
            end();
        }
    }
    
    /**
     * Hires new interns if necessary, and restocks vehicle inventory
     */
    private void open() {
        dailySales = 0;
        Main.log("\nOpening...");
        while (staffMembers.size() < 9) {
            hire();
        }
        
        restock(PerformanceCar.class);
        restock(RegularCar.class);
        restock(Pickup.class);
        restock(ElectricCar.class);
        restock(MonsterTruck.class);
        restock(Motorcycle.class);
    }
    
    /**
     * Looks in the dealerships Vehicle inventory at the passed in vehicle type If there are less than 4 vehicles of
     * that type in inventory, buy until there are 4
     *
     * @param type_ this is the type of vehicle to check and restock
     */
    private void restock(Class<? extends Vehicle> type_) {
        int numVe = 0;
        for (Vehicle v_ : vehicleInventory) {
            if (v_.getClass() == type_) {
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
    private void buyCar(Class<? extends Vehicle> type_) {
        Vehicle newCar = null;
        try {
            newCar = (Vehicle) type_.getDeclaredConstructors()[0].newInstance(); // https://stackoverflow.com/questions/712371/can-i-instantiate-a-class-using-the-class-object-what-about-constructors
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.log(String.format("Bought a %s %s %s, assigned Vehicle Number %s",
                newCar.getCleanliness().getStr(), newCar.getCondition().getStr(), newCar.getStr(), newCar.getVehicleNo()));
        vehicleInventory.add(newCar);
        modifyBudget(-1 * newCar.getCost());
    }
    
    /**
     * hire a new intern
     */
    private void hire() { // OO ELEMENT: Cohesion. The hire() function does one operation (hires an intern)
        Intern hiree = new Intern();
        Main.log(String.format("Hired %s as a new intern.", hiree.getName()));
        staffMembers.add(hiree);
    }
    
    /**
     * wash a random vehicle (either dirty or clean) and update the lists accordingly
     *
     * @param dirtyVehicleList list of vehicles that are dirty
     * @param cleanVehicleList list of vehicles that are clean
     * @param intern           the intern to do the cleaning
     */
    private void wash(ArrayList<Vehicle> dirtyVehicleList, ArrayList<Vehicle> cleanVehicleList, Intern intern) {
        Vehicle toWash;
        if (dirtyVehicleList.size() > 0) {
            toWash = dirtyVehicleList.get(rng.nextInt(dirtyVehicleList.size()));
            intern.wash(toWash);
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
        } else if (cleanVehicleList.size() > 0) {
            toWash = cleanVehicleList.get(rng.nextInt(cleanVehicleList.size()));
            intern.wash(toWash);
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
        } else {
            Main.log(String.format("There are no cars for Intern %s to wash.", intern.getName()));
        }
    }
    
    /**
     * repair a random vehicle and update the list accordingly
     *
     * @param unFixedVehicleList list of vehicles in need of repair
     * @param mechanic           mechanic to do the repairing
     */
    private void repair(ArrayList<Vehicle> unFixedVehicleList, Mechanic mechanic) {
        Vehicle toFix;
        if (unFixedVehicleList.size() > 0) {
            toFix = unFixedVehicleList.get(rng.nextInt(unFixedVehicleList.size()));
            mechanic.repair(toFix);
            if (toFix.getCondition() == Condition.LIKE_NEW) {
                unFixedVehicleList.remove(toFix);
            }
        } else {
            Main.log(String.format("There are no cars for Mechanic %s to fix.", mechanic.getName()));
        }
    }
    
    /**
     * attempt to sell cars to buyers
     *
     * @param extraBuyers whether there are extra buyers today (Fri or Sat)
     * @param salespeople the current salespeople
     */
    private void sell(boolean extraBuyers, ArrayList<Salesperson> salespeople) {
        // create buyers
        int numBuyers = (extraBuyers ? rng.nextInt(7) + 2 : rng.nextInt(6));
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
    }
    
    /**
     * clean, repair, and sell vehicles
     *
     * @param extraBuyers_ whether there are extra buyers today (Fri or Sat)
     */
    private void work(boolean extraBuyers_) {
        ArrayList<Salesperson> salespeople = new ArrayList<>();
        ArrayList<Vehicle> dirtyVehicleList = new ArrayList<>();
        ArrayList<Vehicle> cleanVehicleList = new ArrayList<>();
        ArrayList<Vehicle> unFixedVehicleList = new ArrayList<>();
        for (Vehicle v_ : vehicleInventory) { // OO ELEMENT: Inheritance. Here we work with all elements in the vehicleInventory ArrayList as the superclass Vehicle
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
        
        
        Main.log("\nWorking...");
        for (int sIndex = 0; sIndex < staffMembers.size() * 2; sIndex++) { // loop through twice as each intern and mechanic can work on two vehicles
            Staff s_ = staffMembers.get(sIndex % staffMembers.size());
            if (s_.getClass() == Intern.class) {
                wash(dirtyVehicleList, cleanVehicleList, (Intern) s_);
            } else if (s_.getClass() == Mechanic.class) {
                repair(unFixedVehicleList, (Mechanic) s_);
            } else {
                if (!salespeople.contains(s_)) {
                    salespeople.add(((Salesperson) s_));
                }
            }
        }
        
        sell(extraBuyers_, salespeople);
        System.out.println("Done working");
    }
    
    /**
     * Closes the dealership for the day Pays all employees Handles any quitters, and promotes interns if necessary
     */
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
        //handle intern quitting
        if (internQuit) {
            Staff quitter = interns.get(rng.nextInt(interns.size()));
            interns.remove(quitter); //make sure to remove them from here so they aren't promoted
            staffMembers.remove(quitter);
            formerStaff.add(quitter);
            Main.log(String.format("Intern %s has quit the FNCD.", quitter.getName()));
        }
        
        //handle mechanic quitting, promote intern
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
        
        //handle salesperson quitting, promote intern
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
    
    /**
     * Produces report including: All current and formers staff members All current and sold Vehicles Budget and Loan
     * amounts
     */
    public void report() {
        Main.log("\nGenerating Report...");
        
        Main.log("\nCurrent Staff Members:");
        Main.log(String.format("%12s | %-11s | %4s | %11s | %10s ",
                "Position", "Name", "Days", "Total Pay", "Total Bonus"));
        for (Staff s_ : staffMembers) {
            Main.log(String.format("%12s | %-11s | %4d | $%10.2f | $%10.2f",
                    s_.getPosition(), // OO ELEMENT: Polymorphism. Here the getPosition() method is the subclass implementation, despite being called from a superclass object.
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
                    v_.getStr(),
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
                    v_.getStr(),
                    v_.getCost(),
                    v_.getSalesPrice(),
                    v_.getCondition().getStr(),
                    v_.getCleanliness().getStr()));
        }
        
        Main.log(String.format("Operating Budget: $%.2f", this.budget));
        Main.log(String.format("Loans taken: $%.2f", this.totalLoan));
    }
    
    /**
     * Adjusts the budget by the passed in amount Takes out a loan if necessary
     */
    private void modifyBudget(double budgetChange_) { // OO ELEMENT: Encapsulation. The modifyBudget() method is private, so it cannot be modified by any other class
        budget += budgetChange_;
        if (budget <= 0) {
            // out of money
            totalLoan += 250000;
            budget += 250000;
            Main.log(String.format("The FNCD has run out of money. Loan of $250000 added to the budget. Current budget: $%f", budget));
        }
    }
}
