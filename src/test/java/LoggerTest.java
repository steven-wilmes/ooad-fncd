import classes.Dealership;
import classes.Logger;
import classes.observerData.WashOutcome;
import enums.Cleanliness;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {
    Logger testLogger;
    PropertyChangeSupport publisher;
    
    public LoggerTest() {
        testLogger = Logger.getInstance(1);
        publisher = new PropertyChangeSupport(new Dealership("lfjk", new Semaphore(1), new Semaphore(0)));
        publisher.addPropertyChangeListener(testLogger);
    }
    
    @Test
    public void testFileCreate() {
        assertTrue((new File("logs")).exists());
        assertTrue((new File(String.format("logs%sLogger-1.txt", File.separator))).exists());
    }
    
    @Test
    public void testLog() throws FileNotFoundException {
        testLogger.log("abcd");
        Scanner scan = new Scanner(new File(String.format("logs%sLogger-1.txt", File.separator)));
        assertEquals("abcd", scan.nextLine());
    }
    
    @Test
    public void testEvent() throws FileNotFoundException {
        publisher.firePropertyChange("washOutcome", null, new WashOutcome(false, "Test", "chemical", Cleanliness.DIRTY, Cleanliness.CLEAN, "regular car", 1, 400, ""));
        Scanner scan = new Scanner(new File(String.format("logs%sLogger-1.txt", File.separator)));
        scan.nextLine(); // abcd
        assertEquals("Intern Test chemical Dirty regular car 1 and made it Clean.", scan.nextLine());
    }
}
