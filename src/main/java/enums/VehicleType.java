package enums;

import classes.vehicles.*;

public enum VehicleType {
    PERFORMANCE_CAR(PerformanceCar.class),
    REGULAR_CAR(RegularCar.class),
    PICKUP(Pickup.class);
    
    private Class<? extends Vehicle> classType;
    VehicleType(Class<? extends Vehicle> classType_){
        classType = classType_;
    }
    
    public Class<? extends Vehicle> getClassType(){
        return classType;
    }
}
