import classes.staff.*;
import classes.vehicles.*;
import classes.Buyer;
import java.util.ArrayList;
import enums.Cleanliness;
import enums.Condition;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffTest {
    Intern testIntern;
    Mechanic testMechanic;
    Driver testDriver;
    Salesperson testSalesperson;
    Buyer testBuyer;

    Vehicle testVehicle;
    ArrayList<Vehicle> testInventory;

    public StaffTest() {
        //Create our test staff members
        testIntern = new Intern("testIntern");
        testMechanic = new Mechanic("testMechanic");
        testDriver = new Driver("testDriver");
        testSalesperson = new Salesperson("testSalesperson");
        testBuyer = new Buyer();

        //Create a test vehicle to test functions on and add it to test inventory
        testVehicle = new RegularCar();
        testVehicle.setCondition(Condition.LIKE_NEW);
        testVehicle.wash(Cleanliness.SPARKLING);
        testInventory = new ArrayList<>();
        testInventory.add(testVehicle);

    }

    //Trying to wash an already sparkling vehicle will result in an error
    @Test
    public void testWash() {
        assertEquals("Error: tried to wash an already SPARKLING vehicle", testIntern.wash(testVehicle).getExtraMsg());
    }

    //Trying to repair an already like new vehicle will log an error, but return the vehicle as repaired
    @Test
    public void testRepair(){
        assertEquals(Condition.LIKE_NEW, testMechanic.repair(testVehicle).getInitCondition());
        assertEquals(Condition.LIKE_NEW, testMechanic.repair(testVehicle).getResultCondition());
    }

    //Testing that races will increment driver and vehicle wins, and increase vehicle sales price
    @Test
    public void testRace(){
        testDriver.race(1);
        testDriver.race(2);
        testDriver.race(3);
        assertEquals(3, testDriver.getWins());

        double oldSalesPrice = testVehicle.getSalesPrice();
        testVehicle.race(1);
        assertEquals(1, testVehicle.getWins());
        assertEquals(oldSalesPrice * 1.1, testVehicle.getSalesPrice());
    }

    //Testing that we won't try to sell a broken vehicle.
    @Test
    public void testSale(){
        testInventory.get(0).setCondition(Condition.BROKEN);

        Vehicle testSellVehicle = new CollectorCar();
        testSellVehicle.setCondition(Condition.LIKE_NEW);
        testInventory.add(testSellVehicle);

        assertEquals(testSellVehicle,testSalesperson.sell(testBuyer, testInventory).getX());
    }
}