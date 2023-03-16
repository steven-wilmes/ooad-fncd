import classes.staff.Intern;
import classes.staff.Salesperson;
import classes.staff.Staff;
import classes.staff.StaffFactory;
import classes.vehicles.CollectorCar;
import classes.vehicles.MonsterTruck;
import classes.vehicles.VehicleFactory;
import enums.StaffType;
import enums.VehicleType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryTest {
    StaffFactory staffFactory;
    VehicleFactory vehicleFactory;
    
    public FactoryTest() {
        staffFactory = new StaffFactory();
        vehicleFactory = new VehicleFactory();
    }
    
    @Test
    public void testPromotionError() {
        Staff testIntern = staffFactory.hireStaff(StaffType.INTERN);
        Staff attemptedPromotion = staffFactory.hireStaff(StaffType.DRIVER, (Intern) testIntern);
        assertNotEquals(testIntern.getName(), attemptedPromotion.getName());
    }
    
    @Test
    public void testDuplicateStaffNames() {
        ArrayList<Salesperson> staff = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            staff.add((Salesperson) staffFactory.hireStaff(StaffType.SALESPERSON));
        }
        while (staff.size() > 0) {
            Salesperson person = staff.get(0);
            staff.remove(person);
            for (Salesperson s : staff) {
                assertNotEquals(person.getName(), s.getName());
            }
        }
    }
    
    @Test
    public void testDuplicateTruckNames() {
        ArrayList<MonsterTruck> createdTrucks = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            createdTrucks.add((MonsterTruck) vehicleFactory.purchaseVehicle(VehicleType.MONSTER_TRUCK));
        }
        while (createdTrucks.size() > 0) {
            MonsterTruck truck = createdTrucks.get(0);
            createdTrucks.remove(truck);
            for (MonsterTruck v : createdTrucks) {
                assertNotEquals(truck.getStageName(), v.getStageName());
            }
        }
    }
    
    @Test
    public void yearTest() {
        for (int i = 0; i < 20; i++) {
            CollectorCar newCar = (CollectorCar) vehicleFactory.purchaseVehicle(VehicleType.COLLECTOR_CAR);
            assertTrue(newCar.getYear() > 1920);
            assertTrue((25000 + ((LocalDate.now().getYear() - newCar.getYear()) * 100)) * 2 >= newCar.getSalesPrice());
        }
    }
    
    
}
