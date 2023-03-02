package classes.vehicles.addon;

import classes.vehicles.Vehicle;

public class SatelliteRadio extends AddOnDecorator {
    Vehicle v;
    
    public SatelliteRadio(Vehicle v_) {
        this.v = v_;
    }
    
    public String getStr() {
        return this.v.getStr() + ", satellite radio";
    }
    
    public double getSalesPrice() {
        return (this.v.getSalesPrice() * 1.05);
    }
    
    public double getCost() {
        return this.v.getCost();
    }
}
