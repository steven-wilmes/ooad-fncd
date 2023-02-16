package enums;

import classes.vehicles.PerformanceCar;
import classes.vehicles.Pickup;
import classes.vehicles.RegularCar;
import classes.vehicles.Vehicle;

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
    
    public Class<? extends Vehicle> getClassType() {
        return classType;
    }
    
    public String getStr() {
        return str;
    }
    
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
