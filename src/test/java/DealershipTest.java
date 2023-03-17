import classes.Dealership;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealershipTest {
    Dealership testDealership;
    
    public DealershipTest() {
        testDealership = new Dealership("test", new Semaphore(1), new Semaphore(1));
    }
    
    @Test
    public void testCreation() {
        assertEquals(12, testDealership.getStaffMembers().size());
    }

}
