import classes.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {
    Logger testLogger;
    
    public LoggerTest() {
        testLogger = new Logger(1);
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
}
