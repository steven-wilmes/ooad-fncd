package classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Tracker implements PropertyChangeListener {
    double totalLoan;
    double totalStaff;
    double totalEarned;
    int day;
    
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
