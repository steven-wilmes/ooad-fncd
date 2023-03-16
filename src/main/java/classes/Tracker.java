package classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Observer pattern, singleton with eager instantiation
 */
public class Tracker implements PropertyChangeListener {
    double totalLoan;
    double totalStaff;
    double totalEarned;
    int day;
    
    // OO PATTERN: SINGLETON (EAGER)
    static Tracker trackerInstance = new Tracker();
    
    private Tracker(){}
    
    public static synchronized Tracker getInstance(){
        return trackerInstance;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if (propName.equals("loan")) {
            totalLoan = (Double) evt.getNewValue();
        } else if (propName.equals("staffPay")) {
            totalStaff += (Double) evt.getNewValue();
        } else if (propName.equals("moneyIn")) {
            totalEarned += (Double) evt.getNewValue();
        } else if (propName.equals("day")) {
            day = (Integer) evt.getNewValue();
        }
    }
    
    public void report() {
        System.out.println(String.format("Tracker: Day %d\nTotal money earned by all Staff: $%.2f\nTotal money earned by the FNCD: $%.2f",
                this.day,
                this.totalStaff,
                this.totalEarned));
    }
}
