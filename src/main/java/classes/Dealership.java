package classes;

import classes.observerData.RaceOutcome;
import classes.observerData.SaleOutcome;
import classes.observerData.Tuple;
import classes.observerData.WashOutcome;
import classes.staff.*;
import classes.vehicles.MonsterTruck;
import classes.vehicles.Vehicle;
import classes.vehicles.VehicleFactory;
import enums.Cleanliness;
import enums.Condition;
import enums.StaffType;
import enums.VehicleType;
import main.Main;

import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Semaphore;

// semaphore synchronization from https://stackoverflow.com/questions/9388838/writing-a-program-with-2-threads-which-prints-alternatively
public class Dealership extends Thread {
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
    
    /**
     * random number generator
     */
    Random rng;
    
    /**
     * the publisher for the Observer pattern
     */
    PropertyChangeSupport publisher;
    
    /**
     * the daily logger
     */
    Logger dailyLogger;
    
    /**
     * the tracker
     */
    Tracker tracker;
    
    VehicleFactory vehicleFactory;
    StaffFactory staffFactory;
    
    Semaphore lock, unlock;
    
    String location;
    
    
    /**
     * creates a new Dealership. Instantiates 3 staff of each type, instantiates lists, sets initial budget value
     */
    public Dealership(String name_, Semaphore lock_, Semaphore unlock_) {
        lock = lock_;
        unlock = unlock_;
        location = name_;
        publisher = new PropertyChangeSupport(this);
        tracker = Tracker.getInstance();
        publisher.addPropertyChangeListener(tracker);
        vehicleFactory = new VehicleFactory();
        staffFactory = new StaffFactory();
        staffMembers = new ArrayList<Staff>();
        try {
            lock.acquire();
        } catch (InterruptedException e_) {
            e_.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            for (StaffType type : StaffType.values()) {
                Staff newStaff = staffFactory.hireStaff(type);
                staffMembers.add(newStaff);
                Main.log(String.format("Hired %s as a new %s.", newStaff.getName(), newStaff.getPosition()));
            }
        }
        unlock.release();
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
    public void day(int day_, boolean uiSell_) throws InterruptedException {
        publisher.firePropertyChange("day", day_, day_ + 1);
        dailyLogger = Logger.getInstance(day_ + 1);
        publisher.addPropertyChangeListener(dailyLogger);
        Main.log("\n============================\n");
        Main.log(String.format("It is %s (Day %d)", days[day_ % 7], day_ + 1));
        if (day_ % 7 == 6) { // sunday - no sales, but there is a rac
            lock.acquire();
            Main.log("\nSUNDAY SUNDAY SUNDAY RACE DAY!\n");
            this.race();
            unlock.release();
        } else {
            // open day
            open();
            work((day_ % 7 == 4) || (day_ % 7 == 5), uiSell_);
            lock.acquire();
            if (day_ % 7 == 2) {
                Main.log("\nWEDNESDAY RACE NIGHT!\n");
                this.race();
            }
            unlock.release();
            end();
        }
        tracker.report();
        publisher.removePropertyChangeListener(dailyLogger);
        dailyLogger = null;
    }
    
    /**
     * Hires new interns if necessary, and restocks vehicle inventory
     */
    private void open() throws InterruptedException {
        lock.acquire();
        dailySales = 0;
        Main.log("\nOpening...");
        
        this.handleInjuries();
        //hire interns
        while (staffMembers.size() < 12) {
            hire();
        }
        
        for (VehicleType type : VehicleType.values()) {
            restock(type);
        }
        unlock.release();
    }
    
    /**
     * Looks in the dealerships Vehicle inventory at the passed in vehicle type If there are less than 4 vehicles of
     * that type in inventory, buy until there are 4
     *
     * @param type_ this is the type of vehicle to check and restock
     */
    private void restock(VehicleType type_) {
        int numVe = 0;
        for (Vehicle v_ : vehicleInventory) {
            if (v_.getType() == type_) {
                numVe++;
            }
        }
        
        while (numVe < 6) {
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
        Vehicle newCar = vehicleFactory.purchaseVehicle(type_);
        Main.log(String.format("Bought a %s %s %s, assigned Vehicle Number %s",
                newCar.getCleanliness().getStr(), newCar.getCondition().getStr(), newCar.getStr(), newCar.getVehicleNo()));
        vehicleInventory.add(newCar);
        modifyBudget(-1 * newCar.getCost());
    }
    
    /**
     * hire a new intern
     */
    private void hire() { // OO ELEMENT: Cohesion. The hire() function does one operation (hires an intern)
        Staff hiree = staffFactory.hireStaff(StaffType.INTERN);
        publisher.firePropertyChange("newStaff", null, new Tuple(hiree.getName(), hiree.getPosition()));
        staffMembers.add(hiree);
    }
    
    private void handleInjuries() {
        
        ArrayList<Driver> injuredDrivers = new ArrayList<>();
        for (Staff s_ : this.staffMembers) {
            if (s_.getClass() == Driver.class) {
                Driver driver = (Driver) s_;
                if (driver.getInjured()) {
                    injuredDrivers.add(driver);
                }
            }
        }
        for (Driver d_ : injuredDrivers) {
            this.staffMembers.remove(d_);
            this.staffMembers.add(staffFactory.hireStaff(StaffType.DRIVER));
            publisher.firePropertyChange("newStaff", null, new Tuple(this.staffMembers.get(staffMembers.size() - 1).getName(), "Driver"));
            this.formerStaff.add(d_);
        }
    }
    
    /**
     * wash a random vehicle (either dirty or clean) and update the lists accordingly
     *
     * @param dirtyVehicleList list of vehicles that are dirty
     * @param cleanVehicleList list of vehicles that are clean
     * @param intern           the intern to do the cleaning
     */
    private void wash(ArrayList<Vehicle> dirtyVehicleList, ArrayList<Vehicle> cleanVehicleList, Intern intern) throws InterruptedException {
        lock.acquire();
        Vehicle toWash;
        WashOutcome washOutcome = null;
        if (dirtyVehicleList.size() > 0) {
            toWash = dirtyVehicleList.get(rng.nextInt(dirtyVehicleList.size()));
            washOutcome = intern.wash(toWash);
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
            washOutcome = intern.wash(toWash);
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
        if (washOutcome != null) {
            publisher.firePropertyChange("washOutcome", null, washOutcome);
        }
        unlock.release();
    }
    
    /**
     * repair a random vehicle and update the list accordingly
     *
     * @param unFixedVehicleList list of vehicles in need of repair
     * @param mechanic           mechanic to do the repairing
     */
    private void repair(ArrayList<Vehicle> unFixedVehicleList, Mechanic mechanic) throws InterruptedException {
        lock.acquire();
        Vehicle toFix;
        if (unFixedVehicleList.size() > 0) {
            toFix = unFixedVehicleList.get(rng.nextInt(unFixedVehicleList.size()));
            publisher.firePropertyChange("repairOutcome", null, mechanic.repair(toFix));
            if (toFix.getCondition() == Condition.LIKE_NEW) {
                unFixedVehicleList.remove(toFix);
            }
        } else {
            Main.log(String.format("There are no cars for Mechanic %s to fix.", mechanic.getName()));
        }
        unlock.release();
    }
    
    /**
     * attempt to sell cars to buyers
     *
     * @param extraBuyers whether there are extra buyers today (Fri or Sat)
     * @param salespeople the current salespeople
     */
    private void sell(boolean extraBuyers, ArrayList<Salesperson> salespeople) throws InterruptedException {
        lock.acquire();
        // create buyers
        int numBuyers = (extraBuyers ? rng.nextInt(7) + 2 : rng.nextInt(6));
        Main.log(String.format("\nSelling to %d...", numBuyers));
        for (int i = 0; i < numBuyers; i++) {
            Buyer buyer = new Buyer();
            Salesperson seller = salespeople.get(rng.nextInt(salespeople.size()));
            Tuple result = seller.sell(buyer, vehicleInventory);
            Vehicle sold = (Vehicle) result.getX();
            publisher.firePropertyChange("salesOutcome", null,
                    new SaleOutcome((Boolean) result.getY(), seller.getName(), sold.getCleanliness(), sold.getCondition(), sold.getStr(), sold.getVehicleNo(), sold.getSalesPrice(), sold.getBonusAmount()));
            if ((Boolean) result.getY()) {
                // vehicle successfully sold
                modifyBudget(sold.getSalesPrice());
                dailySales += sold.getSalesPrice();
                vehicleInventory.removeIf(v -> v.getVehicleNo() == sold.getVehicleNo());
                soldVehicles.add(sold);
            }
        }
        unlock.release();
    }
    
    /**
     * clean, repair, and sell vehicles
     *
     * @param extraBuyers_ whether there are extra buyers today (Fri or Sat)
     */
    private void work(boolean extraBuyers_, boolean uiSell_) throws InterruptedException {
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
        }
        
        
        Main.log("\nWorking...");
        Main.log("\nWashing...");
        for (int sIndex = 0; sIndex < staffMembers.size() * 2; sIndex++) { // loop through twice as each intern can work on two vehicles
            Staff s_ = staffMembers.get(sIndex % staffMembers.size());
            if (s_.getClass() == Intern.class) {
                wash(dirtyVehicleList, cleanVehicleList, (Intern) s_);
            } else if (s_.getClass() == Salesperson.class) {
                if (!salespeople.contains(s_)) {
                    salespeople.add(((Salesperson) s_));
                }
            }
        }
        
        for (Vehicle v_ : vehicleInventory) {
            if (v_.getCondition() != Condition.LIKE_NEW) {
                // vehicle can be fixed
                unFixedVehicleList.add(v_);
            }
        }
        
        Main.log("\nRepairing...");
        for (int sIndex = 0; sIndex < staffMembers.size() * 2; sIndex++) { // loop through twice as each mechanic can work on two vehicles
            Staff s_ = staffMembers.get(sIndex % staffMembers.size());
            if (s_.getClass() == Mechanic.class) {
                repair(unFixedVehicleList, (Mechanic) s_);
            }
        }
        if (uiSell_) {
            uiSell(salespeople);
        } else {
            sell(extraBuyers_, salespeople);
        }
        Main.log("Done working");
    }
    
    /**
     * Closes the dealership for the day Pays all employees Handles any quitters, and promotes interns if necessary
     */
    private void end() throws InterruptedException {
        lock.acquire();
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
        publisher.firePropertyChange("staffPay", null, totalPaid);
        publisher.firePropertyChange("moneyIn", null, dailySales);
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
            staffMembers.add(staffFactory.hireStaff(StaffType.MECHANIC, promotee));
            publisher.firePropertyChange("newStaff", null, new Tuple(promotee.getName(), "Mechanic"));
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
            staffMembers.add(staffFactory.hireStaff(StaffType.SALESPERSON, promotee));
            publisher.firePropertyChange("newStaff", null, new Tuple(promotee.getName(), "Salesperson"));
        }
        unlock.release();
        
    }
    
    /**
     * Produces report including: All current and formers staff members All current and sold Vehicles Budget and Loan
     * amounts
     */
    public void report() throws InterruptedException {
        lock.acquire();
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
        
        Main.log(prettyCurrentVehicles());
        
        Main.log("\nSold Vehicles:");
        soldVehicles.sort(Comparator.comparing(Vehicle::getVehicleNo));
        Main.log(String.format("%3s | %75s | %9s | %10s | %9s | %11s | %s",
                "VIN", "Type", "Cost", "Price", "Condition", "Cleanliness", "Race Wins"));
        for (Vehicle v_ : soldVehicles) {
            Main.log(String.format("%3d | %75s | $%8.2f | $%9.2f | %9s | %11s | %d",
                    v_.getVehicleNo(),
                    v_.getStr(),
                    v_.getCost(),
                    v_.getSalesPrice(),
                    v_.getCondition().getStr(),
                    v_.getCleanliness().getStr(),
                    v_.getWins()));
        }
        
        Main.log(String.format("Operating Budget: $%.2f", this.budget));
        Main.log(String.format("Loans taken: $%.2f", this.totalLoan));
        unlock.release();
    }
    
    private String prettyCurrentVehicles() {
        String toReturn = "";
        toReturn = toReturn.concat("\nCurrent Vehicles in Stock:\n");
        vehicleInventory.sort(Comparator.comparing(Vehicle::getVehicleNo));
        toReturn = toReturn.concat(String.format("%3s | %15s | %9s | %10s | %9s | %11s | %s\n",
                "VIN", "Type", "Cost", "Price", "Condition", "Cleanliness", "Race Wins"));
        for (Vehicle v_ : vehicleInventory) {
            toReturn = toReturn.concat(String.format("%3d | %15s | $%8.2f | $%9.2f | %9s | %11s | %d\n",
                    v_.getVehicleNo(),
                    v_.getStr(),
                    v_.getCost(),
                    v_.getSalesPrice(),
                    v_.getCondition().getStr(),
                    v_.getCleanliness().getStr(),
                    v_.getWins()));
        }
        return toReturn;
    }
    
    /**
     * Adjusts the budget by the passed in amount Takes out a loan if necessary
     */
    private void modifyBudget(double budgetChange_) { // OO ELEMENT: Encapsulation. The modifyBudget() method is private, so it cannot be modified by any other class
        budget += budgetChange_;
        if (budget <= 0) {
            // out of money
            totalLoan += 250000;
            publisher.firePropertyChange("loan", null, totalLoan);
            budget += 250000;
            Main.log(String.format("The FNCD has run out of money. Loan of $250000 added to the budget. Current budget: $%f", budget));
        }
    }
    
    private void race() throws InterruptedException {
        String types[] = {"Performance Car", "Pickup", "Motorcycle", "Monster Truck"};
        
        String raceType = types[rng.nextInt(types.length)];
        Main.log(String.format("Today we will be having a %s race!\n", raceType));
        
        ArrayList<Driver> drivers = new ArrayList<>();
        ArrayList<Vehicle> racingVehicles = new ArrayList<>();
        
        //get our drivers for the race
        for (Staff s_ : this.staffMembers) {
            if (s_.getClass() == Driver.class) {
                drivers.add((Driver) s_);
            }
        }
        
        //get our vehicles for the race
        for (Vehicle v_ : vehicleInventory) {
            if (v_.getStr() == raceType && racingVehicles.size() < 3 && v_.getCondition() != Condition.BROKEN) {
                racingVehicles.add(v_);
            }
        }
        
        //do the race
        //numbers 1-20 randomly shuffled gets our place order
        //places for our drivers will be taken from the front of the list
        //for example the first driver got places[0] place, second driver got places[1] place and so on
        ArrayList<Integer> places = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            places.add(i);
        }
        Collections.shuffle(places);
        
        //Announce racers
        for (int i = 0; i < racingVehicles.size(); i++) {
            Driver d = drivers.get(i);
            Vehicle v = racingVehicles.get(i);
            if (raceType == "Monster Truck") {
                MonsterTruck mt = (MonsterTruck) v;
                Main.log(String.format("Driver %s is driving %s (%s %d) in the race",
                        d.getName(),
                        mt.getStageName(),
                        mt.getStr(),
                        mt.getVehicleNo()));
            } else {
                Main.log(String.format("Driver %s is driving %s %d in the race",
                        d.getName(),
                        v.getStr(),
                        v.getVehicleNo()));
            }
        }
        Main.log("\nRace Results:");
        ArrayList<RaceOutcome> raceOutcomes = new ArrayList<>();
        //handle race results
        for (int i = 0; i < racingVehicles.size(); i++) {
            Driver d = drivers.get(i);
            Vehicle r = racingVehicles.get(i);
            d.race(places.get(i));
            r.race(places.get(i));
            raceOutcomes.add(new RaceOutcome(places.get(i), d.getName(), r.getStr(), r.getVehicleNo(), r.getBonusAmount(), d.getInjured()));
            if (r.getClass() == MonsterTruck.class) {
                raceOutcomes.get(i).addMTStageName(((MonsterTruck) r).getStageName());
            }
        }
        publisher.firePropertyChange("raceOutcome", null, raceOutcomes);
    }
    
    private void uiSell(ArrayList<Salesperson> salespeople_) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        final Salesperson[] seller = {salespeople_.get(rng.nextInt(salespeople_.size()))};
        salespeople_.remove(seller[0]);
        HashMap<String, Command> commandMap = new HashMap<>();
        commandMap.put("switch", (n) -> {
            unlock.release();
            lock.acquire();
            return "Switched dealership";
        });
        commandMap.put("name", (n) -> seller[0].getName());
        commandMap.put("time", (n) -> LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        commandMap.put("new salesperson", (n) -> {
            seller[0] = salespeople_.get(rng.nextInt(salespeople_.size()));
            return seller[0].getName();
        });
        commandMap.put("inventory", (n) -> prettyCurrentVehicles());
        commandMap.put("details", (vin) -> {
            Vehicle selectedVehicle = null;
            for (Vehicle v : vehicleInventory) {
                if (vin == v.getVehicleNo()) {
                    selectedVehicle = v;
                    break;
                }
            }
            if (selectedVehicle == null) {
                return "Invalid vehicle selection";
            }
            return selectedVehicle.getDetails();
        });
        commandMap.put("buy", (vin) -> {
            Vehicle selectedVehicle = null;
            for (Vehicle v : vehicleInventory) {
                if (vin == v.getVehicleNo()) {
                    selectedVehicle = v;
                    break;
                }
            }
            if (selectedVehicle == null) {
                return "Invalid vehicle selection";
            } else {
                boolean saleResult = seller[0].UISell(selectedVehicle, scan);
                publisher.firePropertyChange("salesOutcome", null,
                        new SaleOutcome(saleResult, seller[0].getName(), selectedVehicle.getCleanliness(), selectedVehicle.getCondition(), selectedVehicle.getStr(), selectedVehicle.getVehicleNo(), selectedVehicle.getSalesPrice(), selectedVehicle.getBonusAmount()));
                if (saleResult) {
                    // vehicle successfully sold
                    modifyBudget(selectedVehicle.getSalesPrice());
                    dailySales += selectedVehicle.getSalesPrice();
                    vehicleInventory.remove(selectedVehicle);
                    soldVehicles.add(selectedVehicle);
                }
            }
            return "Thank you for your purchase";
            
        });
        
        lock.acquire();
        String usrIn = "";
        while (!usrIn.equals("exit")) {
            
            // display menu
            Main.log(String.format("=========================\nUser Input: FNCD %s\n=========================\n%s%s%s%s%s%s%s%s",
                    getLoc(),
                    "switch - switch FNCDs\n",
                    "name - get your salesperson's name\n",
                    "time - get the current time\n",
                    "new salesperson - get a new salesperson\n",
                    "inventory - get the current inventory\n",
                    "details {vehicle number} - get details on the specified vehicle\n",
                    "buy {vehicle number} - buy the specified vehicle\n",
                    "exit - exit this FNCD"));
            usrIn = scan.nextLine();
            int vin = 0;
            if (usrIn.startsWith("details") || usrIn.startsWith("buy")) {
                String[] cmdSplit = usrIn.split(" ");
                usrIn = cmdSplit[0];
                try {
                    vin = Integer.parseInt(cmdSplit[1]);
                } catch (Exception e) {
                    Main.log("Invalid VIN");
                    continue;
                }
            }
            if (usrIn.equals("exit")) {
                Main.log(String.format("Thank you for visiting FNCD %s.", getLoc()));
            } else {
                Command reqCmd = commandMap.get(usrIn);
                if (reqCmd == null) {
                    Main.log("Invalid command");
                } else {
                    Main.log(reqCmd.execute(vin));
                }
            }
        }
        unlock.release();
    }
    
    @Override
    public void run() {
        for (int day = 0; day < 30; day++) {
            try {
                this.day(day, false);
            } catch (InterruptedException e_) {
                e_.printStackTrace();
            }
        }
        
        // user control
        try {
            this.day(30, true);
        } catch (InterruptedException e_) {
            e_.printStackTrace();
        }
        
        try {
            this.report();
        } catch (InterruptedException e_) {
            e_.printStackTrace();
        }
    }
    
    public String getLoc() {
        return location;
    }
}
