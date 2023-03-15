package classes.vehicles;

import enums.VehicleType;

public class VehicleFactory {
    public VehicleFactory(){}
    
    public Vehicle purchaseVehicle(VehicleType vehicleType_){
        Vehicle newCar = null;
        
        switch(vehicleType_){
            case PICKUP:
                newCar = new Pickup();
                break;
            case REGULAR_CAR:
                newCar = new RegularCar();
                break;
            case VAN:
                newCar = new Van();
                break;
            case MOTORHOME:
                newCar = new Motorhome();
                break;
            case MOTORCYCLE:
                newCar = new Motorcycle();
                break;
            case ELECTRIC_CAR:
                newCar = new ElectricCar();
                break;
            case COLLECTOR_CAR:
                newCar = new CollectorCar();
                break;
            case MONSTER_TRUCK:
                newCar = new MonsterTruck();
                break;
            case PERFORMANCE_CAR:
                newCar = new PerformanceCar();
                break;
        }
        
        return newCar;
    }
}
