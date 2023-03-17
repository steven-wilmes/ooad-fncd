import classes.vehicles.CollectorCar;
import classes.vehicles.Vehicle;
import classes.vehicles.addon.ExtendedWarranty;
import classes.vehicles.addon.RoadRescueCoverage;
import classes.vehicles.addon.SatelliteRadio;
import classes.vehicles.addon.Undercoating;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoratorTest {
    Vehicle testVehicle;
    
    public DecoratorTest() {
        testVehicle = new CollectorCar();
    }
    
    @Test
    public void testIndivPrice() {
        double initialPrice = testVehicle.getSalesPrice();
        Vehicle addonVehicle = new ExtendedWarranty(testVehicle);
        assertEquals(initialPrice * 1.2, addonVehicle.getSalesPrice());
        
        addonVehicle = new RoadRescueCoverage(testVehicle);
        assertEquals(initialPrice * 1.02, addonVehicle.getSalesPrice());
        
        addonVehicle = new SatelliteRadio(testVehicle);
        assertEquals(initialPrice * 1.05, addonVehicle.getSalesPrice());
        
        addonVehicle = new Undercoating(testVehicle);
        assertEquals(initialPrice * 1.05, addonVehicle.getSalesPrice());
    }
    
    @Test
    public void testTotalPrice() {
        Vehicle addonVehicle = new ExtendedWarranty(testVehicle);
        addonVehicle = new RoadRescueCoverage(addonVehicle);
        addonVehicle = new SatelliteRadio(addonVehicle);
        addonVehicle = new Undercoating(addonVehicle);
        assertEquals((((testVehicle.getSalesPrice() * 1.2) * 1.02) * 1.05) * 1.05, addonVehicle.getSalesPrice());
    }

    @Test
    public void testGetStr(){
        Vehicle addonVehicle = new ExtendedWarranty(testVehicle);
        addonVehicle = new RoadRescueCoverage(addonVehicle);
        addonVehicle = new SatelliteRadio(addonVehicle);
        addonVehicle = new Undercoating(addonVehicle);
        assertEquals("Collector Car, extended warranty, road rescue coverage, satellite radio, undercoating", addonVehicle.getStr());
    }
}
