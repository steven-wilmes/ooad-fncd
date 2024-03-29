package classes;

import classes.observerData.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Observer pattern
 */
public class Logger implements PropertyChangeListener {
    /**
     * file name for the current log file
     */
    String dailyLogName;
    
    int currentDay = 1;
    
    // OO PATTERN: SINGLETON (LAZY)
    static Logger loggerInstance;
    
    public static synchronized Logger getInstance(int day_) {
        if (loggerInstance == null || loggerInstance.currentDay != day_) {
            loggerInstance = new Logger(day_); // new logger created on day 1
        }
        return loggerInstance;
    }
    
    /**
     * create a new logger and file
     *
     * @param day_ the current day
     */
    private Logger(int day_) {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }
        dailyLogName = String.format("logs%sLogger-%d.txt", File.separator, day_);
        File dailyLog = new File(dailyLogName);
        if (dailyLog.exists()) {
            dailyLog.delete();
        }
        try {
            dailyLog.createNewFile();
        } catch (IOException e) {
            System.out.println("File creation failed.");
        }
    }
    
    /**
     * log to the file
     *
     * @param str_ line to log
     */
    public void log(String str_) {
        try {
            FileWriter writer = new FileWriter(dailyLogName, true);
            writer.write(str_ + '\n');
            writer.close();
        } catch (IOException e) {
            System.out.println("");
        }
    }
    
    /**
     * handle an event
     *
     * @param evt event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // the event type
        String propName = evt.getPropertyName();
        String sourceName = ((Dealership) evt.getSource()).getLoc();
        if (propName.equals("washOutcome")) {
            // type: WashOutcome
            if (evt.getNewValue().getClass() != WashOutcome.class) {
                main.Main.log("Invalid wash outcome update");
            } else {
                // valid wash update
                WashOutcome toLog = (WashOutcome) evt.getNewValue();
                if (toLog.getWashed()) {
                    log(String.format("Intern %s %s %s %s %d and made it Sparkling (earned $%.2f bonus).",
                            toLog.getInternName(),
                            toLog.getWashType(),
                            toLog.getInitCleanliness().getStr(),
                            toLog.getVehicleType(),
                            toLog.getVehicleNum(),
                            toLog.getBonusAmt()));
                } else {
                    log(String.format("Intern %s %s %s %s %d and made it %s.",
                            toLog.getInternName(),
                            toLog.getWashType(),
                            toLog.getInitCleanliness().getStr(),
                            toLog.getVehicleType(),
                            toLog.getVehicleNum(),
                            toLog.getResultCleanliness().getStr()));
                }
                if (toLog.getExtraMsg() != "") {
                    log(toLog.getExtraMsg());
                }
            }
        } else if (propName.equals("repairOutcome")) {
            // type: RepairOutcome
            if (evt.getNewValue().getClass() != RepairOutcome.class) {
                main.Main.log("Invalid repair outcome update");
            } else {
                // valid repair update
                RepairOutcome toLog = (RepairOutcome) evt.getNewValue();
                if (toLog.getRepaired()) {
                    log(String.format("Mechanic %s fixed %s %s %d and made it %s (earned $%.2f bonus).",
                            toLog.getMechanicName(),
                            toLog.getInitCondition().getStr(),
                            toLog.getVehicleType(),
                            toLog.getVehicleNum(),
                            toLog.getResultCondition().getStr(),
                            toLog.getBonusAmt()));
                } else {
                    log(String.format("Mechanic %s tried to fix %s %s %d and did not succeed.",
                            toLog.getMechanicName(),
                            toLog.getInitCondition().getStr(),
                            toLog.getVehicleType(),
                            toLog.getVehicleNum()));
                }
            }
        } else if (propName.equals("salesOutcome")) {
            // type: SaleOutcome
            if (evt.getNewValue().getClass() != SaleOutcome.class) {
                main.Main.log("Invalid sale outcome update");
            } else {
                // valid sales update
                SaleOutcome toLog = (SaleOutcome) evt.getNewValue();
                if (toLog.getSold()) {
                    log(String.format("Salesperson %s sold %s %s %s %d to Buyer for $%.2f (earned $%.2f bonus)",
                            toLog.getSalespersonName(),
                            toLog.getCleanliness().getStr(),
                            toLog.getCondition().getStr(),
                            toLog.getVehicleType(),
                            toLog.getVehicleNum(),
                            toLog.getSalesPrice(),
                            toLog.getBonusAmt()));
                } else {
                    log(String.format("Salesperson %s attempted to sell %s %s %s %d to Buyer for $%.2f and did not succeed.",
                            toLog.getSalespersonName(),
                            toLog.getCleanliness().getStr(),
                            toLog.getCondition().getStr(),
                            toLog.getVehicleType(),
                            toLog.getVehicleNum(),
                            toLog.getSalesPrice()));
                }
            }
        } else if (propName.equals("raceOutcome")) {
            // type: ArrayList<RaceOutcome>
            if (evt.getNewValue().getClass() != ArrayList.class) {
                main.Main.log("Invalid race outcome update");
            } else {
                // valid race update
                // list of race outcomes
                ArrayList<RaceOutcome> toLog = (ArrayList<RaceOutcome>) evt.getNewValue();
                for (RaceOutcome r : toLog) {
                    if (!r.getMtStageName().equals("No")) {
                        // monster truck race
                        log(String.format("Driver %s is driving %s (%s %d) in the race",
                                r.getDriverName(),
                                r.getMtStageName(),
                                r.getVehicleType(),
                                r.getVehicleNum()));
                    } else {
                        log(String.format("Driver %s is driving %s %d in the race",
                                r.getDriverName(),
                                r.getVehicleType(),
                                r.getVehicleNum()));
                    }
                }
                
                for (RaceOutcome r : toLog) {
                    if (r.getPlace() <= 3) {
                        log(String.format("Driver %s placed %d in the race! (earned $%.2f bonus).",
                                r.getDriverName(),
                                r.getPlace(),
                                r.getBonusAmt()));
                        log(String.format("%s %d won!",
                                r.getVehicleType(),
                                r.getVehicleNum()));
                    } else if (r.getPlace() >= 16) {
                        if (r.getDriverInjured()) {
                            log(String.format("Driver %s placed %d in the race and got injured!",
                                    r.getDriverName(),
                                    r.getPlace()));
                        }
                        log(String.format("%s %d became damaged!",
                                r.getVehicleType(),
                                r.getVehicleNum()));
                    } else {
                        log(String.format("Driver %s placed %d in the race.",
                                r.getDriverName(),
                                r.getPlace()));
                    }
                }
            }
        } else if (propName.equals("newStaff")) {
            // type: Tuple(String, String)
            if (evt.getNewValue().getClass() != Tuple.class) {
                main.Main.log("Invalid new staff update");
            } else {
                String name = (String) ((Tuple) evt.getNewValue()).getX();
                String role = (String) ((Tuple) evt.getNewValue()).getY();
                if (role.equals("Intern") || role.equals("Driver")) {
                    log(String.format("Hired %s as a new %s at Dealership %s.", name, role, sourceName));
                } else {
                    log(String.format("Promoted %s from Intern to %s at Dealership %s.", name, role, sourceName));
                }
            }
        } else if (propName.equals("staffPay")) {
            log(String.format("Paid staff $%.2f at Dealership %s", (Double) evt.getNewValue(), sourceName));
        } else if (propName.equals("moneyIn")) {
            log(String.format("The %s FNCD made $%.2f", sourceName, (Double) evt.getNewValue()));
        } else if (propName.equals("loan")) {
            log(String.format("The %s FNCD has taken out a loan. The %s FNCD has $%.2f in loans.", sourceName, sourceName, (Double) evt.getNewValue()));
        }
    }
}
