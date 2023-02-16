package enums;

import classes.vehicles.PerformanceCar;
import classes.vehicles.Pickup;
import classes.vehicles.RegularCar;
import classes.vehicles.Vehicle;

/**
 * enum for type of vehicle
 */
public enum VehicleType {
    PERFORMANCE_CAR(PerformanceCar.class, "Performance Car"),
    REGULAR_CAR(RegularCar.class, "Regular Car"),
    PICKUP(Pickup.class, "Pickup");
    
    private Class<? extends Vehicle> classType;
    private String str;
    
    VehicleType(Class<? extends Vehicle> classType_, String str_) {
        classType = classType_;
        str = str_;
    }
    
    /**
     * @return the {@link Vehicle} subclass associated with the VehicleType
     */
    public Class<? extends Vehicle> getClassType() {
        return classType;
    }
    
    /**
     * @return the String representation of the VehicleType
     */
    public String getStr() {
        return str;
    }
    
    /**
     * Matches a {@link Vehicle} subclass with the associated VehicleType
     * @param class_ the class to match
     * @return the VehicleType
     */
    public static VehicleType match(Class<? extends Vehicle> class_) {
        if (class_ == PerformanceCar.class) {
            return PERFORMANCE_CAR;
        } else if (class_ == Pickup.class) {
            return PICKUP;
        } else {
            return REGULAR_CAR;
        }
    }
}
